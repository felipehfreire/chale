package br.com.chale.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PedidoProdutoId implements Serializable {
	
	private static final long serialVersionUID = 4852833738314674659L;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="codVenda")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="codProd")
	private Produto produto;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pedido.getId() == null) ? 0 : pedido.getId().hashCode());
		result = prime * result
				+ ((produto.getId() == null) ? 0 : produto.getId().hashCode());
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
		PedidoProdutoId other = (PedidoProdutoId) obj;
		if (pedido.getId() == null) {
			if (other.getPedido().getId() != null)
				return false;
		} else if (!pedido.getId().equals(other.getPedido().getId()))
			return false;
		if (produto.getId() == null) {
			if (other.getProduto().getId() != null)
				return false;
		} else if (!produto.getId().equals(other.getProduto().getId()))
			return false;
		return true;
	}
	
	
	
}
