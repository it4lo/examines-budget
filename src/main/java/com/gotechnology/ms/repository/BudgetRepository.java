package com.gotechnology.ms.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.model.BudgetExam;
import com.gotechnology.ms.model.Filtro;

public class BudgetRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public BudgetRepository() {
	}

	public BudgetRepository(EntityManager manager) {
		this.manager = manager;
	}

	public Budget save(Budget budget) {
		return manager.merge(budget);
	}
	
	public BudgetExam saveTuple(BudgetExam budgetExam) {
		return manager.merge(budgetExam);
	}
	
	
	public void removeTuple(BudgetExam budgetExam) {
		 manager.remove(manager.merge(budgetExam));
	}
	public void remove(Budget budget) {
		manager.remove(manager.merge(budget));
	}
	
	public Integer getBudgetExamByIdExame(Long idBudgetExam, Long idExam) {
		StringBuilder jpql = new StringBuilder("select count(*) from budget_exam where id = :id_be and exam_id = :id_exam");
		Query query = manager.createNativeQuery(jpql.toString());
		query.setParameter("id_be", idBudgetExam);
		query.setParameter("id_exam",idExam);
		Object object = query.getSingleResult();
		return Integer.parseInt(object.toString()!=null?object.toString():"0");
	}
	
	public Integer getNumeroSequencial() {
		StringBuilder jpql = new StringBuilder("select count(*) from budget");
		Query query = manager.createNativeQuery(jpql.toString());
		Object object = query.getSingleResult();
		return Integer.parseInt(object.toString()!=null?object.toString():"0") + 1;
	}

	public Budget findById(Long id) {
		return manager.find(Budget.class, id);
	}

	public List<Budget> getBudgets(Filtro filtro) {
		StringBuilder jpql = new StringBuilder("from Budget b where 1 = 1 ");

		if (filtro.getNumero() != null) {
			jpql.append(" and b.numero = :numero");
		}

		Query query = manager.createQuery(jpql.toString());

		if (filtro.getNumero() != null) {
			query.setParameter("numero", filtro.getNumero());
		}
		
		jpql.append(" order by b.numero desc");

		return query.getResultList();
	}
	
	public List<BudgetExam> getBudgetExams(Filtro filtro) {
		StringBuilder jpql = new StringBuilder("from BudgetExam b where 1 = 1 ");

		if (filtro.getIdBudget() != null) {
			jpql.append(" and b.budget.id = :budget");
		}
		
		jpql.append(" order by b.id");

		Query query = manager.createQuery(jpql.toString());

		if (filtro.getIdBudget() != null) {
			query.setParameter("budget", filtro.getIdBudget());
		}

		return query.getResultList();
	}

}
