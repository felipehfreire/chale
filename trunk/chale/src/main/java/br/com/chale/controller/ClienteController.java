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

import br.com.chale.entity.Cliente;
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
	
	private Cliente pessoa;
	private Cliente pessoaSelecionada;
	private List<Cliente> pessoas;
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
	
	public void excluir(Cliente cliente) {
		clienteService.excluir(cliente);
		pesquisar();
	}
	
	public void limpar() {
		pessoa  = new Cliente();
		pessoaSelecionada = new Cliente();
		termo = "";
		pessoas= new ArrayList<Cliente>();
	}
	
	public Cliente getPessoa() {
		return pessoa;
	}

	public void setPessoa(Cliente pessoa) {
		this.pessoa = pessoa;
	}

	public List<Cliente> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Cliente> pessoas) {
		this.pessoas = pessoas;
	}


	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}


	public Cliente getPessoaSelecionada() {
		return pessoaSelecionada;
	}


	public void setPessoaSelecionada(Cliente pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}
}
