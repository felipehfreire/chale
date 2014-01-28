package br.com.chale.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Usuario;
import br.com.chale.service.UsuarioService;
import br.com.chale.util.EncriptatorUtil;

@Named
@ManagedBean
@ApplicationScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private UsuarioService usuarioService;
	
	private String nomeUsuario;
	private String senha;	
	private Usuario user;
	private String novaSenha;
	private String confirmSenha;
	
	private boolean loggedIn;
	
	@PostConstruct
	public void iniciar() {
		limpar();
	}
	
	public  String Login (){
		user = usuarioService.pesquisarUsuario(nomeUsuario, senha);
		if(user != null){
			loggedIn = true;
			return "index.jsf?faces-redirect=true"; 
		}else{
			FacesMessage msg = new FacesMessage("ERRO ao logar:", "Usuário ou Senha incorretos!");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        loggedIn = false;
			return"";
		}
	}
	
	 public String logout() {
		 loggedIn = false;
		 FacesMessage msg = new FacesMessage("Logout:", "Desconectado com sucesso!");
		 msg.setSeverity(FacesMessage.SEVERITY_INFO);
		 FacesContext.getCurrentInstance().addMessage(null, msg);
		 return "/index.jsf?faces-redirect=true";
	}
	
	 public void limpar() {
			nomeUsuario = "";
			senha = "";
			user = new Usuario();
			novaSenha="";
			confirmSenha="";
	} 
	 
	 public  String alterarSenha (){
		 Usuario userAtual =new Usuario();
		 if(senha!= null && nomeUsuario!=null){
			 userAtual =  usuarioService.pesquisarSenha(senha, nomeUsuario);
		 }else{
			 return"";
		 }
		 
		 if(userAtual!= null){
			 if(novaSenha.equals(confirmSenha)){
				 
				userAtual.setSenha(EncriptatorUtil.formatMD5(novaSenha));
				 userAtual.setUsuario(nomeUsuario);
				 usuarioService.atualizar(userAtual);
			 }
		 }else{
			 loggedIn = false;
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Usuario ou senha incorretos!"));
			 return"";
		 }
		 
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Senha alterada com sucesso!"));
		 limpar();
		 return"";
	 }
	 
	 public  String alterarSenhaNavigation (){
		 return "/secure/alterarSenha.jsf?faces-redirect=true";  
	 }
	 
	 public  String efetuarLogin (){
		 return "/login.jsf?faces-redirect=true";  
	 }
	 
	 public  String cancelar (){
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

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmSenha() {
		return confirmSenha;
	}

	public void setConfirmSenha(String confirmSenha) {
		this.confirmSenha = confirmSenha;
	} 

}
