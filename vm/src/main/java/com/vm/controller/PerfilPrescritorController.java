package com.vm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.PerfilPrescritor;
import com.vm.model.Prescritor;
import com.vm.repository.PerfilPrescritorRepository;
import com.vm.repository.PrescritorRepository;

@Controller
public class PerfilPrescritorController {
	
	@SuppressWarnings("unused")
	@Autowired
	private PerfilPrescritorRepository perfilPrescritorRepository;
	
	@Autowired
	private PrescritorRepository prescritorRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroperfilprescritor")
	public ModelAndView inicio() {
		
			

		ModelAndView model = new ModelAndView("/cadastroperfilprescritor");
		
		model.addObject("perfilobj", new PerfilPrescritor());

		return model;

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarperfil/{idpres}")
	public ModelAndView salvar(PerfilPrescritor pp, @PathVariable("idpres") Long idpres) {

		Prescritor prescritor = prescritorRepository.findById(idpres).get();
		pp.setPrescritor(prescritor);
		
		String msgSucesso = "Perfil salvo com sucesso!";	

		perfilPrescritorRepository.save(pp);

		ModelAndView model = new ModelAndView("/cadastroperfilprescritor");

		model.addObject("perfilobj", pp);
		model.addObject("msgSucesso", msgSucesso);
		model.addObject("prescritorobj", prescritor);

		return model;

	}
	
	

}
