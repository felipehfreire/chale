package br.com.chale.dao;

import br.com.chale.entity.Usuario;

public class LoginDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = 2127025132325017019L;

	public Usuario pesquisarUsuario(String nomeUsuario, String senha) {
		
		return  executeQuerySingleResult(Usuario.QUERY_CONSULTAR_USUARIO, nomeUsuario, senha);
	}


}
