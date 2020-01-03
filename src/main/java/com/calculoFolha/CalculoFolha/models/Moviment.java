package com.calculoFolha.CalculoFolha.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="MOVIMENT")
public class Moviment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Moviment() {
		
	}
	
	public Moviment(long id, Date month, Event idEvent, Employee idEmployee, BigDecimal value) {
		this.id = id;
		this.month = month;
		this.idEvent = idEvent;
		this.idEmployee = idEmployee;
		this.value = value;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	private Date month;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "moviment_employee_id")
	private Employee idEmployee;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "moviment_event_id")
	private Event idEvent;

	@NotNull
	private BigDecimal value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Employee getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Employee idEmployee) {
		this.idEmployee = idEmployee;
	}


	public Event getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Event idEvent) {
		this.idEvent = idEvent;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
