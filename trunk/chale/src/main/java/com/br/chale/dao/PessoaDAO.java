package com.br.chale.dao;

import java.util.List;

import com.br.chale.entidades.Pessoa;

public class PessoaDAO extends GerenicDAO<Pessoa> {

	public List<Pessoa> pesquisarClientes(String nome) {
		return criarNamedQueryResultList(Pessoa.CONSULTAR_CLIENTES, nome);
	}

	public Pessoa consultarPessoaPorId(Integer id) {
		return criarNamedQuerySingleResult(Pessoa.CONSULTAR_CLIENTE_POR_ID, id);
	}

	public List<Pessoa> pesquisarPessoaDuplicada(Pessoa pessoa) {
		return criarNamedQueryResultList(Pessoa.CONSULTAR_PESSOA_DUPLICADA, pessoa.getNome(), pessoa.getTelefone(), pessoa.getIdPessoa());
	}

}
