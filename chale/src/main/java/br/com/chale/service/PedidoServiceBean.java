package br.com.chale.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.MesaDAO;
import br.com.chale.entity.Mesa;

public class PedidoServiceBean implements PedidoService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private MesaDAO mesaDAO;

	@Override
	public List<Mesa> consultarTodasMesas() {
		return (List<Mesa>) mesaDAO.consultarTodasMesas();
	}
	
	
}
