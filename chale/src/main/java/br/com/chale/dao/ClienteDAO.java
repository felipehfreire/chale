package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Cliente> pesquisarTodos() {
		return executeQueryListResult(Cliente.QUERY_CONSULTAR_TODOS);
	}

	public List<Cliente> pesquisar(String termo) {
		return executeQueryListResult(Cliente.QUERY_CONSULTAR_POR_NOME, termo);
		
	}
	
	public void remover(Cliente pessoa){
		manager.getTransaction().begin();
		pessoa = manager.find(Cliente.class, pessoa.getId());
		manager.remove(pessoa);
		manager.getTransaction().commit();
	}


}
