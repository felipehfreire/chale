package br.com.chale.dao;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Pedido;

public class PedidoDAO extends GenericDAO<Pedido> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Pedido> pesquisarPedidos() {
		return executeQueryListResult(Pedido.QUERY_CONSULTAR_TODOS_PEDIDOS);
	}

	public List<Pedido> pesquisarPedidos(Mesa mesaSelecionada) {
		return executeQueryListResult(Pedido.CONSULTAR_PEDIDOS_POR_MESA, mesaSelecionada);
	}

	public List<Pedido> pesquisarPedidosData(Date data) {
		return executeQueryListResult(Pedido.QUERY_CONSULTAR_PED_POR_DATA, data);
	}

	

}
