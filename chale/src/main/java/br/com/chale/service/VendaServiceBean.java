package br.com.chale.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.ClienteDAO;
import br.com.chale.dao.MesaDAO;
import br.com.chale.dao.VendaDAO;
import br.com.chale.entity.Cliente;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.Venda;

public class VendaServiceBean implements VendaService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private MesaDAO mesaDAO;
	
	@Inject
	private VendaDAO vendaDAO;
	
	@Inject
	private ClienteDAO clienteDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mesa> consultarTodasMesas() {
		return (List<Mesa>) mesaDAO.consultarTodasMesas();
	}

	@Override
	public void persistir(Venda venda) {
		vendaDAO.insert(venda);
	}

	@Override
	public Venda atualizar(Venda venda) {
		return vendaDAO.update(venda);
	}
	
	@Override
	public List<Venda> pesquisarVendasNaoFinalizadasPorMesa(Mesa mesaSelecionada) {
		return vendaDAO.pesquisarVendasNaoFinalizadasPorMesa(mesaSelecionada);
	}

	@Override
	public List<Venda> pesquisarVendasNaoFinalizadas() {
		return vendaDAO.pesquisarVendasNaoFinalizadas();
	}

	@Override
	public List<Venda> pesquisarVendasPorData(Date data) {
		return vendaDAO.pesquisarVendasPorData(data);
	}

	@Override
	public void atualizarMesa(Mesa mesa) {
		mesaDAO.update(mesa);
		
	}

	@Override
	public List<Venda> pesquisarVendasFinalizadasPrazo() {
		return vendaDAO.pesquisarVendasFinalizadasPrazo();
	}

	@Override
	public List<Venda> pesquisarVendasPrazoMes(Date data) {
		return vendaDAO.pesquisarVendasPrazoMes(data);
	}

	@Override
	public List<Mesa> consultarMesasNaoUsadas() {
		return mesaDAO.consultarMesasNaoUsadas();
	}

	@Override
	public List<Venda> pesquisarVendasPorCliente(Long id) {
		return vendaDAO.pesquisarVendasPorCliente(id);
	}

	@Override
	public List<Venda> pesquisarVendasPorProduto(Long id) {
		return vendaDAO.pesquisarVendasPorProduto(id);
	}

	@Override
	public List<Venda> pesquisarVendasFinalizadasPrazoPorPeriodo(
			Date dataInicial, Date dataFinal) {
		return vendaDAO.pesquisarVendasFinalizadasPrazoPorPeriodo(dataInicial, dataFinal);
	}

	@Override
	public void atualizarCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}
	

	

}
