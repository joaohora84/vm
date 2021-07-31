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

import com.vm.model.Clinica;
import com.vm.repository.ClinicaRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.repository.SecretariaRepository;

@Controller
public class ClinicaController {
	
	
	@Autowired
	private ClinicaRepository clinicaRepository;
	
	@Autowired
	private SecretariaRepository secretariaRepository;
	
	@Autowired
	private PrescritorRepository prescriorRepository;
	
	final int QUANTIDADE_ITENS_PAGINA = 20;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroclinica")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroclinica");

		model.addObject("clinicaobj", new Clinica());
		return model;

	}
	
	@PostMapping("**/salvarclinica")
	public ModelAndView salvar(Clinica clinica) {

		ModelAndView model = new ModelAndView("/cadastroclinica");

		try {

			clinica.setSecretaria(secretariaRepository.getSecretariaClinica(clinica.getIdclinica()));

			clinica.setPrescritor(prescriorRepository.getPrescritorClinica(clinica.getIdclinica()));

			Date data = new Date(System.currentTimeMillis());

			clinica.setData_alteracao(data);

			if (clinica.getData_cadastro() == null) {

				clinica.setData_cadastro(data);

				clinicaRepository.save(clinica);
			} else {
				clinicaRepository.save(clinica);
			}

			model.addObject("clinicaobj", new Clinica());
			model.addObject("msgSucesso", "Clinica salva com sucesso!");

		} catch (Exception e) {

			model.addObject("clinicaobj", new Clinica());
			model.addObject("msgErro", e.getCause().getCause().getMessage());

		}

		return model;

	}
	
	@GetMapping("/editarclinica/{idclin}")
	public ModelAndView editar(@PathVariable("idclin") Long idclin) {

		ModelAndView model = new ModelAndView("/cadastroclinica");

		Optional<Clinica> clinica = clinicaRepository.findById(idclin);

		model.addObject("clinicaobj", clinica.get());

		return model;

	}

	@GetMapping("/removerclinica/{idclin}")
	public ModelAndView excluir(@PathVariable("idclin") Long idclin) {

		clinicaRepository.deleteById(idclin);

		ModelAndView model = new ModelAndView("/listaclinica");
		model.addObject("clinicas", clinicaRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
		model.addObject("msgExclusao", "Clinica removida com sucesso.");

		return model;

	}

	@PostMapping("**/pesquisarclinica")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA, sort = { "nome" }) Pageable pageable) {
		
		Page<Clinica> clinicas = null;

		clinicas = clinicaRepository.findClinicaByNamePage(nome, pageable);

		ModelAndView model = new ModelAndView("/listaclinica");
		model.addObject("clinicas", clinicas);
		

		return model;

	}

	@GetMapping("/listarclinicas")
	public ModelAndView clinicas() {

		ModelAndView modelAndView = new ModelAndView("/listaclinica");
		modelAndView.addObject("clinicas", clinicaRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
		return modelAndView;

	}

	@GetMapping("/clinicapage")
	public ModelAndView carregaClinicaPorPaginacao(@PageableDefault(size = QUANTIDADE_ITENS_PAGINA) Pageable pageable,
			ModelAndView model) {

		Page<Clinica> pageClinica = clinicaRepository.findAll(PageRequest.of(pageable.getPageNumber(), QUANTIDADE_ITENS_PAGINA, Sort.by("nome")));
		model.addObject("clinicas", pageClinica);
		model.setViewName("/listaclinica");

		return model;

	}


}
