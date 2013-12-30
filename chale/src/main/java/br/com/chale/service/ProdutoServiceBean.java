package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.ProdutoDAO;
import br.com.chale.entity.Produto;

public class ProdutoServiceBean implements ProdutoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private ProdutoDAO produtoDAO;
	
	@Override
	public List<Produto> pesquisar(String termo) {
		return produtoDAO.pesquisar(termo);
	}

	@Override
	public void persistir(Produto produto) {
		produtoDAO.insert(produto);
		
	}

	@Override
	public void atualizar(Produto produto) {
		produtoDAO.update(produto);
	}
	
}
