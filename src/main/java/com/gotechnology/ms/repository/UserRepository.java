package com.gotechnology.ms.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.model.Profile;
import com.gotechnology.ms.model.User;

public class UserRepository {

	@Inject
	private EntityManager manager;

	public UserRepository() {
	}

	public UserRepository(EntityManager manager) {
		this.manager = manager;
	}

	public User getUsuarioByPassword(String name, String pwd) {
		StringBuilder jpql = new StringBuilder("");
		jpql.append("from User where userName = :name and password = :pwd");
		Query query = manager.createQuery(jpql.toString());
		query.setParameter("name", name);
		query.setParameter("pwd", pwd);

		return query.getResultList().size() > 0 ? (User) query.getResultList().get(0) : null;

	}
	
	
	
	
	
	public List<Profile> getProfiles(){
		Query query = manager.createQuery("from Profile");
		return query.getResultList();
	}
	

	public User save(User user) {
		return manager.merge(user);
	}

	public void remove(User user) {
		manager.remove(manager.merge(user));
	}

	public User findById(Long id) {
		return manager.find(User.class, id);
	}
	
	public Profile findProfileById(Long id) {
		return manager.find(Profile.class, id);
	}

	public List<User> getUsers(Filtro filtro) {
		Query query = manager.createQuery("from User");
		return query.getResultList();
	}

}
