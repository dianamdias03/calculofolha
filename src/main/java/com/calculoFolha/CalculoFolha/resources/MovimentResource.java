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
	
	public void emitiFolha(Employee employee, Event event, Moviment moviment) {
		BigDecimal salarioBruto = employee.getSalary();
		BigDecimal salarioLiquido = null;
		BigDecimal adicionais = new BigDecimal(0);
		BigDecimal descontos = new BigDecimal(0);
		
		if(event.getTipo() == 1) {
			adicionais = adicionais.add(moviment.getValue());
		}else if(event.getTipo() == 2){
			descontos.add(moviment.getValue());
		}
		
		salarioLiquido = (salarioBruto.subtract(descontos)).add(adicionais);
		System.out.println("------------------------------------");
		System.out.println("Empregado:" + employee.getName() + "\nSalario Liquido: " + salarioLiquido + "\nSalário Bruto: " + salarioBruto);
		System.out.println("------------------------------------");
	}

	public void calculoInss(Employee employee, Moviment moviment, BigDecimal baseInss) {
		BigDecimal aliquota;
		if (baseInss.compareTo(new BigDecimal(1750.81)) < 1) {
			aliquota = new BigDecimal("0.08");
		} else if (baseInss.compareTo(new BigDecimal(2919.22)) < 1) {
			aliquota = new BigDecimal("0.09");
		} else {
			aliquota = new BigDecimal(0.11);
			if (baseInss.compareTo(new BigDecimal(5839.45)) > 0) {
				baseInss = new BigDecimal(5839.45);
			}
		}
		moviment.setValue(baseInss.multiply(aliquota));
	}

	public void calculoFgts(Employee employee, Moviment moviment, BigDecimal baseFgts) {
		BigDecimal aliquota = new BigDecimal("0.08");
		moviment.setValue(baseFgts.multiply(aliquota));
	}

	public void calculoIrrf(Employee employee, Moviment moviment, BigDecimal baseIrrf) {
		BigDecimal aliquota;		
		if ((baseIrrf.compareTo(new BigDecimal(1903.98)) > 0) && (baseIrrf.compareTo(new BigDecimal(2826.66)) < 0)){
			aliquota = new BigDecimal("0.075");
		}else if((baseIrrf.compareTo(new BigDecimal(2826.65)) > 0) && (baseIrrf.compareTo(new BigDecimal(3751.06)) < 0)){
			aliquota = new BigDecimal("0.15");
		}else if((baseIrrf.compareTo(new BigDecimal(3751.05)) > 0) && (baseIrrf.compareTo(new BigDecimal(4664.69)) < 0)) {
			aliquota = new BigDecimal("0.225");
		}else if(baseIrrf.compareTo(new BigDecimal(4664.68)) > 0){
			aliquota = new BigDecimal("0.275");
		}else {
			aliquota = new BigDecimal("0");
		}
		
		moviment.setValue(baseIrrf.multiply(aliquota));
		
	}

	public BigDecimal calculaBase(Event event, Moviment moviment, BigDecimal base) {
		try {
			if (event.getTipo() != 3) {
				if (event.getTipo() == 1) {
					base = base.add(moviment.getValue());
				} else {
					BigDecimal valueEvent = moviment.getValue();
					if (valueEvent != null) {
						base = base.subtract(valueEvent);
					} else {
						System.out.println("O evento " + event.getId() + " está com o valor nulo!");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Erro calculando evento! Erro: " + e.getMessage());
			e.printStackTrace();
		}
		return base;
	}

	@PostMapping("/movimentCalculo/{mes}")
	public String CalculaFolha(@PathVariable(value = "mes") int mes) {
		
		List<Employee> listaEmployee = employeeRepository.findAll();
		List<Event> listaEvent = eventRepository.findAll();
		List<Moviment> listaMoviment = movimentRepository.findAll();
		Date data = new Date(2020, mes, 01);
		
		for (Employee employee : listaEmployee) {
			BigDecimal baseInss = new BigDecimal(0);
			BigDecimal baseFgts = new BigDecimal(0);
			BigDecimal baseIrrf = new BigDecimal(0);
			for (Event event : listaEvent) {
				Moviment moviment = new Moviment();
				moviment.setIdEmployee(employee);
				moviment.setIdEvent(event);
				moviment.setMonth(data);
				switch ((int) event.getId()) {
				case 5:
					moviment.setValue(employee.getSalary());
					break;
				case 6:
					moviment.setValue(new BigDecimal(200));
					break;
				case 7:
					moviment.setValue(new BigDecimal(60));
					break;
				case 8:
					calculoInss(employee, moviment, baseInss);
					break;
				case 9:
					calculoFgts(employee, moviment, baseFgts);
					break;
				case 10:
					calculoIrrf(employee, moviment, baseIrrf);
					break;
				}

				if (event.getINSS() == 1) {
					baseInss = calculaBase(event, moviment, baseInss);
				}
				
				if (event.getIRRF() == 1) {
					baseIrrf = calculaBase(event, moviment, baseIrrf);
				}
				
				if (event.getFGTS() == 1) {
					baseFgts = calculaBase(event, moviment, baseFgts);
				}
				
				listaMoviment.add(moviment);
				/*System.out.println(employee.getName() + " BaseInss: " + baseInss + "BaseFGTS: " + baseFgts
						+ "BaseIrrf: " + baseIrrf + " Valor: " + moviment.getValue() + " id:" + event.getId() + " tipo:"
						+ event.getTipo() + " base inss:" + event.getINSS());*/
				
				System.out.println(employee.getName() + ": Base Inss: " + baseInss + " Base Fgts: " + baseFgts + " Base IRRF: " + baseIrrf);
			
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
