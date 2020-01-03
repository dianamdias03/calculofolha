package com.calculoFolha.CalculoFolha.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="EMPLOYEE")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Employee() {
	
	}

	
	public Employee(long id, String name, Calendar admission, BigDecimal salary) {
		this.id = id;
		this.name = name;
		this.admission = admission;
		this.salary = salary;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private Calendar admission;
	
	@NotNull
	private BigDecimal salary;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getAdmission() {
		return admission;
	}

	public void setAdmission(Calendar admission) {
		this.admission = admission;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
