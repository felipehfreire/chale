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
	@NamedQuery(name=Usuario.QUERY_CONSULTAR_USUARIO, query="select u from Usuario u where usuario = ?1 and senha = md5(?2)"),
	@NamedQuery(name=Usuario.QUERY_CONSULTAR_SENHA, query="select u from Usuario u where usuario = ?1 and senha = md5(?2)"),
})
public class Usuario implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = 6755595084217031092L;

	public static final String QUERY_CONSULTAR_USUARIO = "usuario.consultarUsuario";
	public static final String QUERY_CONSULTAR_SENHA = "usuario.consultarSenha";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cod_usuario", nullable=false)
	private Long id;
	
	@Column(name="txt_nome_usuario", length= 100, nullable=false)
	private String usuario;
	
	@Column(name="pw_senha", length= 32, nullable=false)
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
