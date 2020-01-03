package com.calculoFolha.CalculoFolha.resources;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.calculoFolha.CalculoFolha.models.Employee;
import com.calculoFolha.CalculoFolha.repository.EmployeeRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/calculaFolha")
public class EmployeeResource {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/employee")
	public List<Employee> listaEmployee(){
		return employeeRepository.findAll();
	}
	
	@GetMapping("/employee/{id}")
	public Employee listaEmployeeUnic(@PathVariable(value="id") long id){
		return employeeRepository.findById(id);
	}
	
	@PostMapping("/employee")
	public Employee saveEmployed(@RequestBody @Valid Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@DeleteMapping("/employee")
	public void deleteEmployee(@RequestBody @Valid Employee employee) {
		employeeRepository.delete(employee);
	}
	
	@PutMapping("/employee")
	public Employee updateEmployee(@RequestBody @Valid Employee employee) {
		return employeeRepository.save(employee);
	}	
	
}
