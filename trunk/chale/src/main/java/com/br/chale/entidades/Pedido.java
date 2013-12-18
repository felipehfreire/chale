package com.br.chale.entidades;



import java.util.Date;
import java.util.List;

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

@Entity
@Table(name="pedido")

@NamedQueries({
	
	@NamedQuery(name=Pedido.CONSULTAR_PEDIDOS, query="select p from Pedido p" +
			" where (p.mesa.numeroMesa = ?1 or ?1 is null)"),
	
	@NamedQuery(name=Pedido.CONSULTAR_PEDIDO_POR_ID, query="select p from Pedido p" +
			" where codVenda = ?1")
	
})

//TODO mudar nome da entidade para venda (banco tbm)
public class Pedido {
	
	public static final String CONSULTAR_PEDIDOS = "consultarPedidos";
	public static final String CONSULTAR_PEDIDO_POR_ID = "consultarPedidoPorId";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codVenda")
	private Integer codVenda;
	
	@Column(name="dataVenda")
	private Date dataVenda = new Date();
	
	@Column(name="finalizada")
	private Boolean finalizada = false;
	
	@Column(name="vendaPrazo")
	private Boolean vendaPrazo = false;
	
	@OneToMany(mappedBy="produto", cascade = CascadeType.ALL)
	private List<PedidoProduto> pedidosProdutos;
	
	@JoinColumn(name="pessoa")
	@OneToOne
	private Pessoa pessoa;

	@JoinColumn(name="mesa")
	@OneToOne()
	private Mesa mesa;

	public Integer getCodVenda() {
		return codVenda;
	}

	public void setCodVenda(Integer codVenda) {
		this.codVenda = codVenda;
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

	public List<PedidoProduto> getPedidosProdutos() {
		return pedidosProdutos;
	}

	public void setPedidosProdutos(List<PedidoProduto> pedidosProdutos) {
		this.pedidosProdutos = pedidosProdutos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
//	public String getTotalPedido() {
//		Double valor = 0D;
//		if (getPedidosProdutos() != null) {
//			for (PedidoProduto pedProd : getPedidosProdutos()) {
//				valor = valor + pedProd.getProduto().getPreco();
//			}
//		}
//		return valor.toString();
//	}
	
}
