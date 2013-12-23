package com.br.chale.Service;

import java.util.List;

import com.br.chale.dao.Produto;

public interface ProdutoService {


	List<Produto> pesquisar(String nome, Integer codigo);

}
