package br.com.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Mesa;
import br.com.chale.entity.Pedido;
import br.com.chale.entity.PedidoProduto;
import br.com.chale.entity.PedidoProdutoId;
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
	private PedidoProduto pedidoProduto;
	private List<Produto> produtosInseridos;
	private List<Produto> produtosSelect;
	
	private Mesa mesaSelecionada;
	private Produto produtoSelecionado;
	
	private Produto produto;
	private Date dataAtual;
	private boolean aVista;
	//Jhonatan
	private Long idProd;
	private Pedido pedido;
	

	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		PreencherMesas();
		PreencherProdutos();
	}
	
	public void reRenderProduto() {
		produto = produtoService.getById(idProd);
		pedidoProduto.setProduto(produto);
		pedidoProduto.getId().setProduto(produto);
	}
	
	public void reRenderIdProduto() {
		idProd = pedidoProduto.getProduto().getId();
		pedidoProduto.setProduto(pedidoProduto.getProduto());
		pedidoProduto.getId().setProduto(pedidoProduto.getProduto());
	}
	
	public void add() {  
		//TODO validar quantidade no estoque
		 //TODO diminuir do estoque
		pedidoProduto.getId().setPedido(pedido);
		pedidoProduto.setPedido(pedido);
		pedido.getPedidosProdutos().add(pedidoProduto);
		
		if (pedido.getId() == null) {
			 pedidoService.persistir(pedido);
		 } else {
			 pedidoService.atualizar(pedido);
		 }
		FacesContext.getCurrentInstance().addMessage(null, new
				 FacesMessage(FacesMessage.SEVERITY_INFO, "",
						 "Registro Inserido com sucesso!"));
		
    }  

	private void PreencherMesas() {
		mesas = pedidoService.consultarTodasMesas();
	}
	
	private void PreencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public void pesquisar() {
		ConversationUtil.iniciarConversacao(conversation);
		
		if(mesaSelecionada != null && mesaSelecionada.getNumeroMesa() != null){
			pedidosProdutos = pedidoProdutoService.pesquisarPedidos(mesaSelecionada);
		}else{
			pedidosProdutos = pedidoProdutoService.pesquisarPedidos();
		}
		
	}

	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		return "/manterPedido.jsf?faces-redirect=true";
	}

	 public String editar() {
		 return "/manterPedido.jsf?faces-redirect=true";
	 }
	
	 public void finalizar() {
		 //TODO validar quantidade de produtos adicionados
		 //TODO adicionar lista de pessoas caso for a prazo
		 pedido.setFinalizada(true);
		 pedidoService.atualizar(pedido);
		 FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Alterado com sucesso!"));
		 ConversationUtil.terminarConversacao(conversation);
	 }
	

	public String voltar() {
		return "/index.jsf?faces-redirect=true";
	}

	public void limpar() {
		mesaSelecionada = new Mesa();
		pedidosProdutos = new ArrayList<PedidoProduto>();
		
		produto = new Produto();
		pedidoProduto  = new PedidoProduto();
		pedidoProduto.setId(new PedidoProdutoId());
		pedidoProduto.setPedido(pedido);
		pedidoProduto.getId().setPedido(pedido);
		dataAtual =new Date();
		produtoSelecionado = new Produto();
		produtosInseridos = new ArrayList<Produto>();
		
		pedido = new Pedido();
		pedido.setPedidosProdutos(new ArrayList<PedidoProduto>());
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

	public Long getIdProd() {
		return idProd;
	}

	public void setIdProd(Long idProd) {
		this.idProd = idProd;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
