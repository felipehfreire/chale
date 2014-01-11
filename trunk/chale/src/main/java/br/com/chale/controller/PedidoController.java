package br.com.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.PedidoProduto;
import br.com.chale.entity.Produto;
import br.com.chale.service.PedidoProdutoService;
import br.com.chale.service.PedidoService;
import br.com.chale.service.ProdutoService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class PedidoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;

	@Inject
	private PedidoProdutoService pedidoProdutoService;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private ProdutoService produtoService;

	@Inject
	private Conversation conversation;

	//pesquisa
	private List<Mesa> mesas;
	private List<PedidoProduto> pedidosProdutos;
	private Mesa mesaSelecionada;
	private PedidoProduto pedidoProduto;
	private List<Produto> produtosInseridos;
	private List<Produto> produtosSelect;
	private Produto produtoSelecionado;
	
	private PedidoProduto pedProd;
	private Produto produto;
	private Date dataAtual;
	private boolean aVista;
	
	

	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		PreencherMesas();
		PreencherProdutos();
	}
	
	public void add() {  
//       produto = new Produto();
          
    }  

	private void PreencherMesas() {
		mesas = pedidoService.consultarTodasMesas();
	}
	
	private void PreencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public void changeMesa(ValueChangeEvent e){
		Mesa m = (Mesa) e.getNewValue();
		mesaSelecionada = m;
	}
	
//	public void changeProd(ValueChangeEvent e){
//		Produto p = (Produto) e.getNewValue();
//		produtoSelecionado = p;
//	}
	
	public void changeCodProd(ValueChangeEvent e){
		Produto p = new Produto();
		
		for (Produto prod : produtosSelect) {
			if(prod.getId() != null){
				if(prod.getId().equals(e.getNewValue())){
					p=prod;
				}
			}
		}
		produtoSelecionado = p;
	}
	

	public void pesquisar() {
		ConversationUtil.iniciarConversacao(conversation);
		
		if(mesaSelecionada.getNumeroMesa() != null){
			pedidosProdutos = pedidoProdutoService.pesquisarPedidos(mesaSelecionada);
		}else{
			pedidosProdutos = pedidoProdutoService.pesquisarPedidos();
		}
		
	}

	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		limpar();
		return "/manterPedido.jsf?faces-redirect=true";
	}

	 public String editar() {
		 return "/manterPedido.jsf?faces-redirect=true";
	 }
	
	// public void salvar() {
	// if (produto.getId() == null) {
	// produtoService.persistir(produto);
	// FacesContext.getCurrentInstance().addMessage(null, new
	// FacesMessage(FacesMessage.SEVERITY_INFO, "",
	// "Registro Inserido com sucesso!"));
	// } else {
	// produtoService.atualizar(produto);
	// FacesContext.getCurrentInstance().addMessage(null, new
	// FacesMessage(FacesMessage.SEVERITY_INFO, "",
	// "Registro Alterado com sucesso!"));
	// }
	// ConversationUtil.terminarConversacao(conversation);
	// novo();
	// }
	//

	public String voltar() {
		return "/index.jsf?faces-redirect=true";
	}

	public void limpar() {
		mesas = new ArrayList<Mesa>();
		mesaSelecionada = new Mesa();
		pedidosProdutos = new ArrayList<PedidoProduto>();
		
		produto = new Produto();
		pedidoProduto  = new PedidoProduto();
		dataAtual =new Date();
		produtoSelecionado = new Produto();
		produtosInseridos = new ArrayList<Produto>();
		produtosSelect = new ArrayList<Produto>();
	}
	
	public void atualizarSelect(Produto p){
		setProdutoSelecionado(p);
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

	public PedidoProduto getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(PedidoProduto pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}

	public List<Produto> getProdutosInseridos() {
		return produtosInseridos;
	}

	public void setProdutosInseridos(List<Produto> produtosInseridos) {
		this.produtosInseridos = produtosInseridos;
	}

	public List<Produto> getProdutosSelect() {
		return produtosSelect;
	}

	public void setProdutosSelect(List<Produto> produtosSelect) {
		this.produtosSelect = produtosSelect;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public boolean isaVista() {
		return aVista;
	}

	public void setaVista(boolean aVista) {
		this.aVista = aVista;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public PedidoProduto getPedProd() {
		return pedProd;
	}

	public void setPedProd(PedidoProduto pedProd) {
		this.pedProd = pedProd;
	}
	
}
