package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Pessoa;

public class PessoaDAO extends GenericDAO<Pessoa> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Pessoa> pesquisarTodos() {
		return executeQueryListResult(Pessoa.QUERY_CONSULTAR_TODOS);
	}

	public List<Pessoa> pesquisar(String termo) {
		return executeQueryListResult(Pessoa.QUERY_CONSULTAR_POR_NOME, termo);
		
	}

}
