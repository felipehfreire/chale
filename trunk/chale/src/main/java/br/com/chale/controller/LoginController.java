package br.com.chale.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Usuario;
import br.com.chale.service.LoginService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoginService loginService;
	
	private String nomeUsuario;
	private String senha;
	private Usuario user;
	
	private boolean loggedIn;
	
	public  String Login (){
		ConversationUtil.iniciarConversacao(conversation);
		user = loginService.pesquisarUsuario(nomeUsuario, senha);
		if(user != null){
			return "/index.jsf?faces-redirect=true"; 
		}else{
			FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
			return"";
		}
		
	}
	
	 public String logout() {
		return "/index.jsf?faces-redirect=true";
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	} 

}
