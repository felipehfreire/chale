package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.MesaDAO;
import br.com.chale.dao.PedidoDAO;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.Pedido;

public class PedidoServiceBean implements PedidoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private MesaDAO mesaDAO;
	
	@Inject
	private PedidoDAO pedidoDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<Mesa> consultarTodasMesas() {
		return (List<Mesa>) mesaDAO.consultarTodasMesas();
	}

	@Override
	public void persistir(Pedido pedido) {
		pedidoDAO.insert(pedido);
	}

	@Override
	public void atualizar(Pedido pedido) {
		pedidoDAO.update(pedido);
	}
	
}
