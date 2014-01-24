package br.com.chale.dao;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.PedidoProduto;

public class PedidoProdutoDAO extends GenericDAO<PedidoProduto> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<PedidoProduto> pesquisarPedidosDataAtual(Date dataAtual) {
		dataAtual = new Date(2014, 1, 24, 15, 15, 2);
		//TODO tirar a data acima feito para teste
		return executeQueryListResult(PedidoProduto.QUERY_CONSULTAR_PED_POR_DATA, dataAtual);
	}


}
