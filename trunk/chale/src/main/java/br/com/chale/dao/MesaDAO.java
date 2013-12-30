package br.com.chale.dao;

import br.com.chale.entity.Mesa;

//TODO na genericDAO já tem os principais métodos para consulta, caso precise utilizar algo específico,
		//utilize o EntityManager, somente chamando o seu atributo, que é o "manager".
		//Ex.: manager.createNativeQuery....
public class MesaDAO extends GenericDAO<Mesa> {

	private static final long serialVersionUID = 2127025132325017019L;

	public Object consultarTodasMesas() {
		return  executeQueryListResult(Mesa.CONSULTAR_TODAS_MESAS);
	}

}
