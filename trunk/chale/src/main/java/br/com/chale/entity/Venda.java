package br.com.chale.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="venda")
@NamedQueries({
	
	@NamedQuery(name=Venda.QUERY_CONSULTAR_TODAS_VENDAS, query="select p from Venda p "),
	
	@NamedQuery(name=Venda.CONSULTAR_VENDAS_POR_MESA, query="select p from Venda p " +
			" where p.mesa = ?1"),
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_POR_DATA, query="select p from Venda p "
			+ " where p.finalizada = true "
			+ " and  p.dataVenda < ?1 "),
			@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_PRAZO, query="select p from Venda p "
					+ " where p.finalizada = true "
					+ " and pago = false "),		
	
})
public class Venda implements BaseEntity, Serializable {
	
	private static final long serialVersionUID = -5412811770343143282L;

	public static final String QUERY_CONSULTAR_TODAS_VENDAS = "venda.queryConsultarTodasVendas";
	public static final String CONSULTAR_VENDAS_POR_MESA = "venda.consultarVendasPorMesa";
	public static final String QUERY_CONSULTAR_VENDAS_POR_DATA = "venda.consultarVendasPorData";
	public static final String QUERY_CONSULTAR_VENDAS_PRAZO = "venda.consultarVendasPrazo";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cod_venda")
	@Basic(optional=false)
	private Long id;
	
	@Column(name="dat_venda")
	@Basic(optional=false)
	private Date dataVenda = new Date();
	
	@Column(name="dat_pagamento")
	private Date dataPagamento;
	
	@Column(name="ind_finalizada")
	private Boolean finalizada = false;
	
	@Column(name="ind_venda_prazo")
	private Boolean vendaPrazo = false;
	
	@Column(name="ind_pago")
	private Boolean pago = false;
	
	@OneToMany(mappedBy="venda", orphanRemoval=true, cascade = CascadeType.ALL)
	private List<VendaProduto> vendaProdutos;
	
	@OneToOne
	@JoinColumn(name="cod_cliente")
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name="cod_mesa",nullable=false)
	private Mesa mesa;
	
	@Transient
	private Double precoTotal;

	public Long getId() {
		return id;
	}
	
	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public Boolean getVendaPrazo() {
		return vendaPrazo;
	}

	public void setVendaPrazo(Boolean vendaPrazo) {
		this.vendaPrazo = vendaPrazo;
	}

	public List<VendaProduto> getVendaProdutos() {
		return vendaProdutos;
	}

	public void setVendaProdutos(List<VendaProduto> vendaProdutos) {
		this.vendaProdutos = vendaProdutos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
	public Double getPrecoTotal() {
		Double total = 0D;
		for (VendaProduto vendProd : getVendaProdutos()) {
			total += (vendProd.getQuantidade() * vendProd.getProduto().getPreco());
		}
			
		return total;
	}

	public Boolean getPago() {
		return pago;
	}

	public void setPago(Boolean pago) {
		this.pago = pago;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
}
