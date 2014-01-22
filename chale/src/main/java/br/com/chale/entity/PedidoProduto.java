package br.com.chale.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="produto_pedido")
public class PedidoProduto implements Serializable{
	
	private static final long serialVersionUID = 2080039836842714937L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codPedidoProduto", nullable=false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="codVenda", nullable=false)
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="codProd", nullable=false)
	private Produto produto;
	
	@Column(name="quantidade", nullable=false)
	private Long quantidade;

	public Long getId() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoProduto other = (PedidoProduto) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	
	
}
