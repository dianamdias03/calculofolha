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
@RequestMapping(value = "/calculaFolha")
public class MovimentResource {

	@Autowired
	MovimentRepository movimentRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EventRepository eventRepository;

	@GetMapping("/moviment")
	public List<Moviment> listaMoviments() {
		return movimentRepository.findAll();
	}

	@GetMapping("/moviment/{id}")
	public Moviment listaMovimentUnic(@PathVariable(value = "id") long id) {
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

	public void calculoInss(Employee employee, Moviment moviment, BigDecimal baseInss) {
		System.out.println("INSS");
		BigDecimal aliquota = new BigDecimal("0.07");
		moviment.setValue(baseInss.multiply(aliquota));
	}

	public void calculoFgts(Employee employee, Moviment moviment, BigDecimal baseInss) {
		System.out.println("FGTS");
		BigDecimal aliquota = new BigDecimal("0.08");
		moviment.setValue(baseInss.multiply(aliquota));
	}

	@PostMapping("/movimentCalculo/{mes}")
	public String CalculaFolha(@PathVariable(value = "mes") int mes) {
		List<Employee> listaEmployee = employeeRepository.findAll();
		List<Event> listaEvent = eventRepository.findAll();
		List<Moviment> listaMoviment = movimentRepository.findAll();
		BigDecimal baseInss = new BigDecimal(0);
		System.out.println(mes);
		System.out.println();
		Date data = new Date(2020, mes, 01);
		for (Employee employee : listaEmployee) {
			for (Event event : listaEvent) {
				Moviment moviment = new Moviment();
				moviment.setIdEmployee(employee);
				moviment.setIdEvent(event);
				moviment.setMonth(data);
				switch ((int) event.getId()) {
				case 5:
					System.out.println("Salário");
					moviment.setValue(employee.getSalary());
					break;
				case 6:
					System.out.println("Hora extra");
					moviment.setValue(new BigDecimal(200));
					break;
				case 7:
					System.out.println("Hora falta");
					moviment.setValue(new BigDecimal(60));
					break;
				case 8:
					calculoInss(employee, moviment, baseInss);
					break;
				case 9:
					calculoFgts(employee, moviment, baseInss);
					break;
				case 10:
					System.out.println("IRRF");
					moviment.setValue(employee.getSalary());
					break;
				}
				listaMoviment.add(moviment);
				if (event.getINSS() == 1) {
					baseInss = baseInss.add(moviment.getValue())
				}
				
				if (event.getFGTS() == 1) {
					baseInss = baseInss.add(moviment.getValue());
				}
				
				if (event.getIRRF() == 1) {
					baseInss = baseInss.add(moviment.getValue());
				}
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
