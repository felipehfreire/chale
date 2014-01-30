package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.ClienteDAO;
import br.com.chale.entity.Cliente;

public class ClienteServiceBean implements ClienteService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private ClienteDAO clenteDAO;

	@Override
	public void persistir(Cliente cliente) {
		clenteDAO.insert(cliente);
	}

	@Override
	public void atualizar(Cliente cliente) {
		clenteDAO.update(cliente);
	}

	@Override
	public List<Cliente> pesquisar(String termo) {
		return clenteDAO.pesquisar(termo);
	}
	
	@Override
	public List<Cliente> pesquisarTodos() {
		return clenteDAO.pesquisarTodos();
	}

	@Override
	public void excluir(Cliente cliente) {
		clenteDAO.remover(cliente);
	}
}
