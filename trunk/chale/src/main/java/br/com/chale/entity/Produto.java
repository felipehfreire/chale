package br.com.chale.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "produto")
@NamedQueries({
	
	@NamedQuery(name=Produto.QUERY_CONSULTAR_POR_NOME, query="select p from Produto p where descricao like '%' || Upper(?1) || '%' or ?1 is null"),
	
	@NamedQuery(name=Produto.QUERY_CONSULTAR_TODOS, query="select p from Produto p order by p.descricao"),
	
	@NamedQuery(name=Produto.QUERY_CONSULTAR_PRODS_QTD_MIN, query="select p from Produto p  where p.qtdMinEstoque >= p.qtdEstoque "),
	
	@NamedQuery(name=Produto.QUERY_POPULAR_AUTO_COMPLETE_PROD, query="select p from Produto p  where upper(p.descricao) like '%' || Upper(?1) || '%'or p.id = ?1  or ?1 is null "),
	
	
})
public class Produto implements BaseEntity,Serializable {
	
	private static final long serialVersionUID = 5130446222776884014L;
	
	public static final String QUERY_CONSULTAR_POR_NOME = "produto.consultarPorNome";
	public static final String QUERY_CONSULTAR_TODOS = "produto.consultarTodos";
	public static final String QUERY_CONSULTAR_PRODS_QTD_MIN = "produto.consultarProdutosEstoqueMin";
	public static final String QUERY_POPULAR_AUTO_COMPLETE_PROD = "produto.popularAutoCompletePro";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cod_produto", nullable=false)
	private Long id;

	@Column(name = "txt_descricao", length = 500, nullable=false)
	private String descricao;

	@Column(name = "num_qtd_estoque", nullable=true)
	private Long qtdEstoque;

	@Column(name = "num_preco", precision=10, scale=2, nullable=false)
	private Double preco;

	@Column(name = "ind_servico")
	private Boolean tipoServico;
	
	@Column(name = "ind_comida")
	private Boolean tipocomida;
	
	@Column(name = "ind_divd_estoque")
	private boolean dividirEstoque;

	@Column(name = "ind_qtd_min_estoque", nullable=true)
	private Long qtdMinEstoque;
	
	@OneToOne
	@JoinColumn(name="cod_produto_vinc",nullable=true)
	private Produto produtoVinculado;
	
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

	public Long getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Long qtdEstoque) {
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

	public Long getQtdMinEstoque() {
		return qtdMinEstoque;
	}

	public void setQtdMinEstoque(Long qtdMinEstoque) {
		this.qtdMinEstoque = qtdMinEstoque;
	}
	
	public String getPrecoFormatado() {
		 DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale ("pt", "BR"));
		 DecimalFormat df1 = new DecimalFormat("#,##0.00", dfs);
		 return "R$ " + df1.format(getPreco());
	}
	
	public Produto getProdutoVinculado() {
		return produtoVinculado;
	}

	public void setProdutoVinculado(Produto produtoVinculado) {
		this.produtoVinculado = produtoVinculado;
	}

	public Boolean getTipocomida() {
		return tipocomida;
	}

	public void setTipocomida(Boolean tipocomida) {
		this.tipocomida = tipocomida;
	}
	
	public boolean getDividirEstoque() {
		return dividirEstoque;
	}

	public void setDividirEstoque(boolean dividirEstoque) {
		this.dividirEstoque = dividirEstoque;
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
