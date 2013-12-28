package br.com.chale.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.chale.dao.ProdutoDAO;

public class ProdutoServiceBean implements ProdutoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Override
	public void pesquisar() {
		produtoDAO.pesquisar();
	}

}
