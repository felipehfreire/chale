package com.br.chale.entidades;









import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.br.chale.dao.Produto;

@Entity
@Table(name="produto_pedido")
public class PedidoProduto {
	
	@EmbeddedId
	private PedidoProdutoId id;
	
	@ManyToOne
	@JoinColumn(name="codVenda", insertable=false, updatable=false)
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="codProd", insertable=false,updatable=false)
	private Produto produto;
	
	@Column(name="quantidade")
	private Long quantidade;

	public PedidoProdutoId getId() {
		return id;
	}

	public void setId(PedidoProdutoId id) {
		this.id = id;
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
	
}
