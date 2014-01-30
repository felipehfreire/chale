package br.com.chale.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.chale.dao.MesaDAO;
import br.com.chale.dao.VendaDAO;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.Venda;

public class VendaServiceBean implements VendaService, Serializable {

	private static final long serialVersionUID = 8310482099763882250L;
	
	@Inject
	private MesaDAO mesaDAO;
	
	@Inject
	private VendaDAO vendaDAO;
	
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
	public List<Venda> pesquisarVendasPorMesa(Mesa mesaSelecionada) {
		return vendaDAO.pesquisarVendasPorMesa(mesaSelecionada);
	}

	@Override
	public List<Venda> pesquisarVendas() {
		return vendaDAO.pesquisarVendas();
	}

	@Override
	public List<Venda> pesquisarVendasPorData(Date data) {
		return vendaDAO.pesquisarVendasPorData(data);
	}

	@Override
	public void atualizarMesa(Mesa mesa) {
		mesaDAO.update(mesa);
		
	}
	
	

}
