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
import br.com.chale.entity.Produto;
import br.com.chale.service.PedidoService;
import br.com.chale.service.ProdutoService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class PedidoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private ProdutoService produtoService;

	@Inject
	private Conversation conversation;

	private List<Mesa> mesas;
	private List<Pedido> pedidos;
	private List<Produto> produtosInseridos;
	private List<Produto> produtosSelect;
	private Date dataAtual;
	private boolean aVista;
	//Jhonatan
	private PedidoProduto pedidoProduto;
	private Produto produtoSelecionado;
	private Mesa mesaSelecionada;
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
		produtoSelecionado = produtoService.getById(idProd);
	}
	
	public void reRenderIdProduto() {
		idProd = produtoSelecionado.getId();
		
	}
	
	public void add() {
		if (getProdutoSelecionado().getQtdEstoque().equals(0L) || getProdutoSelecionado().getQtdEstoque() < pedidoProduto.getQuantidade()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
			
		} else if (pedido.getPedidosProdutos().contains(pedidoProduto)) {
			//quando clicar no add e o produto já estiver na lista, só atualizar os valores
			adicionar();
			
		} else {
			//TODO o cara pode mudar de mesa
			getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - pedidoProduto.getQuantidade());
			produtoService.atualizar(getProdutoSelecionado());
			pedidoProduto.setProduto(getProdutoSelecionado());
			pedidoProduto.setPedido(pedido);
			pedido.getPedidosProdutos().add(pedidoProduto);
			
			if (pedido.getId() == null) {
				 pedidoService.persistir(pedido);
			 } else {
				 pedidoService.atualizar(pedido);
			 }
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
			limparAdd();
		}
    } 
	
	private void limparAdd() {
		pedidoProduto = new PedidoProduto();
		produtoSelecionado = new Produto();
		idProd = null;
	}

	private void PreencherMesas() {
		mesas = pedidoService.consultarTodasMesas();
	}
	
	private void PreencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public void pesquisar() {
		
		if(mesaSelecionada != null && mesaSelecionada.getNumeroMesa() != null){
			pedidos = pedidoService.pesquisarPedidos(mesaSelecionada);
		}else{
			pedidos = pedidoService.pesquisarPedidos();
		}
		
	}

	public String novo() {
		return "/manterPedido.jsf?faces-redirect=true";
	}
	
	/**
	 * Adiciona mais um "pedido" do produto selecionado
	 */
	public void adicionar() {
		if (!pedidoProduto.getProduto().getQtdEstoque().equals(0L)) {
			pedidoProduto.getProduto().setQtdEstoque(pedidoProduto.getProduto().getQtdEstoque() - 1);
			produtoService.atualizar(pedidoProduto.getProduto());
			
			for (PedidoProduto pedProd : pedido.getPedidosProdutos()) {
				if (pedProd.equals(pedidoProduto)) {
					pedProd.setQuantidade(pedProd.getQuantidade() + 1);
				}
			}
			pedidoService.atualizar(pedido);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
		}
		limparAdd();
	}
	
	
	public void subtrair() {
		if (!pedidoProduto.getProduto().getQtdEstoque().equals(0L)) {
			pedidoProduto.getProduto().setQtdEstoque(pedidoProduto.getProduto().getQtdEstoque() + 1);
			produtoService.atualizar(pedidoProduto.getProduto());
			
			for (PedidoProduto pedProd : pedido.getPedidosProdutos()) {
				if (pedProd.equals(pedidoProduto)) {
					pedProd.setQuantidade(pedProd.getQuantidade() - 1);
				}
			}
			pedidoService.atualizar(pedido);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
		}
		limparAdd();
	}
	
	public void deletar() {
		pedidoProduto.getProduto().setQtdEstoque(pedidoProduto.getProduto().getQtdEstoque() + pedidoProduto.getQuantidade());
		produtoService.atualizar(pedidoProduto.getProduto());
		pedido.getPedidosProdutos().remove(pedidoProduto);
		pedidoService.atualizar(pedido);
		limparAdd();
	}

	 public String editar() {
		 return "/manterPedido.jsf?faces-redirect=true";
	 }
	
	 public void finalizar() {
		 if (pedido.getPedidosProdutos().size() == 0) {
			 FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					 "Não é possível finalizar o pedido sem ao menos selecionar um produto!"));
		 }
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
		ConversationUtil.iniciarConversacao(conversation);
		pedido = new Pedido();
		pedido.setPedidosProdutos(new ArrayList<PedidoProduto>());
		
		mesaSelecionada = new Mesa();
		pedidos = new ArrayList<Pedido>();
		
		pedidoProduto  = new PedidoProduto();
		pedidoProduto.setPedido(pedido);
		dataAtual =new Date();
		produtoSelecionado = new Produto();
		produtosInseridos = new ArrayList<Produto>();
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

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
