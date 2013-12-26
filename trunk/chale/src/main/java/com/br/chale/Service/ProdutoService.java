package com.br.chale.Service;

import java.util.List;

import com.br.chale.entidades.Produto;

public interface ProdutoService {


	List<Produto> pesquisar(String nome, Integer codigo);

}
