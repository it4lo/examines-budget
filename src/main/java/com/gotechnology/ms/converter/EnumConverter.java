package com.gotechnology.ms.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.model.TypeBudget;
import com.gotechnology.ms.repository.BudgetRepository;
import com.gotechnology.ms.util.jpa.CDILocator;




@FacesConverter("enumConverter")
public class EnumConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			return TypeBudget.valueOf(value);
		}
		return null;
	}
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof TypeBudget) {
			return ((TypeBudget) value).name();
		}
		return null;
	}
	
	
}
