package com.br.chale.dao;




import java.util.List;

import com.br.chale.entidades.Produto;

public class ProdutoDAO extends GerenicDAO<Produto> {
	
	public List<Produto> pesquisar(String nome, Integer codigo) {
		return criarNamedQueryResultList(Produto.CONSULTAR_PRODUTOS, nome, codigo);
	}
	
	public void inserirProduto(Produto produto){
		salvar(produto);
	}

	public Produto pesquisarProdutoPorId(Integer id) {
		return criarNamedQuerySingleResult(Produto.CONSULTAR_PRODUTO_POR_ID, id);
	}

	public List<Produto> consultarTodosProdutos() {
		return criarNamedQueryResultList(Produto.CONSULTAR_TODOS_PRODUTOS);
	}

	public List<Produto> pesquisarProdutosDuplicados(Produto produto) {
		return criarNamedQueryResultList(Produto.CONSULTAR_PRODUTOS_DUPLICADOS, produto.getDescricao(), produto.getIdProd());
	}
}
