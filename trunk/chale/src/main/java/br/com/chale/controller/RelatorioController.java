package br.com.chale.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.PrintException;

import br.com.chale.entity.PedidoProduto;
import br.com.chale.service.PedidoProdutoService;
import br.com.chale.util.ConversationUtil;
import br.com.chale.util.ImpressaoTXTUtil;

@Named
@ManagedBean
@ConversationScoped
public class RelatorioController implements Serializable {
	
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private PedidoProdutoService pedidoProdutoService;
	
	@Inject
	private Conversation conversation;
	
	private List<PedidoProduto> listagem;
	private Date data;
	
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
	}
	
	
	public void relatorioVendaDiario() {
		ConversationUtil.iniciarConversacao(conversation);
		listagem = pedidoProdutoService.pesquisarPedidosDataAtual(new Date());
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		String cabecalho ="Relatório de venda realizadas no dia "+ sdf.format(data) + "\n\n";
		Double total = null;
		try {
			ImpressaoTXTUtil impressao = new ImpressaoTXTUtil();
			impressao.escreveImpressao(cabecalho);
		} catch (IOException | PrintException e) {
			e.printStackTrace();
			
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
	
	public String voltar() {
		 return "/index.jsf?faces-redirect=true";  
	}
	
	public void limpar() {
		listagem = new ArrayList<PedidoProduto>();
		data = new Date();
	
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
