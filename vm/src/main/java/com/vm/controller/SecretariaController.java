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

import com.vm.model.PerfilSecretaria;
import com.vm.model.Secretaria;
import com.vm.repository.ClinicaRepository;
import com.vm.repository.PerfilSecretariaRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.repository.SecretariaRepository;

@Controller
public class SecretariaController {
	
	@Autowired
	private SecretariaRepository secretariaRepository;
	
	@Autowired
	private ClinicaRepository clinicaRepository;
	
	@Autowired
	private PrescritorRepository prescritorRepository;
	
	@Autowired
	private PerfilSecretariaRepository perfilSecretariaRepository;
	
		
	@RequestMapping(method = RequestMethod.GET, value = "/cadastrosecretaria")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastrosecretaria");

		model.addObject("secretariaobj", new Secretaria());
		model.addObject("clinicas", clinicaRepository.findAll());
		model.addObject("prescritores", prescritorRepository.findAll());
		
		return model;

	}
	
	@PostMapping("**/salvarsecretaria")
	public ModelAndView salvar(Secretaria secretaria, PerfilSecretaria ps) {

		ModelAndView model = new ModelAndView("/cadastrosecretaria");

		try {

			Date data = new Date(System.currentTimeMillis());

			secretaria.setData_alteracao(data);

			if (secretaria.getData_cadastro() == null) {

				secretaria.setData_cadastro(data);

				secretariaRepository.save(secretaria);
				
				//Long idsec = secretaria.getIdsecretaria();
				//Optional<Secretaria> sec = secretariaRepository.findById(idsec);
				ps.setSecretaria(secretaria);
				
				perfilSecretariaRepository.save(ps);
				
				
			} else {
				secretariaRepository.save(secretaria);
				
				ps.setSecretaria(secretaria);
				
				perfilSecretariaRepository.save(ps);
				
			}

			model.addObject("secretariaobj", secretaria);
			model.addObject("msgSucesso", "Secretária salva com sucesso!");
			model.addObject("clinicas", clinicaRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());

		} catch (Exception e) {

			model.addObject("secretariaobj", new Secretaria());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("clinicas", clinicaRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());

		}

		return model;

	}
	
	@GetMapping("/editarsecretaria/{idsec}")
	public ModelAndView editar(@PathVariable("idsec") Long idsec) {

		ModelAndView model = new ModelAndView("/cadastrosecretaria");

		Optional<Secretaria> secretaria = secretariaRepository.findById(idsec);

		model.addObject("secretariaobj", secretaria.get());
		model.addObject("clinicas", clinicaRepository.findAll());
		model.addObject("prescritores", prescritorRepository.findAll());

		return model;

	}

	@GetMapping("/removersecretaria/{idsec}")
	public ModelAndView excluir(@PathVariable("idsec") Long idsec) {

		secretariaRepository.deleteById(idsec);

		ModelAndView model = new ModelAndView("/listasecretaria");
		model.addObject("secretarias", secretariaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("msgExclusao", "Secretária removida com sucesso.");

		return model;

	}

	@PostMapping("**/pesquisarsecretaria")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {
		
		Page<Secretaria> secretarias = null;

		secretarias = secretariaRepository.findSecretariaByNamePage(nome, pageable);

		ModelAndView model = new ModelAndView("/listasecretaria");
		model.addObject("secretarias", secretarias);
		

		return model;

	}

	@GetMapping("/listarsecretarias")
	public ModelAndView secretarias() {

		ModelAndView modelAndView = new ModelAndView("/listasecretaria");
		modelAndView.addObject("secretarias", secretariaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;

	}

	@GetMapping("/secretariapage")
	public ModelAndView carregaSecretariaPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
			ModelAndView model) {

		Page<Secretaria> pageSecretaria = secretariaRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("secretarias", pageSecretaria);
		model.setViewName("/listasecretaria");

		return model;

	}
	
	@GetMapping("/perfilsecretaria/{idsec}")
	public ModelAndView perfil(@PathVariable("idsec") Long idsec) {

		ModelAndView model = new ModelAndView("/cadastroperfilsecretaria");

		Optional<Secretaria> secretaria = secretariaRepository.findById(idsec);
		PerfilSecretaria perfil = perfilSecretariaRepository.getPerfil(idsec);

		model.addObject("secretariaobj", secretaria.get());
		model.addObject("perfilobj", perfil);

		return model;

	}
	
	@PostMapping("/addperfilsecretaria/{secretariaid}")
	public ModelAndView addPerfil(@PathVariable("secretariaid") Long secretariaid, PerfilSecretaria ps) {

		Secretaria secretaria = secretariaRepository.findById(secretariaid).get();

		ps.setSecretaria(secretaria);

		perfilSecretariaRepository.save(ps);

		ModelAndView model = new ModelAndView("/cadastrosecretaria");
		model.addObject("secretariaobj", secretaria);

		return model;
	}

	
	
	

}
