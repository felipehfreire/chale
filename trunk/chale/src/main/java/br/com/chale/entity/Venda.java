package br.com.chale.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@NamedQuery(name=Venda.QUERY_CONSULTAR_TODAS_VENDAS, 
		query="select p from Venda p "),
	@NamedQuery(name=Venda.CONSULTAR_VENDAS_NAO_FINALIZADAS_POR_MESA, 
		query="select p from Venda p " +
			" where p.mesa = ?1 and finalizada = false"),
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_POR_DATA, 
		query="select p from Venda p "
			+ " where p.finalizada = true "
			+ " and YEAR(p.dataVenda) = YEAR(?1) "
			+ " and Month(p.dataVenda) = Month(?1) "
			+ " and Day(p.dataVenda) = Day(?1) "),
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_PRAZO, 
		query="select p from Venda p "
			+ " where p.finalizada = true "
			+ " and pago = false "),
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_PRAZO_POR_MES, 
		query="select p from Venda p "
			+ " where p.finalizada = true "
			+ " and pago = false "
			+ " and YEAR(p.dataVenda) = YEAR(?1) "
			+ " and Month(p.dataVenda) = Month(?1) "),
			
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_NAO_FINALIZADAS, 
			query="select p from Venda p " +
					" where finalizada = false"),
	
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_POR_CLIENTE,
			query="select v from Venda v"
					+ " where v.cliente.id = ?1"),
					
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_POR_PRODUTO,
			query="select v from Venda v"
					+ " inner join v.vendaProdutos vp"
					+ " where vp.produto.id = ?1"),
	
	@NamedQuery(name=Venda.QUERY_CONSULTAR_VENDAS_PRAZO_PERIODO, 
	query="select p from Venda p "
		+ " where p.finalizada = true "
		+ " and pago = false "
		+ " and  p.dataVenda BETWEEN ?1 and ?2"),
	@NamedQuery(name=Venda.CONSULTAR_VENDAS_NAO_FINALIZADAS_POR_CLIENTE, 
		query="select p from Venda p " +
			" where p.cliente= ?1 and finalizada = false"),
	
})
public class Venda implements BaseEntity, Serializable {
	
	private static final long serialVersionUID = -5412811770343143282L;

	public static final String QUERY_CONSULTAR_TODAS_VENDAS = "venda.queryConsultarTodasVendas";
	public static final String CONSULTAR_VENDAS_NAO_FINALIZADAS_POR_MESA = "venda.consultarVendasNaoFinalizadasPorMesa";
	public static final String QUERY_CONSULTAR_VENDAS_POR_DATA = "venda.consultarVendasPorData";
	public static final String QUERY_CONSULTAR_VENDAS_PRAZO = "venda.consultarVendasPrazo";
	public static final String QUERY_CONSULTAR_VENDAS_PRAZO_POR_MES = "venda.consultarVendasPrazoMes";
	public static final String QUERY_CONSULTAR_VENDAS_NAO_FINALIZADAS = "venda.consultarVendasNaoFinalizadas";
	public static final String QUERY_CONSULTAR_VENDAS_POR_CLIENTE = "venda.consultarVendasPorCliente";
	public static final String QUERY_CONSULTAR_VENDAS_POR_PRODUTO = "venda.consultarVendasPorProduto";
	public static final String QUERY_CONSULTAR_VENDAS_PRAZO_PERIODO = "venda.onsultarVendasPrazoPeriodo";
	public static final String CONSULTAR_VENDAS_NAO_FINALIZADAS_POR_CLIENTE = "venda.consultarVendasPrazoCliente";
	

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
	
	@OneToMany(mappedBy="venda", orphanRemoval=true, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<VendaProduto> vendaProdutos;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="cod_cliente")
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name="cod_mesa",nullable=true)
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
		if( getVendaProdutos()!=null && !getVendaProdutos().isEmpty()){
			for (VendaProduto vendProd : getVendaProdutos()) {
				total += (vendProd.getQuantidade() * vendProd.getProduto().getPreco());
			}
		}
		
		return total;
	}
	
	public String getPrecoTotalFormatado() {
		 DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale ("pt", "BR"));
		 DecimalFormat df1 = new DecimalFormat("#,##0.00", dfs);
		 return "R$ " + df1.format(getPrecoTotal());
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
