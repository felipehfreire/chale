package br.com.chale.service;

import java.util.Date;
import java.util.List;

import br.com.chale.entity.Cliente;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.Venda;


public interface VendaService {

	List<Mesa> consultarTodasMesas();

	void persistir(Venda venda);

	Venda atualizar(Venda venda);

	List<Venda> pesquisarVendasNaoFinalizadasPorMesa(Mesa mesaSelecionada);

	List<Venda> pesquisarVendasNaoFinalizadas();

	List<Venda> pesquisarVendasPorData(Date data);

	void atualizarMesa(Mesa mesa);

	List<Venda> pesquisarVendasFinalizadasPrazo();

	List<Venda> pesquisarVendasPrazoMes(Date data);

	List<Mesa> consultarMesasNaoUsadas();

	List<Venda> pesquisarVendasPorCliente(Long id);

	List<Venda> pesquisarVendasPorProduto(Long id);

	List<Venda> pesquisarVendasFinalizadasPrazoPorPeriodoCliente(Date dataInicial,
			Date dataFinal, Cliente cliente);

	void atualizarCliente(Cliente cliente);

	List<Venda> pesquisarVendasNaoFinalizadasPorCliente(
			Cliente clienteSelecionado);

	List<Venda> pesquisarVendasFinalizadasPrazoPorCliente(Cliente cliente);

	List<Venda> pesquisarVendasFinalizadasPrazoPorPeriodo(Date dataInicial,
			Date dataFinal);

	void excluir(Venda venda);


}
