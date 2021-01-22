package com.gotechnology.ms.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gotechnology.ms.model.Profile;
import com.gotechnology.ms.model.User;
import com.gotechnology.ms.repository.UserRepository;
import com.gotechnology.ms.util.jpa.CDILocator;




@FacesConverter(forClass = Profile.class)
public class ProfileConverter implements Converter{

	private UserRepository repository;
	
	public ProfileConverter(){
		this.repository = CDILocator.getBean(UserRepository.class);
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			
			Profile retorno = new Profile();
			
			if (value != null) {
				retorno =  repository.findProfileById(new Long(value));
			
			}
	 return retorno;	
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if(((Profile) value).getId() != null){
				return ((Profile) value).getId().toString();
			}
		}
		return null;
	}

	
	
}
