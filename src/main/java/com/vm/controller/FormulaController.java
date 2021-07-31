package com.vm.controller;

import java.util.Date;
import java.util.List;
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

import com.vm.model.Formula;
import com.vm.repository.AtivoFormulaRepository;
import com.vm.repository.EspecialidadePrescritorRepository;
import com.vm.repository.FormaFarmaceuticaRepository;
import com.vm.repository.FormulaPrescricaoRepository;
import com.vm.repository.FormulaRepository;
import com.vm.repository.FormulaVisitaRepository;
import com.vm.repository.UnidadeMedidaRepository;

@Controller
public class FormulaController {

	@Autowired
	private FormulaRepository formulaRepository;

	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;

	@Autowired
	private EspecialidadePrescritorRepository especialidadePrescritorRepository;

	@Autowired
	private FormulaVisitaRepository formulaVisitaRepository;

	@Autowired
	private AtivoFormulaRepository ativoFormulaRepository;

	@Autowired
	private FormulaPrescricaoRepository formulaPrescricaoRepository;

	@Autowired
	private FormaFarmaceuticaRepository formaFarmaceticaRepository;
	
	final int QUANTIDADE_ITENS_PAGINA = 20;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroformula")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastroformula");

		model.addObject("formulaobj", new Formula());
		model.addObject("unidades", unidadeMedidaRepository.findAllByOrderBy());
		model.addObject("especialidades", especialidadePrescritorRepository.findAll());
		model.addObject("forma_farmaceuticas", formaFarmaceticaRepository.findAll());

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarformula")
	public ModelAndView salvar(Formula formula) {

		formula.setFormula_visita(formulaVisitaRepository.getFormulaVisitaF(formula.getIdformula()));

		formula.setFormula_prescricao(formulaPrescricaoRepository.getFormulaPrescricao(formula.getIdformula()));

		formula.setAtivo_formula(ativoFormulaRepository.getAtivoFormula(formula.getIdformula()));

		try {

			Date data = new Date(System.currentTimeMillis());

			formula.setData_alteracao(data);

			if (formula.getData_cadastro() == null) {

				formula.setData_cadastro(data);

				formulaRepository.save(formula);

			} else {

				formulaRepository.save(formula);

			}

			Long idform = formula.getIdformula();

			ModelAndView model = new ModelAndView("/cadastroformula");

			model.addObject("especialidades", especialidadePrescritorRepository.findAll());

			model.addObject("forma_farmaceuticas", formaFarmaceticaRepository.findAll());
			model.addObject("msgSucesso", "Fórmula salva com sucesso!");
			model.addObject("formulaobj", formula);

			return model;

		} catch (Exception e) {

			ModelAndView model = new ModelAndView("/cadastroformula");
			model.addObject("especialidades", especialidadePrescritorRepository.findAll());

			model.addObject("forma_farmaceuticas", formaFarmaceticaRepository.findAll());
			model.addObject("msgErro", e.getMessage());
			model.addObject("formulaobj", new Formula());
			System.out.println("ERRO: " + e.getMessage());

			return model;

		}
	}

	@GetMapping("/editarformula/{idform}")
	public ModelAndView editar(@PathVariable("idform") Long idform) {

		ModelAndView model = new ModelAndView("/cadastroformula");

		Optional<Formula> formula = formulaRepository.findById(idform);

		model.addObject("formulaobj", formula.get());

		model.addObject("unidades", unidadeMedidaRepository.findAll());
		model.addObject("especialidades", especialidadePrescritorRepository.findAll());

		model.addObject("forma_farmaceuticas", formaFarmaceticaRepository.findAll());

		return model;

	}

	@GetMapping("/removerformula/{idform}")
	public ModelAndView excluir(@PathVariable("idform") Long idform) {

		String msgExclusao = "Fórmula excluida com sucesso!";

		formulaRepository.deleteById(idform);
		ModelAndView model = new ModelAndView("/listaformula");
		model.addObject("formulas", formulaRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));
		model.addObject("msgExclusao", msgExclusao);
		model.addObject("formulaobj", new Formula());

		return model;

	}

	@PostMapping("**/pesquisarformula")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Formula> formulas = null;

		formulas = formulaRepository.findFormulaByNamePage(nome, pageable);

		ModelAndView model = new ModelAndView("/listaformula");
		model.addObject("formulas", formulas);
		model.addObject("formulaobj", new Formula());

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarformulas")
	public ModelAndView formulas() {

		ModelAndView modelAndView = new ModelAndView("/listaformula");
		modelAndView.addObject("formulas", formulaRepository.findAll(PageRequest.of(0, QUANTIDADE_ITENS_PAGINA, Sort.by("nome"))));

		return modelAndView;

	}

	@GetMapping("/formulapage")
	public ModelAndView carregaFormulaPorPaginacao(@PageableDefault(size = QUANTIDADE_ITENS_PAGINA) Pageable pageable, ModelAndView model) {

		Page<Formula> pageFormula = formulaRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), QUANTIDADE_ITENS_PAGINA, Sort.by("nome")));
		model.addObject("formulas", pageFormula);
		model.setViewName("/listaformula");

		return model;

	}

	@GetMapping("/buscarformulas/{idcat}")
	public ModelAndView buscarFormulasPorCategoria(@PathVariable("idcat") Long idcat) {

		ModelAndView model = new ModelAndView("/catalogoformulas");

		List<Formula> formulas = null;

		try {

			model.addObject("formulas", formulas);

		} catch (Exception e) {
			model.addObject("formulas", formulaRepository.findAll());
			model.addObject("msgErro", e.getMessage());

		}

		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/catalogoformulas")
	public ModelAndView catalogoformulas() {

		List<Formula> formulas = formulaRepository.findAll();

		ModelAndView model = new ModelAndView("/catalogoformulas");
		model.addObject("formulas", formulas);

		return model;

	}

	@GetMapping("/formulacompleta/{idform}")
	public ModelAndView formulacompleta(@PathVariable("idform") Long idform) {

		Formula formula = formulaRepository.findById(idform).get();

		ModelAndView model = new ModelAndView("/catalogoinsumos");

		model.addObject("formulaobj", formula);

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/catalogoinsumos")
	public ModelAndView catalogoinsumos() {

		ModelAndView model = new ModelAndView("/catalogoinsumos");

		return model;

	}

}
