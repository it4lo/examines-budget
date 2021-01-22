package com.gotechnology.ms.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.repository.ExamRepository;
import com.gotechnology.ms.util.jpa.CDILocator;




@FacesConverter(forClass = Exam.class)
public class ExamConverter implements Converter{

	private ExamRepository repository;
	
	public ExamConverter(){
		this.repository = CDILocator.getBean(ExamRepository.class);
	}
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
			
			Exam retorno = new Exam();
			
			if (value != null) {
				retorno =  repository.findById(new Long(value));
			
			}
	 return retorno;	
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if(((Exam) value).getId() != null){
				return ((Exam) value).getId().toString();
			}
		}
		return null;
	}

	
	
}
