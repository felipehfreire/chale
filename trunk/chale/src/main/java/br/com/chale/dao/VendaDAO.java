package br.com.chale.dao;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Venda;

public class VendaDAO extends GenericDAO<Venda> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Venda> pesquisarVendas() {
		return executeQueryListResult(Venda.QUERY_CONSULTAR_TODAS_VENDAS);
	}

	public List<Venda> pesquisarVendasPorMesa(Mesa mesaSelecionada) {
		return executeQueryListResult(Venda.CONSULTAR_VENDAS_POR_MESA, mesaSelecionada);
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


	

}
