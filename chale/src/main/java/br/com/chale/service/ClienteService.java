package br.com.chale.service;

import java.util.List;

import br.com.chale.entity.Pessoa;

public interface ClienteService {

	void persistir(Pessoa pessoa);

	void atualizar(Pessoa pessoa);

	List<Pessoa> pesquisar(String termo);

	void excluir(Pessoa pessoa);


}
