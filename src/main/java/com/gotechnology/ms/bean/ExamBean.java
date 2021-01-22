package com.gotechnology.ms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.service.BudgetService;
import com.gotechnology.ms.service.ExamService;

@ViewScoped
@Named(value = "exam_bean")
public class ExamBean  implements Serializable{

	private static final long serialVersionUID = 1L;

	public ExamBean() {}

	private Filtro filtro = new Filtro();
	
	private @Inject ExamService service;
	
	private @Inject BudgetService serviceBud;
	
	private @Inject Exam exam;
	
	private List<Exam> exames =  new ArrayList<>();
	
	private List<Exam> exams = new ArrayList<>();

	public Filtro getFiltro() {
		return filtro;
	}
	
	public String save() {
		service.save(exam);
		return "exames?faces-redirect=true";
	}
	
	public void saveFaseExam() {
		service.save(exam);
		exam = new Exam();
	}
	
	public void limpar() {
		exam = new Exam();
	}
	
	public void remove() {
		service.remove(exam);
		carregarExames();
	}
	
	public List<Exam> completeExam(String s) {
		exams = new ArrayList<Exam>();
		exams = serviceBud.getExamsByString(s);
		return exams;
	}
	
	public String cancelar() {
		return "exames?faces-redirect=true";
	}

	
	public void initList() {
		carregarExames();
	}
	
	public void carregarExames() {
		exames = service.getExames(filtro);
	}
	
	public void filtrar() {
		carregarExames();
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public List<Exam> getExames() {
		return exames;
	}

	public void setExames(List<Exam> exames) {
		this.exames = exames;
	} 
	
	
	
	
}

