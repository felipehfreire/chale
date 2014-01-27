package br.com.chale.service;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Pedido;


public interface PedidoService {

	List<Mesa> consultarTodasMesas();

	void persistir(Pedido pedido);

	void atualizar(Pedido pedido);

	List<Pedido> pesquisarPedidos(Mesa mesaSelecionada);

	List<Pedido> pesquisarPedidos();

	List<Pedido> pesquisarPedidosData(Date data);


}
