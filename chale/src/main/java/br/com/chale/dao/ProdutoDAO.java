package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Produto;

public class ProdutoDAO extends GenericDAO<Produto> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Produto> pesquisar(String termo) {
		return executeQueryListResult(Produto.QUERY_CONSULTAR_POR_NOME, termo);
		
	}

	public List<Produto> pesquisarTodos() {
		return executeQueryListResult(Produto.QUERY_CONSULTAR_TODOS);
	}

	public List<Produto> pesquisarProdutosEstoqueMin() {
		return executeQueryListResult(Produto.QUERY_CONSULTAR_PRODS_QTD_MIN);
	}

}
