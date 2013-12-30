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
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class ProdutoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private Conversation conversation;
	
	private String termo;
	private List<Produto> listagem;
	private Produto produto;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
	}
	
	public void pesquisar() {
		ConversationUtil.iniciarConversacao(conversation);
		listagem = produtoService.pesquisar(termo);
	}
	
	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		limpar();
		return "/manterProduto.jsf?faces-redirect=true";
	}
	
	public String editar() {
		return "/manterProduto.jsf?faces-redirect=true";
	}
	
	public void salvar() {
		if (produto.getId() == null) {
			produtoService.persistir(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
		} else {
			produtoService.atualizar(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Alterado com sucesso!"));
		}
		ConversationUtil.terminarConversacao(conversation);
		novo();
	}
	
	public String voltar() {
		return "/consultarProduto.jsf?faces-redirect=true";
	}
	
	public void limpar() {
		termo = "";
		listagem = new ArrayList<Produto>();
		produto = new Produto();
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

}
