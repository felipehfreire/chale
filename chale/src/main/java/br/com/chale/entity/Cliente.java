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
@Table(name="cliente")
@NamedQueries({
	@NamedQuery(name=Cliente.QUERY_CONSULTAR_TODOS, query="select p from Cliente p "),
	@NamedQuery(name=Cliente.QUERY_CONSULTAR_POR_NOME, query="select p from Cliente p where nome like '%' || Upper(?1) || '%' or ?1 is null"),
	@NamedQuery(name=Cliente.QUERY_CONSULTAR_POR_COD_NOME, query="select c from Cliente c where upper(c.nome) like '%' || Upper(?1) || '%'or c.id = ?1  or ?1 is null "),
})
public class Cliente implements BaseEntity, Serializable {
	
	private static final long serialVersionUID = 6755595084217031092L;

	public static final String QUERY_CONSULTAR_TODOS = "cliente.consultarTodos";
	public static final String QUERY_CONSULTAR_POR_NOME= "cliente.consultarClientePorNome";
	public static final String QUERY_CONSULTAR_POR_COD_NOME= "cliente.consultarClientePorcodNome";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cod_cliente", nullable=false)
	private Long id;
	
	@Column(name="txt_nome", length= 100, nullable=false)
	private String nome;
	
	@Column(name="txt_telefone", length= 20, nullable=false)
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	
	
}
