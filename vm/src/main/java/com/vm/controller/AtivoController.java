package com.vm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.Size;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Ativo;
import com.vm.repository.AtivoFormulaRepository;
import com.vm.repository.AtivoRepository;
import com.vm.repository.AtivoVisitaRepository;
import com.vm.repository.EspecialidadePrescritorRepository;
import com.vm.repository.FormulaRepository;
import com.vm.repository.SugestaoFormulaRepository;
import com.vm.repository.UnidadeMedidaRepository;

@Controller
public class AtivoController {
	
	@Autowired
	private AtivoRepository ativoRepository;
	
	@Autowired
	private EspecialidadePrescritorRepository epRepository;
	
	@Autowired
	private FormulaRepository formulaRepository;
	
	@Autowired
	private UnidadeMedidaRepository unidadeRepository;
	
	@Autowired
	private SugestaoFormulaRepository sugestaoFormulaRepository;
	
	@Autowired
	private AtivoVisitaRepository ativoVisitaRepository;
	
	@Autowired
	private AtivoFormulaRepository ativoFormulaRepository;
	
	@GetMapping("/cadastroativo")
	public ModelAndView inicio() {
		
		ModelAndView model = new ModelAndView("/cadastroativo");
		
		model.addObject("ativoobj", new Ativo());
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("unidades", unidadeRepository.findAll());
		
		return model;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarativo", consumes = {"multipart/form-data"})
	public ModelAndView salvar(Ativo ativo, final MultipartFile file) {
		
		ativo.setSugestao_formulas(sugestaoFormulaRepository.getSugestaoFormula(ativo.getIdativo()));
		
		ativo.setAtivo_visita(ativoVisitaRepository.getAtivoVisita(ativo.getIdativo()));
		
		ativo.setAtivo_formula(ativoFormulaRepository.getAtivoFormula(ativo.getIdativo()));

		ModelAndView model = new ModelAndView("/cadastroativo");
		
		try {

			Date data = new Date(System.currentTimeMillis());	

			ativo.setData_alteracao(data);
			
			if (file.getSize() > 0) {
				
				ativo.setFichatecnica(file.getBytes());
				ativo.setTipofichatecnica(file.getContentType());
				ativo.setNomefichatecnica(file.getOriginalFilename());
				
			}else {
				if(ativo.getIdativo() != null && ativo.getIdativo() > 0) {
					
					Ativo ativoTemp = ativoRepository.findById(ativo.getIdativo()).get();
					
					ativo.setFichatecnica(ativoTemp.getFichatecnica());
					ativo.setTipofichatecnica(ativoTemp.getTipofichatecnica());
					ativo.setNomefichatecnica(ativoTemp.getNomefichatecnica());
					
				}
			}

			if (ativo.getData_cadastro() == null) {

				ativo.setData_cadastro(data);

				ativoRepository.save(ativo);

			} else {

				ativoRepository.save(ativo);
			}

			model.addObject("msgSucesso", "Ativo salvo com sucesso!");
			model.addObject("ativoobj", ativo);
			model.addObject("especialidades", epRepository.findAllByOrderBy());
			model.addObject("unidades", unidadeRepository.findAll());

		} catch (Exception e) {

			//model.addObject("msgErro", e.getCause().getCause().getMessage());
			model.addObject("ativoobj", new Ativo());
			model.addObject("especialidades", epRepository.findAllByOrderBy());
			model.addObject("unidades", unidadeRepository.findAll());

		}

		return model;

	}
	
	@GetMapping("/editarativo/{idativ}")
	public ModelAndView editar(@PathVariable("idativ") Long idativ) {

		ModelAndView model = new ModelAndView("/cadastroativo");
		
		
		Optional<Ativo> ativo = ativoRepository.findById(idativ);
		
		model.addObject("ativoobj", ativo.get());
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("unidades", unidadeRepository.findAll());

		return model;

	}
	
	@GetMapping("/removerativo/{idativ}")
	public ModelAndView excluir(@PathVariable("idativ") Long idativ) {

		ativoRepository.deleteById(idativ);
						
		ModelAndView model = new ModelAndView("/listaativo");
		model.addObject("ativos", ativoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("msgExclusao", "Ativo excluida com sucesso!");
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		
		return model;

	}
	
	@PostMapping("**/pesquisarativo")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("especialidade") Long especialidade,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Ativo> ativos = null;

		if (especialidade != null) {

			ativos = ativoRepository.getAtivoByEspecialidadeNome(especialidade, nomepesquisa, pageable);

		} else {

			ativos = ativoRepository.getAtivoByName(nomepesquisa, pageable);

		}

		ModelAndView model = new ModelAndView("/listaativo");
		model.addObject("ativos", ativos);
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("nomepesquisa", nomepesquisa);

		return model;

	}
	
		
	@GetMapping("/listarativos")
	public ModelAndView ativos() {

		ModelAndView model = new ModelAndView("/listaativo");
		model.addObject("ativos", ativoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		return model;

	}
	
	@GetMapping("/")
	public ModelAndView ativosNovos() {

		ModelAndView model = new ModelAndView("/index");
		
	    model.addObject("ativos", ativoRepository.getAtivoNovo());
	    model.addObject("especialidades", epRepository.findAll());
		
		
		return model;

	}
	
	
	
	@GetMapping("/ativopage")
	public ModelAndView carregaAtivoPorPaginacao(@PageableDefault(size = 5) Pageable pageable, ModelAndView model) {

		Page<Ativo> pageAtivo = ativoRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("ativos", pageAtivo);
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.setViewName("/listaativo");

		return model;

	}
	
	@GetMapping("/catalogoativos")
	public ModelAndView catalogoativos() {

		ModelAndView model = new ModelAndView("/catalogoativos");
		model.addObject("ativos", ativoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		return model;

	}
	
	@GetMapping("/catalogoativoscompleto")
	public ModelAndView catalogoativoscompleto() {

		ModelAndView model = new ModelAndView("/catalogoativoscompleto");
		model.addObject("ativos", ativoRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		return model;

	}
	
	@GetMapping("/ativopagecatalogo")
	public ModelAndView carregaAtivoPorPaginacaoCatalogo(@PageableDefault(size = 5) Pageable pageable, ModelAndView model) {

		Page<Ativo> pageAtivo = ativoRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("nome")));
		model.addObject("ativos", pageAtivo);
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.setViewName("/catalogoativos");

		return model;

	}
	
	@PostMapping("**/pesquisarativocatalogo")
	public ModelAndView pesquisarAtivoCatalogo(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("especialidade") Long especialidade,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Ativo> ativos = null;

		if (especialidade != null) {

			ativos = ativoRepository.getAtivoByEspecialidadeNome(especialidade, nomepesquisa, pageable);

		} else {

			ativos = ativoRepository.getAtivoByName(nomepesquisa, pageable);

		}

		ModelAndView model = new ModelAndView("/catalogoativos");
		model.addObject("ativos", ativos);
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("nomepesquisa", nomepesquisa);

		return model;

	}
	
	@PostMapping("**/pesquisarativocatalogocompleto")
	public ModelAndView pesquisarAtivoCatalogoCompleto(@RequestParam("nomepesquisa") String nomepesquisa,
			@RequestParam("especialidade") Long especialidade,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		Page<Ativo> ativos = null;

		if (especialidade != null) {

			ativos = ativoRepository.getAtivoByEspecialidadeNome(especialidade, nomepesquisa, pageable);

		} else {

			ativos = ativoRepository.getAtivoByName(nomepesquisa, pageable);

		}

		ModelAndView model = new ModelAndView("/catalogoativoscompleto");
		model.addObject("ativos", ativos);
		model.addObject("especialidades", epRepository.findAllByOrderBy());
		model.addObject("nomepesquisa", nomepesquisa);

		return model;

	}

	
	
	@GetMapping("**/downloadfile/{idativ}")
	public void downloadFile(@PathVariable("idativ") Long idativ, HttpServletResponse response) throws IOException {
		
		Ativo ativo = ativoRepository.findById(idativ).get();
		
		if (ativo.getFichatecnica() != null) {
			
			response.setContentLength(ativo.getFichatecnica().length);
			
			response.setContentType(ativo.getTipofichatecnica());
			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", ativo.getNomefichatecnica());
			response.setHeader(headerKey, headerValue);
			
			response.getOutputStream().write(ativo.getFichatecnica());
			
			
		}
		
	}
	
	
	
	

}
