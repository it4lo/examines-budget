package com.gotechnology.ms.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.model.Profile;
import com.gotechnology.ms.model.User;
import com.gotechnology.ms.repository.UserRepository;
import com.gotechnology.ms.util.jpa.Transactional;

public class UserService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UserRepository userRepo;
	
	public UserService() {
		
	}
	
	@Transactional
	public void save(User User) {
		userRepo.save(User);
	}
	
	@Transactional
	public void remove(User User) {
		userRepo.remove(User);
	}
	
	@Transactional
	public User updatePassoword(User user) {
		return userRepo.save(user);
	}
	
	
	
	public User findById(Long id) {
		return userRepo.findById(id);
	}
	
	public List<User> getUsers(Filtro filtro){
		return userRepo.getUsers(filtro);
	}
	
	public List<Profile> getProfiles(){
		return userRepo.getProfiles();
	}
	
	
}
