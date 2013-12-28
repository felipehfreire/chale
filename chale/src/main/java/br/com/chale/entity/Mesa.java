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
@Table(name="mesa")

@NamedQueries({

		@NamedQuery(name=Mesa.CONSULTAR_TODAS_MESAS, query="Select m from Mesa m"),
		@NamedQuery(name=Mesa.CONSULTAR_MESAS_LIVRES, query="Select m from Mesa m where m.usada = false"),
		@NamedQuery(name=Mesa.CONSULTAR_MESA_POR_ID, query="select m from Mesa m where numeroMesa = ?1"),
		@NamedQuery(name=Mesa.CONSULTAR_MESA_POR_PEDIDO, query="select m from Pedido p inner join p.mesa m where m.numeroMesa = ?1")
		
		
})
public class Mesa {
	
	public static final String CONSULTAR_TODAS_MESAS = "consultarTodasMesas";
	public static final String CONSULTAR_MESAS_LIVRES = "consultarMesasLivres";
	public static final String CONSULTAR_MESA_POR_ID = "consultarMesaPorId";
	public static final String CONSULTAR_MESA_POR_PEDIDO = "consultarMesaPorPedido";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codMesa")
	private Integer numeroMesa;
	
	@Column(name="usada")
	private Boolean usada;

	public Integer getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Integer numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public Boolean getUsada() {
		return usada;
	}

	public void setUsada(Boolean usada) {
		this.usada = usada;
	}
	
	public String toString() {
		return getNumeroMesa().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((numeroMesa == null) ? 0 : numeroMesa.hashCode());
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
		Mesa other = (Mesa) obj;
		if (numeroMesa == null) {
			if (other.getNumeroMesa() != null)
				return false;
		} else if (!numeroMesa.equals(other.getNumeroMesa()))
			return false;
		return true;
	}
	
	
	
}
