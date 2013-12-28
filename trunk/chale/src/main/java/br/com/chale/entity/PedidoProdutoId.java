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
				+ ((pedido.getCodVenda() == null) ? 0 : pedido.getCodVenda().hashCode());
		result = prime * result
				+ ((produto.getIdProd() == null) ? 0 : produto.getIdProd().hashCode());
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
		if (pedido.getCodVenda() == null) {
			if (other.getPedido().getCodVenda() != null)
				return false;
		} else if (!pedido.getCodVenda().equals(other.getPedido().getCodVenda()))
			return false;
		if (produto.getIdProd() == null) {
			if (other.getProduto().getIdProd() != null)
				return false;
		} else if (!produto.getIdProd().equals(other.getProduto().getIdProd()))
			return false;
		return true;
	}
	
	
	
}
