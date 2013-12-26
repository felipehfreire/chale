package com.br.chale.entidades;

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
	
	@NamedQuery(name=Pessoa.CONSULTAR_CLIENTES,
			query="select p from Pessoa p" +
				" where (?1 is null or length(trim(?1)) = 0 or " +
				" upper(p.nome) like '%' || Upper(?1) || '%')"),
				
	@NamedQuery(name=Pessoa.CONSULTAR_CLIENTE_POR_ID,
		query="select p from Pessoa p where idPessoa = ?1"),
		
	@NamedQuery(name=Pessoa.CONSULTAR_PESSOA_DUPLICADA,
		query="select p from Pessoa p where p.nome = ?1" +
				" and p.telefone = ?2" +
				" and (p.idPessoa <> ?3 or ?3 is null)")
	
})

public class Pessoa {
	
	public static final String CONSULTAR_CLIENTES = "consultarClientes";
	public static final String CONSULTAR_CLIENTE_POR_ID = "consultarClientePorId";
	public static final String CONSULTAR_PESSOA_DUPLICADA = "consultarPessoaDuplicada";
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codPessoa")
	private Integer idPessoa;
	
	@Column(name="nome", length= 100)
	private String nome;
	
	@Column(name="telefone", length= 20)
	private String telefone;

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
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
