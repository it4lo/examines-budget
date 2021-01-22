package com.gotechnology.ms.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.model.Filtro;

public class ExamRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private EntityManager manager;
	
	public ExamRepository() {}
	
	public ExamRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	
	public void save(Exam exam) {
		this.manager.merge(exam);
	}
	
	public void remove(Exam exam) {
	   manager.remove(manager.merge(exam));
	}
	
	public Exam findById(Long id) {
		return manager.find(Exam.class, id);
	}
	
	
	public List<Exam> getExames(Filtro filtro){
		StringBuilder jpql =  new StringBuilder("from Exam where 1 = 1 ");
		
		if (filtro.getName() != null && !filtro.getName().equals("")) {
			jpql.append(" and lower(name) like lower(:name)");
		}
		
		if (filtro.getDescription() != null && !filtro.getDescription().equals("")) {
			jpql.append(" and lower(description) like lower(:description)");
		}
		
		jpql.append(" order by id ");
		
		Query query = manager.createQuery(jpql.toString());
		
		if (filtro.getName() != null && !filtro.getName().equals("")) {
			query.setParameter("name", "%" + filtro.getName() + "%");
		}
		
		if (filtro.getDescription() != null && !filtro.getDescription().equals("")) {
			query.setParameter("description", "%" + filtro.getDescription() + "%");
		}
		
		return query.getResultList();
	}
	
	public List<Exam> getExamsByString(String s) {
		StringBuilder jpql = new StringBuilder("from  Exam e where lower(e.name) like lower(:name) ");
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("name", "%" + s + "%");
		return query.getResultList().size() > 0 ? query.getResultList() : (List) new ArrayList<Exam>();
	}
	
}
