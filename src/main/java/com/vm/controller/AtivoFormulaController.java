package com.vm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Ativo;
import com.vm.model.AtivoFormula;
import com.vm.model.Formula;
import com.vm.repository.AtivoFormulaRepository;
import com.vm.repository.AtivoRepository;
import com.vm.repository.FormulaRepository;
import com.vm.repository.UnidadeMedidaRepository;

@Controller
public class AtivoFormulaController {
	
	@Autowired
	private AtivoFormulaRepository ativoFormulaRepository;
	
	@Autowired
	private FormulaRepository formulaRepository;
	
	@Autowired
	private AtivoRepository ativoRepository;
	
	@Autowired
	private UnidadeMedidaRepository unidadeRepository;
	
	final int QUANTIDADE_ITENS_PAGINA = 20;

	
	@GetMapping("/cadastroativoformula/{idform}")
	public ModelAndView inicio(@PathVariable("idform") Long idform) {
		
		ModelAndView model = new ModelAndView("/cadastroativoformula");
		
		Optional<Formula> formula = formulaRepository.findById(idform);
		
		model.addObject("ativo_formulaobj", new AtivoFormula());
	    model.addObject("formulaobj", formula.get());
	    model.addObject("ativosformula", ativoFormulaRepository.getAtivoFormula(idform));
	   
		
		return model;
		
		
	}
	
	@PostMapping("/buscarativoformula/{idform}")
	public ModelAndView pesquisarAtivo(@PathVariable("idform") Long idform, @RequestParam("nome") String nome,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA, sort = { "nome" }) Pageable pageable) {
		
			ModelAndView model = new ModelAndView("/cadastroativoformula");
		
			Page<Ativo> ativos = null;
	
			Optional<Formula> formula = formulaRepository.findById(idform);
			
			ativos = ativoRepository.findAtivoByNamePage(nome, pageable);

			String retorno = "retorno";

			
			model.addObject("ativos", ativos);
			model.addObject("retorno", retorno);	
			model.addObject("formulaobj", formula.get());
			model.addObject("ativosformula", ativoFormulaRepository.getAtivoFormula(idform));
			model.addObject("unidades", unidadeRepository.findAll());
						

			return model;
		

	}
	
	@PostMapping("**/addativoformula/{idform}")
	public ModelAndView addAtivoFormula(AtivoFormula ativoFormula, @PathVariable("idform") Long idform,
			@RequestParam("ativoid") Long ativoid) {

		ModelAndView model = new ModelAndView("/cadastroativoformula");

		Formula formula = formulaRepository.findById(idform).get();
		Ativo ativo = ativoRepository.findById(ativoid).get();
		double dosagem_usual = ativo.getDosagem_usual();

		try {

			if (ativoFormula.getDosagem() == 0) {

				ativoFormula.setDosagem(dosagem_usual);

			}

			ativoFormula.setAtivo(ativo);

			ativoFormula.setFormula(formula);

			ativoFormulaRepository.save(ativoFormula);

			model.addObject("formulaobj", formula);
			model.addObject("msgSucesso", "Ativo adicionada com sucesso!");
			model.addObject("ativosformula", ativoFormulaRepository.getAtivoFormula(idform));

		} catch (Exception e) {

			model.addObject("formulaobj", formula);
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("ativosformula", ativoFormulaRepository.getAtivoFormula(idform));

		}

		return model;

	}
	
	@GetMapping("/removerativoformula/{afid}")
	public ModelAndView removerAtivoFormula(@PathVariable("afid") Long afid) {

		ModelAndView model = new ModelAndView("/cadastroativoformula");

		Optional<AtivoFormula> ativoformula = ativoFormulaRepository.findById(afid);

		Long fid = ativoformula.get().getFormula().getIdformula();

		Formula formula = formulaRepository.findById(fid).get();

		ativoFormulaRepository.deleteById(afid);
		
		
		model.addObject("formulaobj", formula);
		model.addObject("ativosformula", ativoFormulaRepository.getAtivoFormula(fid));
		model.addObject("msgExclusao", "Ativo removido da fórmula com sucesso!");

		return model;

	}
	
	
	

}
