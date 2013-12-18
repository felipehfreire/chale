package com.br.chale.dao;


import java.util.List;

import com.br.chale.entidades.Mesa;
import com.br.chale.entidades.Pedido;

public class PedidoDAO extends GerenicDAO<Pedido> {
	
	public List<Pedido> consultarPedidos(Mesa mesa) {
		return criarNamedQueryResultList(Pedido.CONSULTAR_PEDIDOS, mesa != null ? mesa.getNumeroMesa() : null);
	}

	public Pedido consultarPedidoPorId(Integer id) {
		return criarNamedQuerySingleResult(Pedido.CONSULTAR_PEDIDO_POR_ID, id);
	}
	
}
