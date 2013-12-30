package br.com.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Pedido;
import br.com.chale.entity.PedidoProduto;
import br.com.chale.entity.PedidoProdutoId;
import br.com.chale.entity.Produto;
import br.com.chale.service.PedidoService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class PedidoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private Conversation conversation;
	
	private List<Mesa> mesas;
	private List<PedidoProduto> pedidosProdutos;
	private Mesa mesaSelecionada;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		PreencherMesas();
		pesquisar();
	}
	
	private void PreencherMesas() {
		mesas = pedidoService.consultarTodasMesas();
		
	}

	public void pesquisar() {
		//ConversationUtil.iniciarConversacao(conversation);
		//listagem = produtoService.pesquisar(me);
		
		for(int i=0;i<5; i++){
			Produto p = new Produto();
			p.setDescricao("prod"+(i+1));
			p.setPreco(Double.valueOf(i+1));
			p.setQtdEstoque(Long.valueOf(i));
			p.setQtdMinEstoque(Long.valueOf(i));
			p.setTipoServico(false);
			p.setId(Long.valueOf(i));
		
			Mesa m = new Mesa();
			m.setNumeroMesa(Long.valueOf(i+1));
			m.setUsada(false);
			
			Pedido ped = new Pedido();
			ped.setDataVenda(new Date());
			ped.setFinalizada(false);
			ped.setVendaPrazo(false);
			ped.setMesa(m);
			ped.setId((Long.valueOf(i+1)));
		
			
			
			PedidoProduto pp = new PedidoProduto();
			pp.setId(new PedidoProdutoId());
			pp.setPedido(ped);
			pp.setProduto(p);
			pp.getId().setProduto(p);
			pp.setQuantidade(Long.valueOf(i+4));
			
			pedidosProdutos.add(pp);
			
			
		}
		
	}
	
	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		limpar();
		return "/manterProduto.jsf?faces-redirect=true";
	}
	
	
//	public String editar() {
//		return "/manterProduto.jsf?faces-redirect=true";
//	}
//	
//	public void salvar() {
//		if (produto.getId() == null) {
//			produtoService.persistir(produto);
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
//		} else {
//			produtoService.atualizar(produto);
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Alterado com sucesso!"));
//		}
//		ConversationUtil.terminarConversacao(conversation);
//		novo();
//	}
//	
	
	public String voltar() {
		return "/?faces-redirect=true";
	}
	
	public void limpar() {
		mesas = new ArrayList<Mesa>();
		mesaSelecionada = new Mesa();
		pedidosProdutos = new ArrayList<PedidoProduto>();
	}

	public List<Mesa> getMesas() {
		return mesas;
	}

	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}

	public Mesa getMesaSelecionada() {
		return mesaSelecionada;
	}

	public void setMesaSelecionada(Mesa mesaSelecionada) {
		this.mesaSelecionada = mesaSelecionada;
	}

	public List<PedidoProduto> getPedidosProdutos() {
		return pedidosProdutos;
	}

	public void setPedidosProdutos(List<PedidoProduto> pedidosProdutos) {
		this.pedidosProdutos = pedidosProdutos;
	}

}
