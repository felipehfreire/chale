package br.com.chale.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="pessoa")
@NamedQueries({
	@NamedQuery(name=Pessoa.QUERY_CONSULTAR_TODOS, query="select p from Pessoa p "),
	@NamedQuery(name=Pessoa.QUERY_CONSULTAR_POR_NOME, query="select p from Pessoa p where nome like '%' || Upper(?1) || '%' or ?1 is null"),
})
public class Pessoa implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = 6755595084217031092L;

	public static final String QUERY_CONSULTAR_TODOS = "pessoa.consultarTodos";
	public static final String QUERY_CONSULTAR_POR_NOME= "consultarPessoaPorNome";;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codPessoa", nullable=false)
	private Long id;
	
	@Column(name="nome", length= 100, nullable=false)
	private String nome;
	
	@Column(name="telefone", length= 20, nullable=false)
	private String telefone;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
