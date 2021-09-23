package com.vm.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Ativo;
import com.vm.model.PerfilPrescritor;
import com.vm.model.Prescritor;
import com.vm.repository.ClinicaRepository;
import com.vm.repository.EspecialidadePrescritorRepository;
import com.vm.repository.PerfilPrescritorRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.repository.SecretariaRepository;
import com.vm.service.ServiceRelatorio;

@Controller
public class PrescritorController {

	final int QUANTIDADE_ITENS_PAGINA = 20;

	@Autowired
	public PrescritorRepository prescritorRepository;

	@Autowired
	private PerfilPrescritorRepository perfilPrescritorRepository;

	@Autowired
	private EspecialidadePrescritorRepository especialidadePrescritorRepository;

	@Autowired
	private ClinicaRepository clinicaRepository;

	private SecretariaRepository secretariaRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private ServiceRelatorio serviceRelatorio;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroprescritor")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroprescritor");

		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
		model.addObject("clinicas", clinicaRepository.findAllByOrderBy());
		model.addObject("prescritorobj", new Prescritor());

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarprescritor")
	public ModelAndView salvar(Prescritor prescritor, PerfilPrescritor pp) {

		// prescritor.setSecretaria(secretariaRepository.getSecretariaPrescritor(prescritor.getIdprescritor()));

		ModelAndView model = new ModelAndView("/cadastroprescritor");

		System.out.println(prescritor.toString());

		try {

			Date data = new Date(System.currentTimeMillis());

			prescritor.setData_alteracao(data);

			if (prescritor.getData_cadastro() == null) {

				prescritor.setData_cadastro(data);

				prescritorRepository.save(prescritor);

				Long idpres = prescritor.getIdprescritor();
				Optional<Prescritor> pres = prescritorRepository.findById(idpres);
				pp.setPrescritor(prescritor);

				perfilPrescritorRepository.save(pp);

			} else {

				prescritorRepository.save(prescritor);

				pp.setPrescritor(prescritor);

				perfilPrescritorRepository.save(pp);

			}

			String msgSucesso = "Prescritor salvo com sucesso!";

			model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
			model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
			model.addObject("prescritorobj", prescritor);
			model.addObject("msgSucesso", msgSucesso);
			model.addObject("perfilobj", pp);

		} catch (Exception e) {

			// String msgErro = e.getCause().getCause().getMessage();

			model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
			model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
			model.addObject("prescritorobj", new Prescritor());
			model.addObject("msgErro", e.getMessage());
			model.addObject("perfilobj", pp);

		}

		return model;

	}

	@GetMapping("/editarprescritor/{idpres}")
	public ModelAndView editar(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/cadastroprescritor");

		Optional<Prescritor> prescritor = prescritorRepository.findById(idpres);

		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
		model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		model.addObject("prescritorobj", prescritor.get());

		return model;

	}

	@GetMapping("/removerprescritor/{idpres}")
	public ModelAndView excluir(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/listaprescritor");

		try {

			prescritorRepository.deleteById(idpres);

			model.addObject("prescritores",
					prescritorRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
			model.addObject("msgExclusao", "Prescritor excluido com sucesso!");
			model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
			model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
			model.addObject("prescritorobj", new Prescritor());

		} catch (Exception e) {

			model.addObject("prescritores",
					prescritorRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
			model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());

		}

		return model;

	}

	@PostMapping("**/pesquisarprescritor")
	public ModelAndView pesquisar(@RequestParam("nome") String nome, @RequestParam("conselho") String conselho,
			@RequestParam("numero_conselho") String numeroConselho, @RequestParam("especialidade") Long especialidadeid,
			@RequestParam("clinica") Long clinicaid,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA, sort = { "nome" }) Pageable pageable) {

		Page<Prescritor> prescritores = null;
		String msgErro = null;

		prescritores = prescritorRepository.getPrescritores(nome, conselho, numeroConselho, especialidadeid, clinicaid,
				pageable);

		if (prescritores.isEmpty()) {

			msgErro = "Nenhum prescritor encontrado com os valores informados.";

		}

		ModelAndView model = new ModelAndView("/listaprescritor");
		model.addObject("prescritores", prescritores);
		model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		model.addObject("msgErro", msgErro);
		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
		model.addObject("prescritorobj", new Prescritor());
		model.addObject("nome", nome);
		model.addObject("conselho", conselho);
		model.addObject("numeroConselho", numeroConselho);
		model.addObject("especialidade", especialidadeid);
		model.addObject("clinica", clinicaid);
		model.addObject("conselho", conselho);

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarprescritores")
	public ModelAndView prescritores() {

		ModelAndView modelAndView = new ModelAndView("/listaprescritor");
		modelAndView.addObject("prescritores",
				prescritorRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
		modelAndView.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
		modelAndView.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		return modelAndView;

	}

	@GetMapping("/prescritorpage")
	public ModelAndView carregaPrescritorPorPaginacao(@RequestParam("nome") String nome,
			@RequestParam("conselho") String conselho, @RequestParam("numeroConselho") String numeroConselho,
			@RequestParam("especialidade") Long especialidade, @RequestParam("clinica") Long clinica,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA) Pageable pageable, ModelAndView model) {
		
		if (nome != "" || !nome.equals(null) 
				|| conselho.equals("") || conselho.equals(null)
				|| numeroConselho.equals("") || numeroConselho.equals(null)
				|| !especialidade.equals("") || !especialidade.equals(null)
				|| !clinica.equals("") || !clinica.equals(null)) {
			
			carregaAtivoPorPaginacaoPage(nome, conselho, numeroConselho, especialidade, clinica, pageable.getPageNumber(), pageable, model);
			
		} else {
			
			Page<Prescritor> pagePrescritor = prescritorRepository
					.findAll(PageRequest.of(pageable.getPageNumber(), QUANTIDADE_ITENS_PAGINA, Sort.by("nome")));
			model.addObject("prescritores", pagePrescritor);
			model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
			model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
			model.addObject("nome", nome);
			model.addObject("conselho", conselho);
			model.addObject("numeroConselho", numeroConselho);
			model.addObject("especialidade", especialidade);
			model.addObject("clinica", clinica);
			model.addObject("conselho", conselho);
			model.setViewName("/listaprescritor");

			return model;
			
		}

		return model;

	}

	@GetMapping("/prescritorpage/page/{page}")
	public ModelAndView carregaAtivoPorPaginacaoPage(@RequestParam("nome") String nome,
			@RequestParam("conselho") String conselho, @RequestParam("numeroConselho") String numeroConselho,
			@RequestParam("especialidade") Long especialidade, @RequestParam("clinica") Long clinica,
			@RequestParam("page") int pagina, @PageableDefault(size = QUANTIDADE_ITENS_PAGINA) Pageable pageable,
			ModelAndView model) {

		Page<Prescritor> prescritores = null;

		prescritores = prescritorRepository.getPrescritores(nome, conselho, numeroConselho, especialidade, clinica,
				PageRequest.of(pagina, QUANTIDADE_ITENS_PAGINA, Sort.by("nome")));

		model.addObject("prescritores", prescritores);
		model.addObject("especialidades", especialidadePrescritorRepository.findAll(Sort.by("nome")));
		model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		model.addObject("nome", nome);
		model.addObject("conselho", conselho);
		model.addObject("numeroConselho", numeroConselho);
		model.addObject("especialidade", especialidade);
		model.addObject("clinica", clinica);
		model.addObject("conselho", conselho);
		model.setViewName("/listaprescritor");

		return model;

	}

	@PostMapping("/addperfil/{prescritorid}")
	public ModelAndView addPerfil(@PathVariable("prescritorid") Long prescritorid, PerfilPrescritor pp) {

		Prescritor prescritor = prescritorRepository.findById(prescritorid).get();

		pp.setPrescritor(prescritor);

		perfilPrescritorRepository.save(pp);

		ModelAndView model = new ModelAndView("/cadastroprescritor");
		model.addObject("prescritorobj", prescritor);
		model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());

		return model;
	}

	@GetMapping("/perfil/{idpres}")
	public ModelAndView perfil(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/cadastroperfilprescritor");

		Optional<Prescritor> prescritor = prescritorRepository.findById(idpres);
		PerfilPrescritor perfil = perfilPrescritorRepository.getPerfil(idpres);

		model.addObject("prescritorobj", prescritor.get());
		model.addObject("perfilobj", perfil);
		model.addObject("clinicas", clinicaRepository.findAll(Sort.by("nome")));
		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());

		return model;

	}

}
