package com.br.chale.dao;

import com.br.chale.entidades.Pessoa;

public class ClienteDAO extends GerenicDAO<Pessoa> {
	
	public void inserirProduto(Pessoa pessoa){
		salvar(pessoa);
	}
}
