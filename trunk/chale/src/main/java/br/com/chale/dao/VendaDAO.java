package br.com.chale.dao;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Venda;

public class VendaDAO extends GenericDAO<Venda> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Venda> pesquisarVendasNaoFinalizadas() {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_NAO_FINALIZADAS);
	}

	public List<Venda> pesquisarVendasNaoFinalizadasPorMesa(Mesa mesaSelecionada) {
		return executeQueryListResult(Venda.CONSULTAR_VENDAS_NAO_FINALIZADAS_POR_MESA, mesaSelecionada);
	}

	public List<Venda> pesquisarVendasPorData(Date data) {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_POR_DATA, data);
	}

	public List<Venda> pesquisarVendasFinalizadasPrazo() {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_PRAZO);
	}

	public List<Venda> pesquisarVendasPrazoMes(Date data) {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_PRAZO_POR_MES, data);
	}

	public List<Venda> pesquisarVendasPorCliente(Long id) {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_POR_CLIENTE, id);
	}

	public List<Venda> pesquisarVendasPorProduto(Long id) {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_VENDAS_POR_PRODUTO, id);
	}


	

}
