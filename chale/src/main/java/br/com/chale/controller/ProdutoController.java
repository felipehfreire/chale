package br.com.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Produto;
import br.com.chale.service.ProdutoService;
import br.com.chale.service.VendaService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class ProdutoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private VendaService vendaService;
	
	@Inject
	private Conversation conversation;
	
	private String termo;
	private List<Produto> listagem;
	private Produto produto;
	private boolean mesmoEstoque;
	private List<Produto> produtosSelect;
	private Produto produtoSelecionado;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		preencherProdutos();
		pesquisar();
	}
	
	
	public void pesquisar() {
		ConversationUtil.iniciarConversacao(conversation);
		listagem = produtoService.pesquisar(termo);
		
		if(termo != null && !termo.isEmpty()){
			if(listagem == null || listagem.isEmpty()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Não foram encontrados registros com o nome informado!"));
			}
		}
	}
	
	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		limpar();
		return "/secure/manterProduto.jsf?faces-redirect=true";
	}
	
	public String editar() {
		return "/secure/manterProduto.jsf?faces-redirect=true";
	}
	
	public void salvar() {
		
		if (produto.getId() == null) {
			if( produto.getDividirEstoque() && produtoSelecionado!= null ){
				Long qtdEstqExistente = produtoSelecionado.getQtdEstoque();
				Long qtdMinEstqExistente = produtoSelecionado.getQtdMinEstoque();
				if(qtdEstqExistente % 2 ==0){
					produto.setQtdEstoque((qtdEstqExistente/2));
					
				}else{
					produto.setQtdEstoque(((qtdEstqExistente-1)/2));
					
				}
				if(qtdMinEstqExistente % 2 ==0){
					produto.setQtdMinEstoque((qtdMinEstqExistente/2));
				}else{
					produto.setQtdMinEstoque(((qtdMinEstqExistente-1)/2));
				}
				produto.setProdutoVinculado(produtoSelecionado);
				produtoSelecionado.setProdutoVinculado(produto);
				
				
			}else if(produto.getDividirEstoque()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Favor preencher o produto que será divido o estoque ou desmarque a opção dividir estoque!"));
			}
			
			if(produto.getDividirEstoque()){
				produtoService.persistir(produto);
				produtoService.atualizar(produtoSelecionado);
			}else{
				produtoService.persistir(produto);
			}
			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
			
			
			
		} else {
			
			if(produto.getProdutoVinculado()!=null){
				//atualiza as duas quantidades do dividir estoque
				Long qtdEstqExistente = produto.getQtdEstoque();
				Long qtdMinEstqExistente = produto.getQtdMinEstoque();
				if(qtdEstqExistente % 2 ==0){
					produto.getProdutoVinculado().setQtdEstoque((qtdEstqExistente/2));
					
				}else{
					produto.getProdutoVinculado().setQtdEstoque(((qtdEstqExistente-1)/2));
					
				}
				if(qtdMinEstqExistente % 2 ==0){
					produto.getProdutoVinculado().setQtdMinEstoque((qtdMinEstqExistente/2));
				}else{
					produto.getProdutoVinculado().setQtdMinEstoque(((qtdMinEstqExistente-1)/2));
				}
				produtoService.atualizar(produto.getProdutoVinculado());	
			}
			
			produtoService.atualizar(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Alterado com sucesso!"));
		}
		ConversationUtil.terminarConversacao(conversation);
		novo();
	}
	
	public String voltar() {
		 return "/index.jsf?faces-redirect=true";  
	}
	
	public void excluir(Produto produto) {
		if (vendaService.pesquisarVendasPorProduto(produto.getId()).isEmpty()) {
			produtoService.excluir(produto);
			pesquisar();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Excluido com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Não é possível excluir produtos que possuem alguma venda!"));
		}
		
	}
	
	public void limpar() {
		termo = "";
		listagem = new ArrayList<Produto>();
		produtosSelect = new ArrayList<Produto>();
		produto = new Produto();
		mesmoEstoque = false;
		
	}
	
	private void preencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public List<Produto> popularAutoCompleteProduto(String nomeCod){
		return produtoService.popularAutoCompleteProduto(nomeCod);
	}
	
	public void changeQtdMinAndQtdEstq(){
		getProduto().setQtdEstoque(0L);
		getProduto().setQtdMinEstoque(0L);
	}
	
	
	public void changeAtualizaQtdMinAndQtdEstq(){
		if(produtoSelecionado != null){
			Long qtdEstqExistente = produtoSelecionado.getQtdEstoque();
			Long qtdMinEstqExistente = produtoSelecionado.getQtdMinEstoque();
			if(qtdEstqExistente % 2 ==0){
				produto.setQtdEstoque((qtdEstqExistente/2));
				
			}else{
				produto.setQtdEstoque(((qtdEstqExistente-1)/2));
				
			}
			if(qtdMinEstqExistente % 2 ==0){
				produto.setQtdMinEstoque((qtdMinEstqExistente/2));
			}else{
				produto.setQtdMinEstoque(((qtdMinEstqExistente-1)/2));
			}
		}
	}
	
	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public List<Produto> getListagem() {
		return listagem;
	}

	public void setListagem(List<Produto> listagem) {
		this.listagem = listagem;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public boolean isMesmoEstoque() {
		return mesmoEstoque;
	}


	public void setMesmoEstoque(boolean mesmoEstoque) {
		this.mesmoEstoque = mesmoEstoque;
	}


	public VendaService getVendaService() {
		return vendaService;
	}


	public void setVendaService(VendaService vendaService) {
		this.vendaService = vendaService;
	}


	public List<Produto> getProdutosSelect() {
		return produtosSelect;
	}


	public void setProdutosSelect(List<Produto> produtosSelect) {
		this.produtosSelect = produtosSelect;
	}


	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}


	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
}
