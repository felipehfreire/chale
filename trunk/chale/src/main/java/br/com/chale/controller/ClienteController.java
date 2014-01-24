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

import br.com.chale.entity.Pessoa;
import br.com.chale.service.ClienteService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class ClienteController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ClienteService clienteService;
	
	@Inject
	private Conversation conversation;
	
	private Pessoa pessoa;
	private Pessoa pessoaSelecionada;
	private List<Pessoa> pessoas;
	private String termo;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		pesquisar();
	}
	
	
	public void salvar() {
		if (pessoa.getId() == null) {
			clienteService.persistir(pessoa);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
		} else {
			clienteService.atualizar(pessoa);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Alterado com sucesso!"));
		}
		ConversationUtil.terminarConversacao(conversation);
		novo();
	}
	
	public void pesquisar(){
		ConversationUtil.iniciarConversacao(conversation);
		pessoas= clienteService.pesquisar(termo);
	}
	
	public String editar() {
		return "/manterCliente.jsf?faces-redirect=true";
	}
	
	public String voltar() {
		return "/index.jsf?faces-redirect=true";
	}
	
	public String novo() {
		ConversationUtil.iniciarConversacao(conversation);
		limpar();
		return "/manterCliente.jsf?faces-redirect=true";
	}
	
	public void limpar() {
		pessoa  = new Pessoa();
		pessoaSelecionada = new Pessoa();
		termo = "";
		pessoas= new ArrayList<Pessoa>();
	}
	
	public void deletar() {
	//	ConversationUtil.iniciarConversacao(conversation);
		pessoas.remove(pessoaSelecionada);
		clienteService.excluir(pessoaSelecionada);
		iniciar();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}


	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}


	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}


	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

}
