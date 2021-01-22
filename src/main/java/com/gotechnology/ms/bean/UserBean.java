package com.gotechnology.ms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.model.Profile;
import com.gotechnology.ms.model.User;
import com.gotechnology.ms.service.UserService;

import util.UsuarioSessao;

@ViewScoped
@Named(value = "user_bean")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserBean() {
	}

	private Filtro filtro = new Filtro();

	private @Inject UserService service;

	private @Inject User user;

	private @Inject UsuarioSessao usuarioSessao;

	private List<User> users = new ArrayList<>();

	private String passwordAux;

	public Filtro getFiltro() {
		return filtro;
	}

	public String save() {

		if (user.getId() != null)
			if (!usuarioSessao.getUsuario().getProfile().equals("admin")) {
				User userAux = service.findById(user.getId());
				if (!(userAux.getPassword().equals(user.getPassword()))) {
					addMessage("", "A senha de login não está correta", FacesMessage.SEVERITY_ERROR);
					return "";
				}
			}

		service.save(user);

		return "usuarios?faces-redirect=true";
	}

	public void updatePassword() {

		if (user.getId() != null)
			if (!usuarioSessao.getUsuario().getProfile().equals("admin")) {
				User userAux = service.findById(user.getId());
				if (!(passwordAux.equals(userAux.getPassword()))) {
					addMessage("", "A senha atual não está correta e não foi alterada", FacesMessage.SEVERITY_ERROR);
					return;
				}
			}

		user = service.updatePassoword(user);
		addMessage("", "Senha alterada", FacesMessage.SEVERITY_INFO);
	}
	
	
	public void resetPassword() {
		user = service.updatePassoword(user);
		addMessage("", "Senha alterada", FacesMessage.SEVERITY_INFO);
	}
	
	

	public void addMessage(String summary, String detail, Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String gettingProfile() {
		if (usuarioSessao.getUsuario() != null) {
			return usuarioSessao.getUsuario().getProfile().getName();
		}
		return "";
	}

	public void saveFaseExam() {
		service.save(user);
		user = new User();
	}

	public void limpar() {
		user = new User();
	}

	public void remove() {
		service.remove(user);
		gettingUsers();
	}

	public String cancelar() {
		return "usuarios?faces-redirect=true";
	}

	private List<Profile> profiles;

	public void initCadastro() {
		profiles = new ArrayList<>();
		profiles = service.getProfiles();
	}

	public void initList() {
		gettingUsers();
	}

	public void gettingUsers() {
		users = service.getUsers(filtro);
	}

	public void filtrar() {
		initList();
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public String getPasswordAux() {
		return passwordAux;
	}

	public void setPasswordAux(String passwordAux) {
		this.passwordAux = passwordAux;
	}

}
