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
@Table(name="usuario")
@NamedQueries({
	@NamedQuery(name=Usuario.QUERY_CONSULTAR_USUARIO, query="select u from Usuario u where nome = ?1 and senha = ?2"),
})
public class Usuario implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = 6755595084217031092L;

	public static final String QUERY_CONSULTAR_USUARIO = "consultarUsuario";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codUsuario", nullable=false)
	private Long id;
	
	@Column(name="usuario", length= 100, nullable=false)
	private String usuario;
	
	@Column(name="senha", length= 20, nullable=false)
	private String senha;

	public Long getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
