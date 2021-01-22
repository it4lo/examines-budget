package com.gotechnology.ms.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gotechnology.ms.service.BudgetService;

@ViewScoped
@Named(value = "menu_bean")
public class MenuBean  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuBean() {}
	
	private String pagina = "orcamentos.xhtml";

	public String mudarPagina(String pagina) {
		return pagina + "?faces-redirect=true";
	}
	
	

	public String getPagina() {
		return pagina;
	}


	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	
	
	
}

