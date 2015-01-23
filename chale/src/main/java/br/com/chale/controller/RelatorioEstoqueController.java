package br.com.chale.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.print.PrintException;

import org.primefaces.event.SelectEvent;

import br.com.chale.entity.Produto;
import br.com.chale.entity.Venda;
import br.com.chale.entity.VendaProduto;
import br.com.chale.service.ProdutoService;
import br.com.chale.service.VendaService;
import br.com.chale.util.ConversationUtil;
import br.com.chale.util.ImpressaoTXTUtil;

@Named
@ManagedBean
@ConversationScoped
public class RelatorioEstoqueController implements Serializable {
	
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private Conversation conversation;
	private List<Produto> produtos;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		recuperarListaProdutos();
	}
	
	
	private void recuperarListaProdutos() {
		produtos =  produtoService.pesquisarTodos();
	}

	
	public void relatorioEstoque(){
		
		ConversationUtil.iniciarConversacao(conversation);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if(produtos!= null&&!produtos.isEmpty()){
			String cabecalho ="Relatório de estoque: "+ sdf.format(new Date()) + "\n\n";
			String mensagem = cabecalho;
			mensagem += "Produto"+ retornaEspacoBranco("Produto", "Qtd.Estoque")+"Qtd.Estoque\n";
			for (Produto prod : produtos) {
				mensagem += prod.getDescricao()+ retornaPontos(prod.getDescricao(), prod.getQtdEstoque().toString())+ prod.getQtdEstoque().toString() +"\n";
			}
			mensagem += "\n\n\n\n\n\n\n\n";
			System.out.println(mensagem);
			try {
				ImpressaoTXTUtil impressao = new ImpressaoTXTUtil();
				impressao.escreveImpressao(mensagem);
			} catch (IOException | PrintException e) {
				e.printStackTrace();
				FacesMessage msg = new FacesMessage("ERRO:", "Não foi possível realizar a impressão !!");
		        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}else{
			FacesMessage msg = new FacesMessage("AVISO: ", "Não existem Produtos cadastrados com quantidade mínima!!");
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	private String retornaPontos(String descProdQtd, String preco) {
		int palavraSomada = descProdQtd.length()+preco.length();
		int loop = 47-palavraSomada;
		String pontos="";
		for(int i=0;i<loop; i++){
			pontos+=".";
		}
		return pontos;
	}


	private String retornaEspacoBranco(String pedido, String hora) {
		int palavraSomada = pedido.length()+hora.length();
		int loop = 47-palavraSomada;
		String espacos="";
		for(int i=0;i<loop; i++){
			espacos+=" ";
		}
		return espacos;
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
		 produtos = new ArrayList<Produto>();
	}
	
	public void somarNotas(){
		ConversationUtil.iniciarConversacao(conversation);
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		
		Double totalPedido = 0D;
		Double totalGeral = 0D;
		String descProdQtd="";
		String preco="";
		String mensagem= "";
		
	}


	public List<Produto> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
}
