package com.br.chale.dao;



import java.util.List;

import com.br.chale.entidades.Mesa;

public class MesaDAO extends GerenicDAO<Mesa> {
	
	public List<Mesa> consultarTodasMesas() {
		return criarNamedQueryResultList(Mesa.CONSULTAR_TODAS_MESAS);
	}

	public List<Mesa> consultarMesasLivres() {
		return criarNamedQueryResultList(Mesa.CONSULTAR_MESAS_LIVRES);
	}

	public Mesa consultarMesaPorId(Integer numeroMesa) {
		return criarNamedQuerySingleResult(Mesa.CONSULTAR_MESA_POR_ID, numeroMesa);
	}

	public Mesa getMesaPedido(Integer idPedido) {
		return criarNamedQuerySingleResult(Mesa.CONSULTAR_MESA_POR_PEDIDO, idPedido);
	}
	
}
