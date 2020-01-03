package com.calculoFolha.CalculoFolha.resources;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calculoFolha.CalculoFolha.models.Employee;
import com.calculoFolha.CalculoFolha.models.Event;
import com.calculoFolha.CalculoFolha.models.Moviment;
import com.calculoFolha.CalculoFolha.repository.EmployeeRepository;
import com.calculoFolha.CalculoFolha.repository.EventRepository;
import com.calculoFolha.CalculoFolha.repository.MovimentRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/calculaFolha")
public class MovimentResource {
	
	@Autowired
	MovimentRepository movimentRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EventRepository eventRepository;
	
	@GetMapping("/moviment")
	public List<Moviment> listaMoviments(){
		return movimentRepository.findAll();
	}
	
	@GetMapping("/moviment/{id}")
	public Moviment listaMovimentUnic(@PathVariable(value="id") long id){
		return movimentRepository.findById(id);
	}
	
	@PostMapping("/moviment")
	public Moviment saveMoviment(@RequestBody @Valid Moviment moviment) {
		return movimentRepository.save(moviment);
	}
	
	@DeleteMapping("/moviment")
	public void deleteMoviment(@RequestBody @Valid Moviment moviment) {
		movimentRepository.delete(moviment);
	}
	
	@PutMapping("/moviment")
	public Moviment updateMoviment(@RequestBody @Valid Moviment moviment) {
		return movimentRepository.save(moviment);
	}
	
	@PostMapping("/movimentCalculo")
	public List<Moviment> CalculaFolha(@RequestBody @Valid Moviment moviment) {
		List<Employee> listaEmployee = employeeRepository.findAll();
		List<Event> listaEvent = eventRepository.findAll();
		List<Moviment> listaMoviment = movimentRepository.findAll();
		BigDecimal big1 = new BigDecimal("0.1");
        BigDecimal big2 = new BigDecimal("0.5");
        BigDecimal bigResult = big1.add(big2);
        Date dataSQL = new Date(System.currentTimeMillis());
		for (Employee employee : listaEmployee) {
			for (Event event : listaEvent) {
				moviment.setIdEmployee(employee);
				moviment.setIdEvent(event);
				moviment.setMonth(dataSQL);
				moviment.setValue(bigResult);
				listaMoviment.add(moviment);
			}
		}
		return listaMoviment;
		
	}

}
