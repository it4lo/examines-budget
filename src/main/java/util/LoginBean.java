package util;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.gotechnology.ms.model.User;
import com.gotechnology.ms.repository.UserRepository;

@Named(value = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private User usuario;
	@Inject
	private UsuarioSessao sessao;
	@Inject
	private UserRepository repository;

	private String emailUsuario;
	private String nomeUsuario;
	private String senhaUsuario;

	// public String getWelcome() {
	// return "Bem vindo !";
	// }

	public String getWelcome() {
		return "Bem vindo " + this.sessao.getUsuario().getName() + "!";
	}

	public LoginBean() {
	}
	
	

	public String logar() {
		FacesContext context = FacesContext.getCurrentInstance();
		usuario = repository.getUsuarioByPassword(nomeUsuario, senhaUsuario);

		if (usuario != null) {
			sessao.setNomeUsuario(nomeUsuario);
			sessao.setLogado(true);
			sessao.setDataLogin(new Date());
			sessao.setUsuario(usuario);

			return "/main/orcamentos.xhtml?faces-redirect=true";
		} else {
			FacesMessage message = new FacesMessage("Usuario/Senha inválido");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		}
		return null;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/main/login.xhtml?faces-redirect=true";
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public UsuarioSessao getSessao() {
		return sessao;
	}

	public void setSessao(UsuarioSessao sessao) {
		this.sessao = sessao;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

}
