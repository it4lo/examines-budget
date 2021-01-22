package util;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.gotechnology.ms.model.User;



@Named(value =  "sessaoBean")
@SessionScoped
public class UsuarioSessao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeUsuario;
	private Date dataLogin;
	private User usuario;
	
	private boolean logado;
	
	
	public UsuarioSessao(){}


	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public Date getDataLogin() {
		return dataLogin;
	}


	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
	}


	public boolean isLogado() {
		return logado;
	}


	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	
	public boolean verificaLogado(){
		return nomeUsuario != null;
	}
	
	public  User getUsuario() {
		return usuario;
	}



	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
}
