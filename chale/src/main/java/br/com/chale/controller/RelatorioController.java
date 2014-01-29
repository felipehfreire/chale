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
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.PrintException;

import org.primefaces.event.SelectEvent;

import br.com.chale.entity.Pedido;
import br.com.chale.entity.PedidoProduto;
import br.com.chale.entity.Produto;
import br.com.chale.service.PedidoService;
import br.com.chale.service.ProdutoService;
import br.com.chale.util.ConversationUtil;
import br.com.chale.util.ImpressaoTXTUtil;

@Named
@ManagedBean
@ConversationScoped
public class RelatorioController implements Serializable {
	
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private Conversation conversation;
	
	private List<Pedido> pedidosFinalizados;
	private List<Produto> produtosQtdMin;
	private Date data;
	
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
	}
	
	public void impressaoPedidoFinalizado(Pedido p){
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		String cabecalho ="            Pousada Vale dos ventos \n\n"; 
		String mesa="Mesa:"+ p.getMesa()+retornaEspacoBranco("Mesa:"+ p.getMesa(), "Hora:"+new SimpleDateFormat("HH:mm").format(new Date()))+"Hora:"+new SimpleDateFormat("HH:mm").format(new Date())+"\n";
		
		String cabecalhoVenda ="Itens"+retornaEspacoBranco("Itens", "Qtd/preço")+"Qtd/preço\n";
		String mensagem = cabecalho+ mesa+cabecalhoVenda;
		Double totalPedido = 0D;
		String descProdQtd="";
		String preco="";
		for (PedidoProduto pedProd : p.getPedidosProdutos()) {
			
			descProdQtd= pedProd.getProduto().getDescricao();
			preco = "("+pedProd.getQuantidade()+"Un.)"+"R$"+(dcmFmt.format( pedProd.getProduto().getPreco()));
			mensagem+=descProdQtd+retornaPontos(descProdQtd, preco)+preco+"\n";
			totalPedido+=pedProd.getProduto().getPreco()*pedProd.getQuantidade();
		}
		mensagem +=retornaEspacoBranco("","Total: " + dcmFmt.format(totalPedido) )+"Total: " + dcmFmt.format(totalPedido) +"\n\n";
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
	}
	
	public void relatorioVendaDiario() throws ParseException {
		ConversationUtil.iniciarConversacao(conversation);
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		data= sdf.parse(sdf.format(data));
		pedidosFinalizados = pedidoService.pesquisarPedidosData(data);
		String cabecalho ="Relatório de venda realizadas no dia "+ sdf.format(data) + "\n\n";
		Double totalPedido = 0D;
		Double totalGeral = 0D;
		String descProdQtd="";
		String preco="";
		String mensagem = cabecalho;
		int pedido  = 0;
		
		if(pedidosFinalizados!= null||!pedidosFinalizados.isEmpty()){
			for (Pedido p : pedidosFinalizados) {
				mensagem += "Pedido:" +pedido +retornaEspacoBranco("Pedido:" +pedido," Hora:"+new SimpleDateFormat("HH:mm").format(p.getDataVenda())) +" Hora:"+new SimpleDateFormat("HH:mm").format(p.getDataVenda())+ "\n" ;
				for (PedidoProduto pp : p.getPedidosProdutos()) {
					
					descProdQtd= pp.getProduto().getDescricao();
					preco = "("+pp.getQuantidade()+"Un.)"+"R$"+(dcmFmt.format( pp.getProduto().getPreco()));
					mensagem+=descProdQtd+retornaPontos(descProdQtd, preco)+preco+"\n";
					totalPedido+=pp.getProduto().getPreco()*pp.getQuantidade();
				}
				totalGeral +=totalPedido;
				mensagem +=retornaEspacoBranco("","Total: " + dcmFmt.format(totalPedido) )+"Total: " + dcmFmt.format(totalPedido) +"\n";
				if(p.getVendaPrazo()== true && p.getPago() == false){
					mensagem +=p.getPessoa().getNome()+ retornaEspacoBranco(p.getPessoa().getNome(), p.getPessoa().getTelefone())+p.getPessoa().getTelefone()+"\n\n";
				}
				totalPedido= 0D;
				pedido ++;
			}
			mensagem +=retornaEspacoBranco("", "Total: "+"R$"+dcmFmt.format(totalGeral)) + "Total: "+"R$"+dcmFmt.format(totalGeral);
			mensagem += "\n\n\n\n\n\n\n\n";
		}else{
			FacesMessage msg = new FacesMessage("Vendas Inexistentes:", "Não existem Vendas realizadas na data informada!!");
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		
		try {
			System.out.println(new String(mensagem.getBytes("UTF-8"), "UTF-8"));
			ImpressaoTXTUtil impressao = new ImpressaoTXTUtil();
			impressao.escreveImpressao(mensagem);
		} catch (IOException | PrintException e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("ERRO:", "Não foi possível realizar a impressão !!");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}
		System.out.println(mensagem);
	}
	
	public void relatorioProdQtdMin(){
		
		ConversationUtil.iniciarConversacao(conversation);
		produtosQtdMin =  produtoService.pesquisarProdutosEstoqueMin();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		
		if(produtosQtdMin!= null||!produtosQtdMin.isEmpty()){
			String cabecalho ="Relatório de produtos com estoque na quantidade \n"
					+ "\tmínima realizado no dia "+ sdf.format(new Date()) + "\n\n";
			String mensagem = cabecalho;
			mensagem += "Produto"+ retornaEspacoBranco("Produto", "Qtd/Estoque")+"Qtd/Estoque\n";
			for (Produto prod : produtosQtdMin) {
				mensagem += prod.getDescricao()+ retornaPontos(prod.getDescricao(), prod.getQtdEstoque().toString())+ prod.getQtdEstoque().toString() +"\n";
			}
			mensagem +=mensagem += "\n\n\n\n\n\n\n\n";
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
		 pedidosFinalizados= new ArrayList<Pedido>();
		 produtosQtdMin = new ArrayList<Produto>();
		data = new Date();
	
	}
	
	 public void handleDateSelect(SelectEvent event) {  
	        FacesContext facesContext = FacesContext.getCurrentInstance();  
	        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");  
	        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));  
	    }  

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Pedido> getPedidosFinalizados() {
		return pedidosFinalizados;
	}

	public void setPedidosFinalizados(List<Pedido> pedidosFinalizados) {
		this.pedidosFinalizados = pedidosFinalizados;
	}

	public List<Produto> getProdutosQtdMin() {
		return produtosQtdMin;
	}

	public void setProdutosQtdMin(List<Produto> produtosQtdMin) {
		this.produtosQtdMin = produtosQtdMin;
	}
	
}
