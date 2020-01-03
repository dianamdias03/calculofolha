package com.calculoFolha.CalculoFolha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calculoFolha.CalculoFolha.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Employee findById(long id);
}
