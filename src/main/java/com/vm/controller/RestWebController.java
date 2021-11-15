package com.vm.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.model.Event;
import com.vm.model.Visita;
import com.vm.repository.VisitaRepository;

@RestController
@RequestMapping("/api/event")
public class RestWebController {
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@GetMapping(value = "/all")
	public String getEvents() throws JsonProcessingException {
try {
	
	String jsonMsg = null;

	List<Visita> visitas = visitaRepository.findAll();

	List<Event> events = new ArrayList<Event>();

	if (!visitas.isEmpty()) {

		for (Visita v : visitas) {
			
			
			String title = "\n" + "Prescritor: " + v.getPrescritor().getNome() + "\n" + "Visitador: " + v.getVisitador().getNome();

			Date data = v.getData_agendamento();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dataFormatada = dateFormat.format(data);
			

			String horario = v.getHorario();
			
			String horario_fim = v.getHorario_fim();
			String url = "/editarvisita/" + v.getIdvisita();

			Event event = new Event();

			event.setTitle(title);
			event.setStart(dataFormatada + "T" + horario);
			event.setEnd(dataFormatada + "T" + horario_fim);
			event.setUrl(url);

			events.add(event);

			ObjectMapper mapper = new ObjectMapper();
			jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		}

	}

	
	return jsonMsg;
	
} catch (Exception e) {
	
}
return null;
		

	}
	

}
