package br.com.chale.service;

import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.PedidoProduto;


public interface PedidoProdutoService {

	List<PedidoProduto> pesquisarPedidos(Mesa mesaSelecionada);

	List<PedidoProduto> pesquisarPedidos();

}
