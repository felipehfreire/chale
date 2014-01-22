package br.com.chale.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.chale.dao.UsuarioDAO;
import br.com.chale.entity.Usuario;

public class UsuarioServiceBean implements UsuarioService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public Usuario pesquisarUsuario(String nomeUsuario, String senha) {

		return usuarioDAO.pesquisarUsuario(nomeUsuario, senha);
	}

	@Override
	public Usuario pesquisarSenha(String senha, String nomeUsuario) {

		return usuarioDAO.pesquisarSenha(senha, nomeUsuario);
	}

	@Override
	public void atualizar(Usuario userAtual) {
		usuarioDAO.update(userAtual,true);
	}

}
