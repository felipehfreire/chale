package br.com.chale.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="venda_produto")
public class VendaProduto implements Serializable{
	
	private static final long serialVersionUID = 2080039836842714937L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cod_venda_produto")
	@Basic(optional=false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="cod_venda")
	@Basic(optional=false)
	private Venda venda;
	
	@ManyToOne
	@JoinColumn(name="cod_produto")
	@Basic(optional=false)
	private Produto produto;
	
	@Column(name="num_quantidade")
	@Basic(optional=false)
	private Long quantidade;
	
	public Long getId() {
		return id;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
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

	public Double getPrecoQtdProd() {
		Double preco = null;
		if(getProduto()!= null){
			preco = getQuantidade()*getProduto().getPreco();
		}
		return preco;
	}
	
	public String getPrecoQtdProdFormatado() {
		 DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale ("pt", "BR"));
		 DecimalFormat df1 = new DecimalFormat("#,##0.00", dfs);
		 return "R$ " + df1.format(getPrecoQtdProd());
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
		VendaProduto other = (VendaProduto) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	
	
}
