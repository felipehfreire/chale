package br.com.chale.service;

import java.util.List;

import br.com.chale.entity.Produto;

public interface ProdutoService {

	List<Produto> pesquisar(String termo);

	void persistir(Produto produto);

	void atualizar(Produto produto);

	List<Produto> pesquisarTodos();

	Produto getById(Long idProd);

	List<Produto> pesquisarProdutosEstoqueMin();

}
