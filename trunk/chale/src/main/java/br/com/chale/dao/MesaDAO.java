package br.com.chale.dao;

import br.com.chale.entity.Mesa;

public class MesaDAO extends GenericDAO<Mesa> {

	private static final long serialVersionUID = 2127025132325017019L;

	public Object consultarTodasMesas() {
		return  executeQueryListResult(Mesa.CONSULTAR_TODAS_MESAS);
	}

}
