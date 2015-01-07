package br.com.chale.service;

import java.util.List;

import br.com.chale.entity.Cliente;

public interface ClienteService {

	void persistir(Cliente cliente);

	void atualizar(Cliente cliente);

	List<Cliente> pesquisar(String termo);

	void excluir(Cliente cliente);

	List<Cliente> pesquisarTodos();

	List<Cliente> popularAutoCompleteCliente(String nomeCod);


}
