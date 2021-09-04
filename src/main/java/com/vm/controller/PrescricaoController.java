package com.vm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.vm.model.AtivoVisita;
import com.vm.model.Formula;
import com.vm.model.FormulaPrescricao;
import com.vm.model.Prescricao;
import com.vm.model.Visita;
import com.vm.repository.FormulaPrescricaoRepository;
import com.vm.repository.FormulaRepository;
import com.vm.repository.PacienteRepository;
import com.vm.repository.PrescricaoRepository;
import com.vm.repository.PrescritorRepository;

@Controller
public class PrescricaoController {
	
	final int QUANTIDADE_ITENS_PAGINA = 20;
	
	@SuppressWarnings("unused")
	@Autowired
	private PrescricaoRepository prescricaoRepository;
	
	@Autowired
	private PrescritorRepository prescritorRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private FormulaPrescricaoRepository formulaPrescricaoRepository;
	
	@Autowired
	private FormulaRepository formulaRepository;
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroprescricao")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroprescricao");
				
		model.addObject("prescricaoobj", new Prescricao());
		model.addObject("prescritores", prescritorRepository.findAll());
	
		return model;

	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarprescricao")
	public ModelAndView salvar(Prescricao prescricao) {
		
		ModelAndView model = new ModelAndView("/cadastroprescricao");
		
		prescricao.setFormula_prescricao(formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(prescricao.getIdprescricao()));
		
		try {
			
			Date data = new Date(System.currentTimeMillis());
			
			prescricao.setData_alteracao(data);
			
			if (prescricao.getData_cadastro() == null) {
				
				prescricao.setData_cadastro(data);
				
				prescricaoRepository.save(prescricao);
				
			}else {
				
				prescricaoRepository.save(prescricao);
				
			}
			
			
			model.addObject("prescricaoobj", prescricao);
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("msgSucesso", "Prescrição salva com sucesso");
			
		} catch (Exception e) {
			
			model.addObject("prescricaoobj", new Prescricao());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			
		}
		
		
		
		return model;
		
	}
	
	@GetMapping("/editarprescricao/{idpres}")
	public ModelAndView editar(@PathVariable("idpres") Long idpres) {

		ModelAndView model = new ModelAndView("/cadastroprescricao");

		Optional<Prescricao> prescricao = prescricaoRepository.findById(idpres);

		model.addObject("prescricaoobj", prescricao.get());
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("formulasprescricao", formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(idpres));

		return model;

	}

	@GetMapping("/removerprescricao/{idpres}")
	public ModelAndView excluir(@PathVariable("idpres") Long idpres) {

		prescricaoRepository.deleteById(idpres);

		ModelAndView model = new ModelAndView("/listaprescricao");
		model.addObject("prescricoes", prescricaoRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA)));
		model.addObject("msgExclusao", "Prescrição removida com sucesso.");
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("formulasprescricao", formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(idpres));

		return model;

	}

	@PostMapping("**/pesquisarprescricao")
	public ModelAndView pesquisar(@RequestParam("prescritor") Long prescritor,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA) Pageable pageable) {

		Page<Prescricao> prescricoes = null;

		ModelAndView model = new ModelAndView("/listaprescricao");

		try {

			if (prescritor != null) {

				prescricoes = prescricaoRepository.getPrescricaoPorPrescritor(prescritor, pageable);

			} else {

				prescricoes = prescricaoRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA));

			}

			model.addObject("prescricoes", prescricoes);
			model.addObject("prescritores", prescritorRepository.findAll());

		} catch (Exception e) {

			model.addObject("prescricoes", prescricoes = prescricaoRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA)));
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("msgErro", e.getCause().getCause().getMessage());

		}

		return model;

	}
	
	@GetMapping("/listarprescricoes")
	public ModelAndView prescricoes() {

		ModelAndView model = new ModelAndView("/listaprescricao");
		model.addObject("prescricoes", prescricaoRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA)));
		model.addObject("prescritores", prescritorRepository.findAll());
		return model;

	}

	@GetMapping("/prescricaopage")
	public ModelAndView carregaPrescricaoPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
			ModelAndView model) {

		Page<Prescricao> pagePrescricao = prescricaoRepository.findAll(PageRequest.of(pageable.getPageNumber(), QUANTIDADE_ITENS_PAGINA));
		model.addObject("prescricoes", pagePrescricao);
		model.setViewName("/listaprescricao");

		return model;

	}
	
	@PostMapping("/buscarformula/{idpres}")
	public ModelAndView pesquisarFormula(@PathVariable("idpres") Long idpres, @RequestParam("nome") String nome,
			@PageableDefault(size = QUANTIDADE_ITENS_PAGINA, sort = { "nome" }) Pageable pageable) {
		
		ModelAndView model = new ModelAndView("/cadastroprescricao");
		
	
		Page<Formula> formulas = null;

		Optional<Prescricao> prescricao = prescricaoRepository.findById(idpres);
		
		formulas = formulaRepository.findFormulaByNamePage(nome, pageable);

		String retorno = "retorno";

		
		model.addObject("formulas", formulas);
		model.addObject("retorno", retorno);
		model.addObject("prescricaoobj", prescricao.get());		
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("formulasprescricao", formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(idpres));
		

		return model;

	}
	
	@PostMapping("**/addformulaprescricao/{idpres}")
	public ModelAndView addFormulaPrescricao(FormulaPrescricao formulaPrescricao, @PathVariable("idpres") Long idpres,
			@RequestParam("idformula") Long idformula) {

		ModelAndView model = new ModelAndView("/cadastroprescricao");

		Prescricao prescricao = prescricaoRepository.findById(idpres).get();
		
		Formula formula = formulaRepository.findById(idformula).get();
		
		formulaPrescricao.setFormula(formula);
		formulaPrescricao.setPrescricao(prescricao);
		
		formulaPrescricaoRepository.save(formulaPrescricao);
		
		
		model.addObject("prescricaoobj", prescricao);
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("formulasprescricao", formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(idpres));
		return model;

	}
	
	@GetMapping("/removerformulaprescricao/{idfp}")
	public ModelAndView removerFormulaPrescricao(@PathVariable("idfp") Long idfp) {
		
		ModelAndView model = new ModelAndView("/cadastroprescricao");
		
		Optional<FormulaPrescricao> formulaPrescricao = formulaPrescricaoRepository.findById(idfp);
		
		Optional<Prescricao> prescricao = prescricaoRepository.findById(formulaPrescricao.get().getPrescricao().getIdprescricao());
		
		formulaPrescricaoRepository.deleteById(idfp);
		
		model.addObject("prescricaoobj", prescricao.get());
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("formulasprescricao", formulaPrescricaoRepository.getFormulaPrescricaoPorPrescricao(prescricao.get().getIdprescricao()));
		return model;
		
	}

	
	

}
