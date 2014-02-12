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
import javax.swing.text.StyledEditorKit.BoldAction;

import br.com.chale.entity.Venda;
import br.com.chale.service.VendaService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class VendaPrazoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private VendaService vendaService;

	@Inject
	private Conversation conversation;
	
	@Inject
	private RelatorioController relatorioController;

	private List<Venda> vendasFinalizadasAprazo;
	private Venda vendaSelecionada;
	private Date dataInicial;
	private Date dataFinal;
	
	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		pesquisar();
	}
	
	public void pesquisar(){
		ConversationUtil.iniciarConversacao(conversation);
		if(dataInicial != null && dataFinal != null ){
			if(validaDatas(dataInicial,dataFinal)){
				vendasFinalizadasAprazo = vendaService.pesquisarVendasFinalizadasPrazoPorPeriodo(dataInicial, dataFinal);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "A data final deve ser maior que a data inicial!"));
			}
			
		}else{
			vendasFinalizadasAprazo = vendaService.pesquisarVendasFinalizadasPrazo();
		}
	}
	
	public void pesquisarTodos() {
		ConversationUtil.iniciarConversacao(conversation);
		vendasFinalizadasAprazo = vendaService.pesquisarVendasFinalizadasPrazo();
	}
	
	public void pesquisarPorPerido() {
		ConversationUtil.iniciarConversacao(conversation);
		vendasFinalizadasAprazo = vendaService.pesquisarVendasFinalizadasPrazoPorPeriodo(dataInicial, dataFinal);
		vendasFinalizadasAprazo.size();
	}

	 public void receberVenda() {
		 vendaSelecionada.setPago(true);
		 vendaSelecionada.setDataPagamento(new Date());
		 vendaService.atualizar(vendaSelecionada);
		 relatorioController.impressaoPedidoFinalizado(vendaSelecionada);
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Venda alterada com sucesso!"));
		 
	 }
	 
	public String voltar() {
		return "/index.jsf?faces-redirect=true";
	}
	
	public String voltarDetalhar() {
		return "/consultarVendaAPrazo.jsf?faces-redirect=true";
	}
	
	

	public void limpar() {
		vendasFinalizadasAprazo= new ArrayList<Venda>();
		vendaSelecionada = new Venda();
		dataFinal = null;
		dataInicial = null;
	}
	
	public String detalhar() {
		return "detalhar";
	}
	
	public Boolean  validaDatas(Date dataIn,Date dataFin){
		Boolean  retorno = true;
		if(dataIn.compareTo(dataFin)>0){
			retorno = false;
		}
		
		return retorno;
		
		 
	}

	public List<Venda> getVendasFinalizadasAprazo() {
		return vendasFinalizadasAprazo;
	}

	public void setVendasFinalizadasAprazo(List<Venda> vendasFinalizadasAprazo) {
		this.vendasFinalizadasAprazo = vendasFinalizadasAprazo;
	}

	public VendaService getVendaService() {
		return vendaService;
	}

	public void setVendaService(VendaService vendaService) {
		this.vendaService = vendaService;
	}

	public Venda getVendaSelecionada() {
		return vendaSelecionada;
	}

	public void setVendaSelecionada(Venda vendaSelecionada) {
		this.vendaSelecionada = vendaSelecionada;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}
