package com.vm.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.EspecialidadePrescritor;
import com.vm.model.Prescritor;
import com.vm.repository.ClinicaRepository;
import com.vm.repository.EspecialidadePrescritorRepository;

@Controller
public class EspecialidadeMedicaController {

	@Autowired
	private EspecialidadePrescritorRepository epRepository;
	
	@Autowired
	private ClinicaRepository clinicaRepository;
	

	@GetMapping("/cadastroespecialidade")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroespecialidade");

		model.addObject("especialidadeobj", new EspecialidadePrescritor());

		return model;

	}

	@PostMapping("**/salvarespecialidade/{espec}")
	public ModelAndView salvar(EspecialidadePrescritor especialidade,
			Prescritor prescritor,
			@RequestParam("espec") String espec) {

		ModelAndView model = new ModelAndView("/cadastroprescritor");
		
		EspecialidadePrescritor Especialidade = epRepository.getEspecialidadeByName(espec);
			
		if(Especialidade == null) {
			
			
			especialidade.setNome(espec);
			
			epRepository.save(especialidade);
			
			
			
		} 
		
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("clinicas", clinicaRepository.findAllByOrderBy());
		model.addObject("prescritorobj", new Prescritor());

	

		return model;

	}

}
