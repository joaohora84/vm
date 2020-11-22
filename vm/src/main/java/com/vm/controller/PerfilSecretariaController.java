package com.vm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.PerfilSecretaria;
import com.vm.model.Secretaria;
import com.vm.repository.PerfilSecretariaRepository;
import com.vm.repository.SecretariaRepository;

@Controller
public class PerfilSecretariaController {
	
	@Autowired
	private PerfilSecretariaRepository pSecretariaRepository;
	
	@Autowired
	private SecretariaRepository secretariaRepository;
	
	@GetMapping("/cadastroperfilsecretaria")
	public ModelAndView inicio() {
		
		ModelAndView model = new ModelAndView("/cadastroperfilsecretaria");
		
		model.addObject("perfilobj", new PerfilSecretaria());
		
		return model;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarperfilsecretaria/{idsec}")
	public ModelAndView salvar(PerfilSecretaria ps, @PathVariable("idsec") Long idsec) {

		Secretaria secretaria = secretariaRepository.findById(idsec).get();
		ps.setSecretaria(secretaria);
		
		String msgSucesso = "Perfil salvo com sucesso!";	

		pSecretariaRepository.save(ps);

		ModelAndView model = new ModelAndView("/cadastroperfilsecretaria");

		model.addObject("perfilobj", ps);
		model.addObject("msgSucesso", msgSucesso);
		model.addObject("secretariaobj", secretaria);

		return model;

	}
	
	

}
