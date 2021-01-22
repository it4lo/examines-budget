package com.gotechnology.ms.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "exam")
public class Exam implements Serializable{

	
	
	/**
	 * 
	 */
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column 
	private String code;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "note", columnDefinition = "TEXT")
	private String note;
	
	@Column(name = "bronze_value", precision = 10, scale = 2)
	private BigDecimal bronzeValue;
	
	@Column(name = "silver_value", precision = 10, scale = 2)
	private BigDecimal silverValue;
	
	@Column(name = "gold_value", precision = 10, scale = 2)
	private BigDecimal goldValue;
	
	@Column(name = "diamond_value", precision = 10, scale = 2)
	private BigDecimal diamondValue;
	
	@Column(name = "particular_value", precision = 10, scale = 2)
	private BigDecimal particularValue;
	
	
	public Exam() {
	
	}
	
	
	public Exam(String linha[]) {
		code = linha[0] != null ? linha[0] : "";
		name = linha[1] != null ? linha[1] : "";
		description =  linha[2] != null ? linha[2] : "";
		diamondValue = new BigDecimal((linha[3] != null && !linha[3].equals(""))? linha[3] : "0");
		goldValue = new BigDecimal((linha[4] != null && !linha[4].equals("")) ? linha[4] : "0");
		silverValue = new BigDecimal((linha[5] != null && !linha[5].equals("")) ? linha[5] : "0");
		bronzeValue = new BigDecimal((linha[6] != null && !linha[6].equals("")) ? linha[6] : "0");
		particularValue = new BigDecimal((linha[7] != null && !linha[7].equals(""))? linha[7] : "0");
	}

	public Exam(String name, String description, String note, BigDecimal bronzeValue, BigDecimal silverValue,
			BigDecimal goldValue, BigDecimal diamondValue, BigDecimal particularValue) {

		this.name = name;
		this.description = description;
		this.note = note;
		this.bronzeValue = bronzeValue;
		this.silverValue = silverValue;
		this.goldValue = goldValue;
		this.diamondValue = diamondValue;
		this.particularValue = particularValue;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public BigDecimal getBronzeValue() {
		return bronzeValue;
	}


	public void setBronzeValue(BigDecimal bronzeValue) {
		this.bronzeValue = bronzeValue;
	}


	public BigDecimal getSilverValue() {
		return silverValue;
	}


	public void setSilverValue(BigDecimal silverValue) {
		this.silverValue = silverValue;
	}


	public BigDecimal getGoldValue() {
		return goldValue;
	}


	public void setGoldValue(BigDecimal goldValue) {
		this.goldValue = goldValue;
	}


	public BigDecimal getDiamondValue() {
		return diamondValue;
	}


	public void setDiamondValue(BigDecimal diamondValue) {
		this.diamondValue = diamondValue;
	}


	public BigDecimal getParticularValue() {
		return particularValue;
	}


	public void setParticularValue(BigDecimal particularValue) {
		this.particularValue = particularValue;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exam other = (Exam) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	

		
	
}
