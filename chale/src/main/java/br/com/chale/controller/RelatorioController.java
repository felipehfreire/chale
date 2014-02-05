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
public class RelatorioController implements Serializable {
	
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private VendaService pedidoService;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private VendaService vendaService;
	
	@Inject
	private Conversation conversation;
	
	private List<Venda> pedidosFinalizados;
	private List<Venda> vendasFinalizadasAprazo;
	private List<Produto> produtosQtdMin;
	private Date data;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
	}
	
	public void impressaoPedidoFinalizado(Venda p){
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		String cabecalho ="            Pousada Vale dos ventos \n\n"; 
		String mesa="Mesa:"+ p.getMesa()+retornaEspacoBranco("Mesa:"+ p.getMesa(), "Hora:"+new SimpleDateFormat("HH:mm").format(new Date()))+"Hora:"+new SimpleDateFormat("HH:mm").format(new Date())+"\n";
		
		String cabecalhoVenda ="Itens"+retornaEspacoBranco("Itens", "Qtd/preço")+"Qtd/preço\n";
		String mensagem = cabecalho+ mesa+cabecalhoVenda;
		Double totalPedido = 0D;
		String descProdQtd="";
		String preco="";
		for (VendaProduto vendProd : p.getVendaProdutos()) {
			
			descProdQtd= vendProd.getProduto().getDescricao();
			preco = "("+vendProd.getQuantidade()+"Un.)"+"R$"+(dcmFmt.format( vendProd.getProduto().getPreco()));
			mensagem+=descProdQtd+retornaPontos(descProdQtd, preco)+preco+"\n";
			totalPedido+=vendProd.getProduto().getPreco()*vendProd.getQuantidade();
		}
		mensagem +=retornaEspacoBranco("","Total: " + dcmFmt.format(totalPedido) )+"Total: " + dcmFmt.format(totalPedido) +"\n\n";
		System.out.println(mensagem);
		try {
			ImpressaoTXTUtil impressao = new ImpressaoTXTUtil();
			impressao.escreveImpressao(mensagem);
		} catch (IOException | PrintException e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("ERRO:", "Não foi posível Não foi possível realizar a impressão !!");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void relatorioVendaDiario() throws ParseException {
		ConversationUtil.iniciarConversacao(conversation);
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		data= sdf.parse(sdf.format(data));
		pedidosFinalizados = pedidoService.pesquisarVendasPorData(data);
		
		Double totalPedido = 0D;
		Double totalGeral = 0D;
		String descProdQtd="";
		String preco="";
		String mensagem= "";
		
		if(pedidosFinalizados!= null && !pedidosFinalizados.isEmpty()){
			String cabecalho ="Relatório de venda realizadas no dia "+ sdf.format(data) + "\n\n";
			mensagem = cabecalho;
			for (Venda p : pedidosFinalizados) {
				mensagem += "Pedido: Mesa" +p.getMesa() +retornaEspacoBranco("Pedido: Mesa" +p.getMesa() ," Hora:"+new SimpleDateFormat("HH:mm").format(p.getDataVenda())) +" Hora:"+new SimpleDateFormat("HH:mm").format(p.getDataVenda())+ "\n" ;
				for (VendaProduto pp : p.getVendaProdutos()) {
					
					descProdQtd= pp.getProduto().getDescricao();
					preco = "("+pp.getQuantidade()+"Un.)"+"R$"+(dcmFmt.format( pp.getProduto().getPreco()));
					mensagem+=descProdQtd+retornaPontos(descProdQtd, preco)+preco+"\n";
					totalPedido+=pp.getProduto().getPreco()*pp.getQuantidade();
				}
				totalGeral +=totalPedido;
				if(p.getVendaPrazo()== true ){
					mensagem +=p.getCliente().getNome()+ retornaEspacoBranco(p.getCliente().getNome(), p.getCliente().getTelefone())+p.getCliente().getTelefone()+"\n";
				}
				mensagem +=retornaEspacoBranco("","Total: " + dcmFmt.format(totalPedido) )+"Total: " + dcmFmt.format(totalPedido) +"\n\n";
				
				
				totalPedido= 0D;
			}
			mensagem +=retornaEspacoBranco("", "Total: "+"R$"+dcmFmt.format(totalGeral)) + "Total: "+"R$"+dcmFmt.format(totalGeral);
			mensagem += "\n\n\n\n\n\n\n\n";
			
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
		}else{
			FacesMessage msg = new FacesMessage("Vendas Inexistentes:", "Não existem Vendas realizadas na data informada!!");
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		System.out.println(mensagem);
	}
	
	public void relatorioProdQtdMin(){
		
		ConversationUtil.iniciarConversacao(conversation);
		produtosQtdMin =  produtoService.pesquisarProdutosEstoqueMin();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if(produtosQtdMin!= null&&!produtosQtdMin.isEmpty()){
			String cabecalho ="Relatório de produtos com estoque na quantidade \n"
					+ "\tmínima realizado no dia "+ sdf.format(new Date()) + "\n\n";
			String mensagem = cabecalho;
			mensagem += "Produto"+ retornaEspacoBranco("Produto", "Qtd.Estoque")+"Qtd.Estoque\n";
			for (Produto prod : produtosQtdMin) {
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
	
	public void relatorioVendaPrazo(){
		
		ConversationUtil.iniciarConversacao(conversation);
		vendasFinalizadasAprazo =  vendaService.pesquisarVendasPrazoMes(data);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy HH:mm:ss");
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		String mensagem ="";
		Double totalGeral = 0D;
		
		if(vendasFinalizadasAprazo!= null &&!vendasFinalizadasAprazo.isEmpty()){
			String cabecalho ="Relatório de vendas à prazo no mês de "+ sdf.format(data)+ "\n\n";
			mensagem= cabecalho;
			mensagem += "Cliente" + retornaEspacoBranco("Cliente", "Total")+"Total\n";
			for (Venda venda : vendasFinalizadasAprazo) {
				mensagem += venda.getCliente().getNome()+retornaPontos(venda.getCliente().getNome(), "Total: "+venda.getPrecoTotalFormatado())+"Total: "+venda.getPrecoTotalFormatado()+"\n";
				totalGeral += Double.valueOf(venda.getPrecoTotal());
			}
			
			mensagem+= "\n"+retornaEspacoBranco("","Total: "+"R$ "+totalGeral) + "Total: "+"R$ "+dcmFmt.format(totalGeral);
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
			FacesMessage msg = new FacesMessage("AVISO: ", "Não existem Vendas a prazo no mês informado!!");
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
		 pedidosFinalizados= new ArrayList<Venda>();
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

	public List<Venda> getPedidosFinalizados() {
		return pedidosFinalizados;
	}

	public void setPedidosFinalizados(List<Venda> pedidosFinalizados) {
		this.pedidosFinalizados = pedidosFinalizados;
	}

	public List<Produto> getProdutosQtdMin() {
		return produtosQtdMin;
	}

	public void setProdutosQtdMin(List<Produto> produtosQtdMin) {
		this.produtosQtdMin = produtosQtdMin;
	}

	public List<Venda> getVendasFinalizadasAprazo() {
		return vendasFinalizadasAprazo;
	}

	public void setVendasFinalizadasAprazo(List<Venda> vendasFinalizadasAprazo) {
		this.vendasFinalizadasAprazo = vendasFinalizadasAprazo;
	}
	
}
