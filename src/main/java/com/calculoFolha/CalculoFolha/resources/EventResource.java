package com.calculoFolha.CalculoFolha.resources;

import javax.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.calculoFolha.CalculoFolha.repository.EventRepository;

import com.calculoFolha.CalculoFolha.models.Event;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/calculaFolha")
public class EventResource {
	
	@Autowired
	EventRepository eventRepository;

	@GetMapping("/event")
	public List<Event> listaEvents(){
		return eventRepository.findAll();
	}
	
	@GetMapping("/event/{id}")
	public Event listaEventUnic(@PathVariable(value="id") long id){
		return eventRepository.findById(id);
	}
	
	@PostMapping("/event")
	public Event saveEvent(@RequestBody @Valid Event event) {
		System.out.println("Aqui");
		System.out.println(event.getName());
		return eventRepository.save(event);
	}
	
	@DeleteMapping("/event")
	public void deleteEvent(@RequestBody @Valid Event event) {
		eventRepository.delete(event);
	}
	
	@PutMapping("/event")
	public Event updateEvent(@RequestBody @Valid Event event) {
		return eventRepository.save(event);
	}
	
}
