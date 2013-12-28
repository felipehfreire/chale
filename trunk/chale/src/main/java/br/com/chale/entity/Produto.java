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

	@NamedQuery(name = Produto.CONSULTAR_PRODUTOS, query = "select p from Produto p"
		+ " where (?1 is null or length(trim(?1)) = 0 or "
		+ " upper(p.descricao) like '%' || Upper(?1) || '%')"
		+ " and (p.idProd = ?2 or ?2 is null)"),
		
	@NamedQuery(name=Produto.CONSULTAR_PRODUTO_POR_ID, query="select p from Produto p" +
			" where idProd = ?1"),
			
	@NamedQuery(name=Produto.CONSULTAR_TODOS_PRODUTOS, query="select p from Produto p" ),
	
	@NamedQuery(name=Produto.CONSULTAR_PRODUTOS_DUPLICADOS, 
			query="select p from Produto p where p.descricao = ?1 " +
					" and (p.idProd = ?2 or ?2 is null)")
			
})
public class Produto {

	public static final String CONSULTAR_PRODUTOS = "consultarProdutos";

	public static final String CONSULTAR_PRODUTO_POR_ID = "consultarProdutoPorId";
	
	public static final String CONSULTAR_TODOS_PRODUTOS = "consultarTodosProdutos";

	public static final String CONSULTAR_PRODUTOS_DUPLICADOS = "consultarProdutosDuplicados";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codProd")
	private Integer idProd;

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

	public Integer getIdProd() {
		return idProd;
	}

	public void setIdProd(Integer idProd) {
		this.idProd = idProd;
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
		result = prime * result + ((idProd == null) ? 0 : idProd.hashCode());
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
		if (idProd == null) {
			if (other.getIdProd() != null)
				return false;
		} else if (!idProd.equals(other.getIdProd()))
			return false;
		return true;
	}
	
}
