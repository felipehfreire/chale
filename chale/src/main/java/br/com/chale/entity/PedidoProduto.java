package br.com.chale.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="produto_pedido")
@NamedQueries({
	
	@NamedQuery(name=PedidoProduto.QUERY_CONSULTAR_TODOS_PEDIDOS, query="select pp from PedidoProduto pp "),
			
	@NamedQuery(name=PedidoProduto.CONSULTAR_PEDIDOS_POR_MESA, query="select pp from PedidoProduto pp" +
			" inner join pp.pedido p " +
			" where p.mesa = ?1"),
	
	@NamedQuery(name=PedidoProduto.CONSULTAR_PEDIDO_PROD_POR_ID, query="select pp from PedidoProduto pp" +
			" where id = ?1")
	
})
public class PedidoProduto implements Serializable{
	
	private static final long serialVersionUID = 2080039836842714937L;
	
	public static final String QUERY_CONSULTAR_TODOS_PEDIDOS = "consultarTodosPedidos";
	public static final String CONSULTAR_PEDIDOS_POR_MESA = "consultarPedidosPorMesa";
	public static final String CONSULTAR_PEDIDO_PROD_POR_ID = "consultarPedidosProdutoPorMesa";
	
	@EmbeddedId
	private PedidoProdutoId id;
	
	@ManyToOne
	@JoinColumn(name="codVenda", insertable=false, updatable=false)
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="codProd", insertable=false,updatable=false)
	private Produto produto;
	
	@Column(name="quantidade", nullable=false)
	private Long quantidade;

	@Transient
	private Double precoTotal;
	
	public PedidoProdutoId getId() {
		return id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoTotal() {
		if(produto!= null){
			return (quantidade * produto.getPreco());
		}
		return null;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}
	
}
