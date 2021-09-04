package com.vm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Ativo;
import com.vm.model.Formula;
import com.vm.model.SugestaoFormula;
import com.vm.model.Visita;
import com.vm.repository.AtivoFormulaRepository;
import com.vm.repository.AtivoRepository;
import com.vm.repository.FormulaRepository;
import com.vm.repository.SugestaoFormulaRepository;
import com.vm.repository.VisitaRepository;

@Controller
public class SugestaoFormulaController {
	
	@Autowired
	private SugestaoFormulaRepository sugestaoFormulaRepository;
	
	@Autowired
	private AtivoRepository ativoRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private FormulaRepository formulaRepository;
	
	@Autowired
	private AtivoFormulaRepository ativoFormulaRepository;
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	final int QTD_PAGINAS = 20;
	
	@GetMapping("/susgestaoformula/{idat}")
	public ModelAndView sugestaoFormula(@PathVariable("idat") Long idat) {
		
		ModelAndView model = new ModelAndView("/sugestaoformula");
		
		Optional<Ativo> ativo = ativoRepository.findById(idat);
		
		List<Formula> formulas = ativoFormulaRepository.getFormulaPorAtivo(idat);
		
		List<SugestaoFormula> sugestaoformula = sugestaoFormulaRepository.getSugestaoFormula(idat);
		
		for (int i = 0; i < sugestaoformula.size(); i++) {
			
			System.out.println(sugestaoformula.get(i).getFormula().getNome());
			
			for (int j = 0; j < sugestaoformula.get(i).getFormula().getAtivo_formula().size(); j++) {
				
				System.out.println(sugestaoformula.get(i).getFormula().getAtivo_formula().get(j).getAtivo().getNome());
				
			}
			
		}
		
				
		model.addObject("ativoobj", ativo.get());
		model.addObject("sugestoesformula", sugestaoformula);
		model.addObject("formulas", formulas);
		
		
		
		
		return model;
		
	}
	
	@PostMapping("/addsugestaoformula/{idat}")
	public ModelAndView addSugestaoFormula(SugestaoFormula sugestaoFormula, @PathVariable("idat") Long idat,
			@RequestParam("formulaid") List<Formula> formulaid) {

		ModelAndView model = new ModelAndView("/sugestaoformula");

		Ativo ativo = ativoRepository.findById(idat).get();

		List<Formula> formulas = ativoFormulaRepository.getFormulaPorAtivo(idat);

		try {

			List<Formula> formula = formulaid;

			List<SugestaoFormula> sf = new ArrayList<>();

			for (int i = 0; i < formula.size(); i++) {

				SugestaoFormula sugestaoformula = new SugestaoFormula();

				sugestaoformula.setFormula(formula.get(i));
				sugestaoformula.setAtivo(ativo);

				sf.add(sugestaoformula);

			}

			sugestaoFormulaRepository.saveAll(sf);

			model.addObject("ativoobj", ativo);
			model.addObject("sugestoesformula", sugestaoFormulaRepository.getSugestaoFormula(idat));
			model.addObject("formulas", formulas);
			model.addObject("msgSucesso", "Sugestão de fórmula adicionada com sucesso");

		} catch (Exception e) {

			model.addObject("ativoobj", ativo);
			model.addObject("sugestoesformula", sugestaoFormulaRepository.getSugestaoFormula(idat));
			model.addObject("formulas", formulas);
			model.addObject("msgErro", e.getCause().getCause().getMessage());

		}

		return model;

	}
	
	
	
	@GetMapping("/removersugestaoformula/{idsug}")
	public ModelAndView excluir(@PathVariable("idsug") Long idsug) {

		ModelAndView model = new ModelAndView("/sugestaoformula");
		
		Optional<SugestaoFormula> sugestaoFormula = sugestaoFormulaRepository.findById(idsug);
		
		Long idat = sugestaoFormula.get().getAtivo().getIdativo();
		
		Ativo ativo = ativoRepository.findById(idat).get();

		List<Formula> formulas = ativoFormulaRepository.getFormulaPorAtivo(idat);

		try {

			sugestaoFormulaRepository.deleteById(idsug);

			
			model.addObject("ativoobj", ativo);
			model.addObject("sugestoesformula", sugestaoFormulaRepository.getSugestaoFormula(idat));
			model.addObject("formulas", formulas);
			model.addObject("msgExclusao", "Sugestão de fórmula removida com sucesso");

			return model;

		} catch (Exception e) {

			model.addObject("ativoobj", ativo);
			model.addObject("sugestoesformula", sugestaoFormulaRepository.getSugestaoFormula(idat));
			model.addObject("formulas", formulas);
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			

			return model;
		}

	}
	
	@GetMapping("/sugestaoformulavisita/ativo/{idat}/visita/{idvis}")
	public ModelAndView sugestaoFormulaVisita(@PathVariable("idat") Long idat, @PathVariable("idvis") Long idvis) {
		
		ModelAndView model = new ModelAndView("/sugestaoformulavisita");
		
		Optional<Visita> visita = visitaRepository.findById(idvis);
		
		Optional<Ativo> ativo = ativoRepository.findById(idat);
		
		List<Formula> formulas = ativoFormulaRepository.getFormulaPorAtivo(idat);
		
		List<SugestaoFormula> sugestaoformula = sugestaoFormulaRepository.getSugestaoFormula(idat);
		
		for (int i = 0; i < sugestaoformula.size(); i++) {
			
			System.out.println(sugestaoformula.get(i).getFormula().getNome());
			
			for (int j = 0; j < sugestaoformula.get(i).getFormula().getAtivo_formula().size(); j++) {
				
				System.out.println(sugestaoformula.get(i).getFormula().getAtivo_formula().get(j).getAtivo().getNome());
				
			}
			
		}
		
		
		model.addObject("visitaobj", visita.get());
		model.addObject("ativoobj", ativo.get());
		model.addObject("sugestoesformula", sugestaoformula);		
		model.addObject("formulas", formulas);
		
		
		
		
		return model;
		
	}

}
