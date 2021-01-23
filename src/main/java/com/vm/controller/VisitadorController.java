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

import com.vm.model.Visitador;
import com.vm.repository.VisitadorRepository;

@Controller
public class VisitadorController {

	@Autowired
	private VisitadorRepository visitadorRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastrovisitador")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastrovisitador");

		model.addObject("visitadorobj", new Visitador());

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarvisitador")
	public ModelAndView salvar(Visitador visitador) {
		
		try {
			
			Date data = new Date(System.currentTimeMillis());
			
			visitador.setData_alteracao(data);

			if (visitador.getData_cadastro() == null) {

				visitador.setData_cadastro(data);

				visitadorRepository.save(visitador);

			} else {

				visitadorRepository.save(visitador);

			}

			ModelAndView model = new ModelAndView("/cadastrovisitador");
			model.addObject("msgSucesso", "Visitador salvo com sucesso!");
			model.addObject("visitadorobj", visitador);

			return model;
			
		} catch (Exception e) {
			
			ModelAndView model = new ModelAndView("/cadastrovisitador");
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("visitadorobj", new Visitador());
			
			return model;
			
		}

	}

	@GetMapping("/editarvisitador/{idv}")
	public ModelAndView editar(@PathVariable("idv") Long idv) {

		ModelAndView model = new ModelAndView("/cadastrovisitador");
		Optional<Visitador> visitador = visitadorRepository.findById(idv);
		model.addObject("visitadorobj", visitador.get());

		return model;

	}

	@GetMapping("/removervisitador/{idv}")
	public ModelAndView excluir(@PathVariable("idv") Long idv) {

		visitadorRepository.deleteById(idv);
		
		ModelAndView model = new ModelAndView("/listavisitador");
		model.addObject("visitadores", visitadorRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("msgExclusao", "Visitador excluido com sucesso!");
		model.addObject("visitadorobj", new Visitador());

		return model;

	}

	@PostMapping("**/pesquisarvisitador")
	public ModelAndView pesquisar(@RequestParam("nome") String nome,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Visitador> visitadores = null;

		visitadores = visitadorRepository.findVisitadorByNamePage(nome, pageable);

		ModelAndView model = new ModelAndView("/listavisitador");
		model.addObject("visitadores", visitadores);
		model.addObject("visitadorobj", new Visitador());

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarvisitadores")
	public ModelAndView visitadores() {

		ModelAndView modelAndView = new ModelAndView("/listavisitador");
		modelAndView.addObject("visitadores", visitadorRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;

	}

	@GetMapping("/visitadorpage")
	public ModelAndView carregaPrescritorPorPaginacao(@PageableDefault(size = 5) Pageable pageable,
			ModelAndView model) {

		Page<Visitador> pageVisitador = visitadorRepository
				.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("visitadores", pageVisitador);
		model.setViewName("/listavisitador");

		return model;

	}

}
