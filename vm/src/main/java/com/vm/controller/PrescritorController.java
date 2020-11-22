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

import com.vm.model.PerfilPrescritor;
import com.vm.model.Prescritor;
import com.vm.repository.ClinicaRepository;
import com.vm.repository.EspecialidadePrescritorRepository;
import com.vm.repository.PerfilPrescritorRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.service.ServiceRelatorio;

@Controller
public class PrescritorController {

	@Autowired
	private PrescritorRepository prescritorRepository;

	@Autowired
	private PerfilPrescritorRepository perfilPrescritorRepository;

	@Autowired
	private EspecialidadePrescritorRepository especialidadePrescritorRepository;

	@Autowired
	private ClinicaRepository clinicaRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private ServiceRelatorio serviceRelatorio;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroprescritor")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroprescritor");

		model.addObject("especialidades", especialidadePrescritorRepository.findAllByOrderBy());
		model.addObject("clinicas", clinicaRepository.findAll());
		model.addObject("prescritorobj", new Prescritor());

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarprescritor")
	public ModelAndView salvar(Prescritor prescritor, PerfilPrescritor pp) {

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

				Long idpres = prescritor.getIdprescritor();
				Optional<Prescritor> pres = prescritorRepository.findById(idpres);
				pp.setPrescritor(prescritor);

				perfilPrescritorRepository.save(pp);

			}

			String msgSucesso = "Prescritor salvo com sucesso!";

			ModelAndView model = new ModelAndView("/cadastroprescritor");
			model.addObject("especialidades", especialidadePrescritorRepository.findAll());
			model.addObject("clinicas", clinicaRepository.findAll());
			model.addObject("prescritorobj", prescritor);
			model.addObject("msgSucesso", msgSucesso);
			model.addObject("perfilobj", pp);

			return model;

		} catch (Exception e) {

			String msgErro = e.getCause().getCause().getMessage();

			ModelAndView model = new ModelAndView("/cadastroprescritor");
			model.addObject("especialidades", especialidadePrescritorRepository.findAll());
			model.addObject("clinicas", clinicaRepository.findAll());
			model.addObject("prescritorobj", prescritor);
			model.addObject("msgErro", msgErro);
			model.addObject("perfilobj", pp);

			return model;

		}

	}

	@GetMapping("/editarprescritor/{idpres}")
	public ModelAndView editar(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/cadastroprescritor");

		Optional<Prescritor> prescritor = prescritorRepository.findById(idpres);

		model.addObject("especialidades", especialidadePrescritorRepository.findAll());
		model.addObject("clinicas", clinicaRepository.findAll());
		model.addObject("prescritorobj", prescritor.get());

		return model;

	}

	@GetMapping("/removerprescritor/{idpres}")
	public ModelAndView excluir(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/listaprescritor");

		try {

			prescritorRepository.deleteById(idpres);

			model.addObject("prescritores", prescritorRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			model.addObject("msgExclusao", "Prescritor excluido com sucesso!");
			model.addObject("prescritorobj", new Prescritor());

		} catch (Exception e) {

			model.addObject("prescritores", prescritorRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			model.addObject("msgErro", e.getCause().getCause().getMessage());

		}

		return model;

	}

	@PostMapping("**/pesquisarprescritor")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Prescritor> prescritores = null;

		prescritores = prescritorRepository.findPrescritorByNamePage(nome, pageable);

		ModelAndView model = new ModelAndView("/listaprescritor");
		model.addObject("prescritores", prescritores);
		model.addObject("prescritorobj", new Prescritor());

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarprescritores")
	public ModelAndView prescritores() {

		ModelAndView modelAndView = new ModelAndView("/listaprescritor");
		modelAndView.addObject("prescritores", prescritorRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;

	}

	@GetMapping("/prescritorpage")
	public ModelAndView carregaPrescritorPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
			ModelAndView model) {

		Page<Prescritor> pagePrescritor = prescritorRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("prescritores", pagePrescritor);
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

		return model;
	}

	@GetMapping("/perfil/{idpres}")
	public ModelAndView perfil(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/cadastroperfilprescritor");

		Optional<Prescritor> prescritor = prescritorRepository.findById(idpres);
		PerfilPrescritor perfil = perfilPrescritorRepository.getPerfil(idpres);

		model.addObject("prescritorobj", prescritor.get());
		model.addObject("perfilobj", perfil);

		return model;

	}

}
