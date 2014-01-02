package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.PedidoProdutoDAO;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.PedidoProduto;

public class PedidoProdutoServiceBean implements PedidoProdutoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private PedidoProdutoDAO pedidoPrdoDAO ;

	@Override
	public List<PedidoProduto> pesquisarPedidos(Mesa mesaSelecionada) {
		
		return pedidoPrdoDAO.pesquisarPedidos(mesaSelecionada);
	}

	@Override
	public List<PedidoProduto> pesquisarPedidos() {
		
		return pedidoPrdoDAO.pesquisarPedidos();
	}

	
}
