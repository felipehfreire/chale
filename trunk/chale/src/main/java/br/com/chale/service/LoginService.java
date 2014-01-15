package br.com.chale.service;

import br.com.chale.entity.Usuario;

public interface LoginService {

		Usuario pesquisarUsuario(String nomeUsuario, String senha);

}
