package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.PedidoProduto;

//TODO na genericDAO já tem os principais métodos para consulta, caso precise utilizar algo específico,
		//utilize o EntityManager, somente chamando o seu atributo, que é o "manager".
		//Ex.: manager.createNativeQuery....
public class PedidoProdutoDAO extends GenericDAO<PedidoProduto> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<PedidoProduto> pesquisarPedidos() {
		return executeQueryListResult(PedidoProduto.QUERY_CONSULTAR_TODOS_PEDIDOS);
	}

	public List<PedidoProduto> pesquisarPedidos(Mesa mesaSelecionada) {
		return executeQueryListResult(PedidoProduto.CONSULTAR_PEDIDOS_POR_MESA, mesaSelecionada);
	}

	

}
