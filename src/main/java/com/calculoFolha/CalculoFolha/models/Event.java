package com.calculoFolha.CalculoFolha.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="EVENT")
public class Event implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Event() {
		
	}
	
	public Event(long id, String name, int INSS, int FGTS, int IRRF, int HEXTRA) {
		this.id = id;
		this.name = name;
		this.INSS = INSS;
		this.FGTS = FGTS;
		this.IRRF = IRRF;
		this.HEXTRA = HEXTRA;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private int INSS;
	

	@NotNull
	private int FGTS;
	

	@NotNull
	private int IRRF;
	
	@NotNull
	private int HEXTRA;

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

	public int getINSS() {
		return INSS;
	}

	public void setINSS(int INSS) {
		this.INSS = INSS;
	}

	public int getFGTS() {
		return FGTS;
	}

	public void setFGTS(int FGTS) {
		this.FGTS = FGTS;
	}

	public int getIRRF() {
		return IRRF;
	}

	public void setIRRF(int IRRF) {
		this.IRRF = IRRF;
	}

	public int getHEXTRA() {
		return HEXTRA;
	}

	public void setHEXTRA(int HEXTRA) {
		this.HEXTRA = HEXTRA;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


}
