package com.gotechnology.ms.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gotechnology.ms.model.User;
import com.gotechnology.ms.repository.UserRepository;
import com.gotechnology.ms.util.jpa.CDILocator;




@FacesConverter(forClass = User.class)
public class UserConverter implements Converter{

	private UserRepository repository;
	
	public UserConverter(){
		this.repository = CDILocator.getBean(UserRepository.class);
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			
			User retorno = new User();
			
			if (value != null) {
				retorno =  repository.findById(new Long(value));
			
			}
	 return retorno;	
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if(((User) value).getId() != null){
				return ((User) value).getId().toString();
			}
		}
		return null;
	}

	
	
}
