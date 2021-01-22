package com.gotechnology.ms.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.repository.ExamRepository;
import com.gotechnology.ms.util.jpa.Transactional;

public class ExamService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ExamRepository examRepo;
	
	public ExamService() {
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	public void save(Exam exam) {
		examRepo.save(exam);
	}
	
	@Transactional
	public void remove(Exam exam) {
		examRepo.remove(exam);
	}
	
	public Exam findById(Long id) {
		return examRepo.findById(id);
	}
	
	public List<Exam> getExames(Filtro filtro){
		return examRepo.getExames(filtro);
	}
}
