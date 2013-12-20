package com.br.chale.ifacade;

import java.util.List;

import com.br.chale.dao.Produto;

public interface IFacade {


	List<Produto> pesquisar(String nome, Integer codigo);

}
