package com.vm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vm.model.Formula;
import com.vm.model.Prescritor;
import com.vm.model.StatusVisita;
import com.vm.model.Visita;
import com.vm.model.Visitador;
import com.vm.repository.FormulaRepository;
import com.vm.repository.PrescritorRepository;
import com.vm.repository.VisitaRepository;
import com.vm.repository.VisitadorRepository;

@Controller
public class RelatoriosController {
	
	@Autowired
	private PrescritorRepository prescritorRepository;
	
	@Autowired
	private FormulaRepository formulaRepository;
	
	@Autowired
	private VisitadorRepository visitadorRepository;
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
/**RELATORIOS FORMULA**/
	
	@RequestMapping(method = RequestMethod.GET, value = "/formula_relatorios")
	public ModelAndView relatorios() {

		ModelAndView model = new ModelAndView("/formula_relatorios");
		
		return model;

	}
	
	@GetMapping("/allformulas")
	public void imprimePdfFormula(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Formula> formulas = new ArrayList<Formula>();
		
		formulas = formulaRepository.findAll();		
		
		
		byte[] pdf = reportUtil.gerarRelatorio(formulas, "allformulas", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(pdf);
		
	}
	
/**RELATORIOS VISITADOR**/
	
	@RequestMapping(method = RequestMethod.GET, value = "/visitador_relatorios")
	public ModelAndView relatoriosvisitador() {

		ModelAndView model = new ModelAndView("/visitador_relatorios");
		return model;

	}
	
	@GetMapping("/allvisitadores")
	public void imprimePdfVisitador(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Visitador> visitadores = new ArrayList<Visitador>();
		
		visitadores = visitadorRepository.findAll();		
		
		
		byte[] pdf = reportUtil.gerarRelatorio(visitadores, "allvisitadores", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(pdf);
		
	}
	
/**RELATORIOS VISITA**/
	
	@RequestMapping(method = RequestMethod.GET, value = "/visita_relatorios")
	public ModelAndView relatoriosVisita() {

		ModelAndView model = new ModelAndView("/visita_relatorios");
		
		
		model.addObject("prescritores", prescritorRepository.findAll());
		
		return model;

	}
	
	@GetMapping("/allvisitas")
	public void imprimePdfVisita(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Visita> visitas = new ArrayList<Visita>();
		
		visitas = visitaRepository.findAll();		
		
		
		byte[] pdf = reportUtil.gerarRelatorio(visitas, "visita", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(pdf);
		
	}
	
	@GetMapping("/visitasporperiodo")
	public void imprimePdfVisitaPeriodo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("data_inicio") String data_inicio, @RequestParam("data_fim") String data_fim)
			throws Exception {

		SimpleDateFormat dataf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dataf2 = new SimpleDateFormat("dd/MM/yyyy");

		Date dataInicio = dataf1.parse(data_inicio);
		Date dataFim = dataf2.parse(data_fim);

		List<Visita> visitas = new ArrayList<Visita>();

		visitas = visitaRepository.findVisitaByPeriodo(dataInicio, dataFim);

		byte[] pdf = reportUtil.gerarRelatorio(visitas, "visita", request.getServletContext());

		response.setContentLength(pdf.length);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");

		response.setHeader(headerKey, headerValue);

		response.getOutputStream().write(pdf);

	}
	
	@GetMapping("/visitasporstatus")
	public void imprimePdfVisitaStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("statusvisita") String statusvisita)
			throws Exception {
		
		System.out.println(statusvisita);	
		
		List<Visita> visitas = new ArrayList<Visita>();

		//visitas = visitaRepository.findVisitaByStatus(statusvisita);

		byte[] pdf = reportUtil.gerarRelatorio(visitas, "visita", request.getServletContext());

		response.setContentLength(pdf.length);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");

		response.setHeader(headerKey, headerValue);

		response.getOutputStream().write(pdf);

	}
	
	@GetMapping("/visitasporvisitador")
	public void imprimePdfVisitaVisitador(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("visitador") String visitador)
			throws Exception {
		
		System.out.println(visitador);	
		
		List<Visita> visitas = new ArrayList<Visita>();

		visitas = visitaRepository.findVisitaByVisitador(visitador);

		byte[] pdf = reportUtil.gerarRelatorio(visitas, "visita", request.getServletContext());

		response.setContentLength(pdf.length);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");

		response.setHeader(headerKey, headerValue);

		response.getOutputStream().write(pdf);

	}
	
	@GetMapping("/visitasporprescritor")
	public void imprimePdfVisitaPrescritor(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("prescritor") Long prescritor)
			throws Exception {
		
				
		//List<Visita> visitas = new ArrayList<Visita>();

		List<Visita> visitas = visitaRepository.findVisitaByPrescritor(prescritor);

		byte[] pdf = reportUtil.gerarRelatorio(visitas, "visita", request.getServletContext());

		response.setContentLength(pdf.length);

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");

		response.setHeader(headerKey, headerValue);

		response.getOutputStream().write(pdf);

	}
	
/**RELATORIOS PRESCRITOR**/
	
	@RequestMapping(method = RequestMethod.GET, value = "/prescritor_relatorios")
	public ModelAndView relatoriosPrescritor() {

		ModelAndView model = new ModelAndView("/prescritor_relatorios");
		
		return model;

	}
			
	
	@GetMapping("/allprescritor")
	public void imprimePdfPrescritor(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Prescritor> prescritores = new ArrayList<Prescritor>();
		
		prescritores = prescritorRepository.findAll();		
		
		
		byte[] pdf = reportUtil.gerarRelatorio(prescritores, "allprescritores", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(pdf);
		
	}
	
	
	

	
	
	
}
