package br.com.chale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.chale.converter.BaseEntity;

@Entity
@Table(name="mesa")
@NamedQueries({

	@NamedQuery(name=Mesa.CONSULTAR_TODAS_MESAS, query="Select m from Mesa m"),
	
	
})
public class Mesa implements BaseEntity{
	
	public static final String CONSULTAR_TODAS_MESAS = "consultarTodasMesas";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codMesa", nullable=false)
	private Long numeroMesa;
	
	@Column(name="usada")
	private Boolean usada;

	public Long getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Long numeroMesa) {
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

	@Override
	public Long getId() {
		return getNumeroMesa();
	}
	
	
	
}
