package br.com.chale.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.chale.dao.LoginDAO;
import br.com.chale.entity.Usuario;

public class LoginServiceBean implements LoginService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private LoginDAO loginDAO;
	

	@Override
	public Usuario pesquisarUsuario(String nomeUsuario, String senha) {
		
		return loginDAO.pesquisarUsuario(nomeUsuario, senha);
	}
	

}
