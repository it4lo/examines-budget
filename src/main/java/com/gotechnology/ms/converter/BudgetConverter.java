package com.gotechnology.ms.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.repository.BudgetRepository;
import com.gotechnology.ms.util.jpa.CDILocator;




@FacesConverter(forClass = Budget.class)
public class BudgetConverter implements Converter{

	private BudgetRepository repository;
	
	public BudgetConverter(){
		this.repository = CDILocator.getBean(BudgetRepository.class);
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			
			Budget retorno = new Budget();
			
			if (value != null) {
				retorno =  repository.findById(new Long(value));
			
			}
	 return retorno;	
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if(((Budget) value).getId() != null){
				return ((Budget) value).getId().toString();
			}
		}
		return null;
	}

	
	
}
