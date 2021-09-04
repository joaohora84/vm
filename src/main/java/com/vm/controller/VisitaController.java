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
import com.vm.model.StatusVisita;
import com.vm.model.Visita;
import com.vm.repository.AtivoRepository;
import com.vm.repository.AtivoVisitaRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.repository.VisitaRepository;
import com.vm.repository.VisitadorRepository;

@Controller
public class VisitaController {

	@Autowired
	private VisitaRepository visitaRepository;

	@Autowired
	private VisitadorRepository visitadorRepository;

	@Autowired
	private PrescritorRepository prescritorRepository;

	@Autowired
	private AtivoRepository ativoRepository;

	@Autowired
	private AtivoVisitaRepository ativoVisitaRepository;
	
	final int QTD_PAGINAS = 20;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastrovisita")
	public ModelAndView inicio() {

		ModelAndView model = new ModelAndView("/cadastrovisita");

		model.addObject("visitaobj", new Visita());
		model.addObject("visitadores", visitadorRepository.findAll());
		model.addObject("prescritores", prescritorRepository.findAll());

		return model;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarvisita")
	public ModelAndView salvar(Visita visita) {

		
			
	    visita.setAtivo_visita(ativoVisitaRepository.getAtivoVisita(visita.getIdvisita()));
			
		

		ModelAndView model = new ModelAndView("/cadastrovisita");

		try {

			Date data = new Date(System.currentTimeMillis());

			visita.setData_alteracao(data);

			if (visita.getData_cadastro() == null) {

				visita.setData_cadastro(data);

				visitaRepository.save(visita);

			} else {

				visitaRepository.save(visita);

			}

			Long especPrescritorVisita = visita.getPrescritor().getEspecialidade().getIdespecialidade();

			List<Ativo> ativosPorEspecialidade = ativoRepository.getAtivoPorEspecialide(especPrescritorVisita);

			model.addObject("visitaobj", visita);
			model.addObject("msgSucesso", "Visita salva com sucesso!");
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativos", ativosPorEspecialidade);
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(visita.getIdvisita()));

		} catch (Exception e) {

			model.addObject("visitaobj", new Visita());
			model.addObject("msgErro", e.getMessage());
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(visita.getIdvisita()));

		}

		return model;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/agenda")
	public ModelAndView visitas() {

		ModelAndView model = new ModelAndView("/agenda");
		model.addObject("visita", visitaRepository.findAll());
		return model;

	}

	@GetMapping("/editarvisita/{idvi}")
	public ModelAndView editar(@PathVariable("idvi") Long idvi) {

		ModelAndView model = new ModelAndView("/cadastrovisita");

		Optional<Visita> visita = visitaRepository.findById(idvi);

		try {

			Long especPrescritorVisita = visita.get().getPrescritor().getEspecialidade().getIdespecialidade();

			List<Ativo> ativosPorEspecialidade = ativoRepository.getAtivoPorEspecialide(especPrescritorVisita);

			model.addObject("visitaobj", visita.get());
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(idvi));
			model.addObject("ativos", ativosPorEspecialidade);

		} catch (Exception e) {

			Long especPrescritorVisita = visita.get().getPrescritor().getEspecialidade().getIdespecialidade();

			List<Ativo> ativosPorEspecialidade = ativoRepository.getAtivoPorEspecialide(especPrescritorVisita);

			model.addObject("visitaobj", visita.get());
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(idvi));
			model.addObject("ativos", ativosPorEspecialidade);
			model.addObject("msgErro", e.getCause().getCause().getMessage());

		}

		return model;

	}

	@GetMapping("/removervisita/{idvi}")
	public ModelAndView excluir(@PathVariable("idvi") Long idvi) {

		ModelAndView model = new ModelAndView("/listavisita");

		try {

			visitaRepository.deleteById(idvi);

			model.addObject("visitas", visitaRepository.findAll(PageRequest.of(0, QTD_PAGINAS)));
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("msgExclusao", "Visita removida com sucesso!");
			model.addObject("visitaobj", new Visita());

			return model;

		} catch (Exception e) {

			model.addObject("visitas", visitaRepository.findAll(PageRequest.of(0, QTD_PAGINAS)));
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("visitaobj", new Visita());

			return model;
		}

	}

	

	@GetMapping("/removerativovisita/{avid}")
	public ModelAndView removerAtivoVisita(@PathVariable("avid") Long avid) {

		ModelAndView model = new ModelAndView("/cadastrovisita");

		Optional<AtivoVisita> ativovisita = ativoVisitaRepository.findById(avid);

		Long vid = ativovisita.get().getVisita().getIdvisita();

		Visita visita = visitaRepository.findById(vid).get();

		Long especPrescritorVisita = visita.getPrescritor().getEspecialidade().getIdespecialidade();

		List<Ativo> ativosPorEspecialidade = ativoRepository.getAtivoPorEspecialide(especPrescritorVisita);

		try {

			ativoVisitaRepository.deleteById(avid);

			model.addObject("visitaobj", visita);
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(vid));
			model.addObject("msgExclusao", "Ativo removido da visita com sucesso!");
			model.addObject("ativos", ativosPorEspecialidade);

		} catch (Exception e) {

			model.addObject("visitaobj", visita);
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(vid));
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("ativos", ativosPorEspecialidade);

		}

		return model;

	}

	@PostMapping("/buscarativo/{idvi}")
	public ModelAndView pesquisarAtivo(@PathVariable("idvi") Long idvi, @RequestParam("nome") String nome,
			@PageableDefault(size = QTD_PAGINAS, sort = { "nome" }) Pageable pageable) {

		Page<Ativo> ativos = null;

		Visita visita = visitaRepository.findById(idvi).get();

		ativos = ativoRepository.findAtivoByNamePage(nome, pageable);

		String retorno = "retorno";

		ModelAndView model = new ModelAndView("/cadastrovisita");
		model.addObject("ativos", ativos);
		model.addObject("retorno", retorno);
		model.addObject("visitaobj", visita);
		model.addObject("visitadores", visitadorRepository.findAll());
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(idvi));

		return model;

	}

	@PostMapping("**/addativovisita/{visitaid}")
	public ModelAndView addAtivoVisita(AtivoVisita ativoVisita, @PathVariable("visitaid") Long visitaid,
			@RequestParam("ativoid") List<Ativo> ativoid) {

		ModelAndView model = new ModelAndView("/cadastrovisita");

		Visita visita = visitaRepository.findById(visitaid).get();

		Long especPrescritorVisita = visita.getPrescritor().getEspecialidade().getIdespecialidade();

		List<Ativo> ativosPorEspecialidade = ativoRepository.getAtivoPorEspecialide(especPrescritorVisita);

		try {

			List<Ativo> ativo = ativoid;

			List<AtivoVisita> av = new ArrayList<>();

			for (int i = 0; i < ativo.size(); i++) {

				AtivoVisita ativovisita = new AtivoVisita();

				ativovisita.setAtivo(ativo.get(i));
				ativovisita.setVisita(visita);

				av.add(ativovisita);

			}

			ativoVisitaRepository.saveAll(av);

			model.addObject("visitaobj", visita);
			model.addObject("msgSucesso", "Ativo adicionada com sucesso!");
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(visitaid));
			model.addObject("ativos", ativosPorEspecialidade);

		} catch (Exception e) {

			model.addObject("visitaobj", new Visita());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("visitadores", visitadorRepository.findAll());
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.getAtivoVisita(visitaid));
			model.addObject("ativos", ativosPorEspecialidade);

		}

		return model;

	}
	
	@PostMapping("**/pesquisarvisita")
	public ModelAndView pesquisar(@RequestParam("prescritor") Long prescritor,
			@RequestParam("statusvisita") StatusVisita statusvisita, @PageableDefault(size = QTD_PAGINAS) Pageable pageable) {

		ModelAndView model = new ModelAndView("/listavisita");

		Page<Visita> visitas = null;

		if (prescritor != null && statusvisita != null) {

			visitas = visitaRepository.getVisitaPrescritorAndStatus(prescritor, statusvisita, pageable);

		} else if (statusvisita != null && prescritor == null) {

			visitas = visitaRepository.findVisitaByStatus(statusvisita, pageable);

		} else if (statusvisita == null && prescritor != null) {

			visitas = visitaRepository.getVisitaPorPrescritor(prescritor, pageable);

		} else {

			visitas = visitaRepository.findAll(pageable);

		}

		model.addObject("visitas", visitas);
		model.addObject("prescritores", prescritorRepository.findAll());

		return model;

	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/listarvisitas")
	public ModelAndView listarvisitas() {

		ModelAndView model = new ModelAndView("/listavisita");
		model.addObject("visitas", visitaRepository.findAll(PageRequest.of(0, QTD_PAGINAS)));
		model.addObject("prescritores", prescritorRepository.findAll());
		model.addObject("ativosvisita", ativoVisitaRepository.findAll());
		
		return model;

	}

	@GetMapping("/visitapage")
	public ModelAndView carregaVisitaPorPaginacao(@PageableDefault(size = QTD_PAGINAS) Pageable pageable, ModelAndView model) {

		Page<Visita> pageVisita = visitaRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5));
		model.addObject("visitas", pageVisita);
		model.setViewName("/listavisita");
		model.addObject("prescritores", prescritorRepository.findAll());

		return model;

	}
	
	@GetMapping("/concluirvisita/{idvi}")
	public ModelAndView concluirVisita(@PathVariable("idvi") Long idvi) {
		
		ModelAndView model = new ModelAndView("/listavisita");
		
		try {			
			
			Optional<Visita> visita = visitaRepository.findById(idvi);	
			
			visita.get().setStatusvisita(StatusVisita.REALIZADA);
			
			visitaRepository.save(visita.get());
			
			model.addObject("visitas", visitaRepository.findAll(PageRequest.of(0, QTD_PAGINAS)));
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.findAll());
			model.addObject("msgConclusao", "Visita código: " + idvi + "Concluida com sucesso."); 
			
		} catch (Exception e) {
			
			model.addObject("visitas", visitaRepository.findAll(PageRequest.of(0, QTD_PAGINAS)));
			model.addObject("prescritores", prescritorRepository.findAll());
			model.addObject("ativosvisita", ativoVisitaRepository.findAll());
			model.addObject("msgErro", e.getCause().getCause().getMessage());
			
		}
		
	
		return model;
		
	}
	
	

}
