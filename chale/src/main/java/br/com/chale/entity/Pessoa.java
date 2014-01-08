package br.com.chale.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pessoa")
public class Pessoa implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = 6755595084217031092L;

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
