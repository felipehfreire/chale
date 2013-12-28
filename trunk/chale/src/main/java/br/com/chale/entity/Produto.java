package br.com.chale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "produto")
@NamedQueries({
	
	@NamedQuery(name=Produto.QUERY_CONSULTAR_POR_NOME, query="select p from Produto p where descricao like '%' || Upper(?1) || '%' or ?1 is null")
	
})
public class Produto {

	public static final String QUERY_CONSULTAR_POR_NOME = "consultarPorNome";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codProd")
	private Long id;

	@Column(name = "descricao", length = 200)
	private String descricao;

	@Column(name = "qtdEstoque")
	private Integer qtdEstoque;

	@Column(name = "preco")
	private Double preco;

	@Column(name = "tipoServico")
	private Boolean tipoServico;

	@Column(name = "qtdMinEstoque")
	private Integer qtdMinEstoque;
	
	public String toString() {
		return getDescricao().toString();
	}

	public Long getId() {
		return id;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(String preco) {
		
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Boolean getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(Boolean tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Integer getQtdMinEstoque() {
		return qtdMinEstoque;
	}

	public void setQtdMinEstoque(Integer qtdMinEstoque) {
		this.qtdMinEstoque = qtdMinEstoque;
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
		if (!(obj instanceof Produto)) 
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
}
