package br.com.chale.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedido")

//TODO mudar nome da entidade para venda (banco tbm)
public class Pedido implements BaseEntity,Serializable{
	

	private static final long serialVersionUID = -5412811770343143282L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="codVenda", nullable=false)
	private Long id;
	
	@Column(name="dataVenda", nullable=false)
	private Date dataVenda = new Date();
	
	@Column(name="finalizada")
	private Boolean finalizada = false;
	
	@Column(name="vendaPrazo")
	private Boolean vendaPrazo = false;
	
	@OneToMany(mappedBy="produto", cascade = CascadeType.ALL)
	private List<PedidoProduto> pedidosProdutos;
	
	@JoinColumn(name="pessoa")
	@OneToOne
	private Pessoa pessoa;

	@JoinColumn(name="mesa")
	@OneToOne()
	private Mesa mesa;

	public Long getId() {
		return id;
	}
	
	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public Boolean getVendaPrazo() {
		return vendaPrazo;
	}

	public void setVendaPrazo(Boolean vendaPrazo) {
		this.vendaPrazo = vendaPrazo;
	}

	public List<PedidoProduto> getPedidosProdutos() {
		return pedidosProdutos;
	}

	public void setPedidosProdutos(List<PedidoProduto> pedidosProdutos) {
		this.pedidosProdutos = pedidosProdutos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
}
