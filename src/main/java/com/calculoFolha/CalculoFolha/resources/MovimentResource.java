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
	
	@PostMapping("/movimentCalculo/{mes}")
	public String CalculaFolha(@PathVariable(value="mes") int mes) {
		List<Employee> listaEmployee = employeeRepository.findAll();
		List<Event> listaEvent = eventRepository.findAll();
		List<Moviment> listaMoviment = movimentRepository.findAll();
		System.out.println(mes);
		System.out.println();
		BigDecimal big1 = new BigDecimal("1.1");
        BigDecimal big2 = new BigDecimal("5.5");
        BigDecimal bigResult = big1.add(big2);
        Date data = new Date(2020, mes, 01);
		for (Employee employee : listaEmployee) {
			for (Event event : listaEvent) {
				Moviment moviment = new Moviment();
				moviment.setIdEmployee(employee);
				moviment.setIdEvent(event);
				moviment.setMonth(data);
				moviment.setValue(bigResult);
				switch ((int)event.getId()) {
				case 5:
					System.out.println("Salário");
					moviment.setValue(employee.getSalary());
					break;
				case 6:
					System.out.println("Hora extra");
					moviment.setValue(employee.getSalary());
					break;
				case 7:
					System.out.println("Hora falta");
					moviment.setValue(employee.getSalary());
					break;
				case 8:
					System.out.println("INSS");
					moviment.setValue(employee.getSalary());
					break;
				case 9:
					System.out.println("FGTS");
					moviment.setValue(employee.getSalary());
					break;
				case 10:
					System.out.println("IRRF");
					moviment.setValue(employee.getSalary());
					break;
				}
				listaMoviment.add(moviment);
			}
		}
		
		try {
			listaMoviment.forEach(item -> movimentRepository.save(item));
			return "Sucesso";
		} catch (Exception e) {
			return "Não foi possível executar a ação";
		}
		
		
	}

}
