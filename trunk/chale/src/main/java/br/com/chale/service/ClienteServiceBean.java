package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.PessoaDAO;
import br.com.chale.entity.Pessoa;

public class ClienteServiceBean implements ClienteService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private PessoaDAO pessoaDAO;

	@Override
	public void persistir(Pessoa pessoa) {
		pessoaDAO.insert(pessoa);
	}

	@Override
	public void atualizar(Pessoa pessoa) {
		pessoaDAO.update(pessoa);
	}

	@Override
	public List<Pessoa> pesquisar(String termo) {
		return pessoaDAO.pesquisar(termo);
	}
	
	@Override
	public List<Pessoa> pesquisarTodos() {
		return pessoaDAO.pesquisarTodos();
	}

	@Override
	public void excluir(Pessoa pessoa) {
		pessoaDAO.remover(pessoa);
	}
}
