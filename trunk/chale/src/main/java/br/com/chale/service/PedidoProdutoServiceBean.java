package br.com.chale.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.chale.dao.PedidoProdutoDAO;
import br.com.chale.entity.PedidoProduto;

public class PedidoProdutoServiceBean implements PedidoProdutoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private PedidoProdutoDAO pedidoPrdoDAO ;


	@Override
	public void atualizar(PedidoProduto pedidoProduto) {
		pedidoPrdoDAO.update(pedidoProduto);
	}
	
	
}
