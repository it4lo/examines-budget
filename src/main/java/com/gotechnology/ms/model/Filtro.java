package com.gotechnology.ms.model;

public class Filtro {
	
	
	
	private String name;
	private String description;
    private Integer numero; // Passou despercebido
    private Long idUser;
    private Long idBudget;
	
	public Filtro() {}

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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdBudget() {
		return idBudget;
	}

	public void setIdBudget(Long idBudget) {
		this.idBudget = idBudget;
	}
	
	
	

}
