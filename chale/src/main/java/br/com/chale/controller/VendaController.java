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

import br.com.chale.entity.Cliente;
import br.com.chale.entity.Mesa;
import br.com.chale.entity.Produto;
import br.com.chale.entity.Venda;
import br.com.chale.entity.VendaProduto;
import br.com.chale.service.ClienteService;
import br.com.chale.service.ProdutoService;
import br.com.chale.service.VendaService;
import br.com.chale.util.ConversationUtil;

@Named
@ManagedBean
@ConversationScoped
public class VendaController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private VendaService vendaService;
	
	@Inject
	private ProdutoService produtoService;

	@Inject
	private Conversation conversation;
	
	@Inject
	private ClienteService clienteService;
	
	private List<Mesa> mesas;
	private List<Mesa> mesasNaoUsadas;
	private List<Venda> vendas;
	private List<Produto> produtosInseridos;
	private List<Produto> produtosSelect;
	private Date dataAtual;
	private boolean aVista;
	
	private VendaProduto vendaProduto;
	private Produto produtoSelecionado;
	private Mesa mesaSelecionada;
	private Mesa mesaSelecionadaPesquisa;
	private Long idProd;
	private Venda venda;
	private List<Cliente> clientes;
	private Cliente clienteSelecionado;
	private Cliente  clienteSelecionadoPesquisa;
	private List<Venda> vendasSelecionadas;

	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		preencherMesas();
		preencherProdutos();
		preencherClientes();
		pesquisar();
	}
	
	public void reRenderProduto() {
		produtoSelecionado = produtoService.getById(idProd);
	}
	
	public void reRenderIdProduto() {
		idProd = produtoSelecionado.getId();
		
	}
	
	public void add() {
		
		if (!getProdutoSelecionado().getTipoServico()  && !getProdutoSelecionado().getTipocomida() && verificaSladoDivisaoEstoque()&& (getProdutoSelecionado().getQtdEstoque().equals(0L) || getProdutoSelecionado().getQtdEstoque() < vendaProduto.getQuantidade())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
			
		} else if (verificaExistenciaProdutoNoPedido()) {
			//quando clicar no add e o produto já estiver na lista, só atualizar os valores
			vendaProduto.setProduto(getProdutoSelecionado());
			adicionar(vendaProduto.getQuantidade());
			
		} else {
			
			if (!getProdutoSelecionado().getTipoServico() && !getProdutoSelecionado().getTipocomida()) {
			
				
				if(getProdutoSelecionado().getProdutoVinculado() != null){
					if(getProdutoSelecionado().getDividirEstoque()){//se dividir estoque for true retira a quantidade add * 2 do item vinculado
						
						//atualiza o estoque do produto vezes duas vezes a quantidade por ser divisão de estoque pois compartila o estoque de outro item geralmente para porções que incluam 1/2 e 1 porção
						getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade());
						getProdutoSelecionado().getProdutoVinculado().setQtdEstoque(getProdutoSelecionado().getProdutoVinculado().getQtdEstoque() - (vendaProduto.getQuantidade()*2));
						produtoService.atualizar(getProdutoSelecionado().getProdutoVinculado());
						
					}else if(getProdutoSelecionado().getProdutoVinculado() != null){
						//atualiza a qauntidade do produto base do estoque
						Long qtdEstqExistente = getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade();
						if(qtdEstqExistente % 2 ==0){
							qtdEstqExistente=((qtdEstqExistente/2));
							
						}else{
							qtdEstqExistente=(((qtdEstqExistente-1)/2));
							
						}
						getProdutoSelecionado().getProdutoVinculado().setQtdEstoque(qtdEstqExistente);
						produtoService.atualizar(getProdutoSelecionado().getProdutoVinculado());
						getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade());
						
					}else{
						//atualiza o estoque do produto normalmente
						getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade());
					}
				}else{
					getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade());
				}
			}
			
			produtoService.atualizar(getProdutoSelecionado());
			vendaProduto.setProduto(getProdutoSelecionado());
			vendaProduto.setVenda(venda);
			venda.getVendaProdutos().add(vendaProduto);
			if (venda.getId() == null) {
				vendaService.persistir(venda);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Inserido com sucesso!"));
			} else {
				venda = vendaService.atualizar(venda);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
			}
			
			limparAdd();
		}
    }

	private boolean verificaSladoDivisaoEstoque() {
		
		if(getProdutoSelecionado().getQtdEstoque() < 2L || getProdutoSelecionado().getQtdEstoque() <(vendaProduto.getQuantidade()*2) ){
			return false;
		}else{
			return true;
		}
	}

	private boolean verificaExistenciaProdutoNoPedido() {
		boolean retorno = false;
		for (VendaProduto vendProd : venda.getVendaProdutos()) {
			if (vendProd.getProduto().equals(getProdutoSelecionado())) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}
	
	private void limparAdd() {
		vendaProduto = new VendaProduto();
		produtoSelecionado = new Produto();
		idProd = null;
	}

	private void preencherMesas() {
		mesas = vendaService.consultarTodasMesas();
		mesasNaoUsadas = vendaService.consultarMesasNaoUsadas();
	}
	
	private void preencherClientes() {
		clientes= clienteService.pesquisarTodos(); 
	}
	
	private void preencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public void pesquisar() {
		ConversationUtil.iniciarConversacao(conversation);
		if((mesaSelecionada!= null && mesaSelecionada.getNumeroMesa() != null) ){
			vendas = vendaService.pesquisarVendasNaoFinalizadasPorMesa(mesaSelecionada);
		}else if ((clienteSelecionado!= null && clienteSelecionado.getId() != null) ){
			vendas = vendaService.pesquisarVendasNaoFinalizadasPorCliente(clienteSelecionado);
		}else{
			vendas = vendaService.pesquisarVendasNaoFinalizadas();
		}
		
	}

	public String novo() {
		Mesa mesa = venda.getMesa();
		Cliente cliente = venda.getCliente();
		if(mesa!= null){
			mesa.setUsada(true);
			vendaService.atualizarMesa(mesa);
		}else if(cliente != null){
			vendaService.atualizarCliente(cliente);
		}
		return "/manterVenda.jsf?faces-redirect=true";
	}
	
	public String alterarMesaCliente() {
		Mesa mesaAntiga = venda.getMesa();
		Cliente clienteAntigo = venda.getCliente();

		if(mesaAntiga != null && mesaSelecionada !=null){//esta mudando de mesa
			mesaAntiga.setUsada(false);
			mesaSelecionada.setUsada(true);
			venda.setMesa(mesaSelecionada);
			vendaService.atualizarMesa(mesaSelecionada);
			vendaService.atualizar(venda);
			vendaService.atualizarMesa(mesaAntiga);
			
		}else if(mesaAntiga != null && clienteSelecionado!= null){//esta passando a venda para o cliente 
			mesaAntiga.setUsada(false);
			if(venda.getMesa() != null){//exita mesa
				venda.setMesa(null);
			}
			venda.setCliente(clienteSelecionado);
			vendaService.atualizar(venda);
			mesaAntiga.setUsada(false);
			vendaService.atualizarMesa(mesaAntiga);
			
		}else if(clienteAntigo != null && clienteSelecionado != null){//esta a venda para outro cliente
			venda.setCliente(clienteSelecionado);
			vendaService.atualizar(venda);
		}else if(clienteAntigo != null && mesaSelecionada !=null) {//esta mudando de um cliente para uma mesa
			venda.setCliente(null);
			mesaSelecionada.setUsada(true);
			venda.setMesa(mesaSelecionada);
			vendaService.atualizar(venda);
			vendaService.atualizarMesa(mesaSelecionada);
		}
		
		mesasNaoUsadas = vendaService.consultarMesasNaoUsadas();
		
		return "/manterVenda.jsf?faces-redirect=true";
	}
	
	/**
	 * Adiciona mais um "pedido" do produto selecionado
	 */
	public void adicionar(Long quantidade) {
		boolean valido = true;
		if( !vendaProduto.getProduto().getTipoServico() && !vendaProduto.getProduto().getTipocomida()){

			if(vendaProduto.getProduto().getDividirEstoque()){
				if(!vendaProduto.getProduto().getQtdEstoque().equals(0L) ){
					vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(vendaProduto.getProduto().getProdutoVinculado().getQtdEstoque() - (quantidade*2));
					produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
					
				}else{
					valido = false;
				}
			}else if(vendaProduto.getProduto().getProdutoVinculado() != null){ //atualiza o quantidade do produto  base do estoque
				if(!vendaProduto.getProduto().getQtdEstoque().equals(0L) ){
					Long qtdEstqExistente = vendaProduto.getProduto().getQtdEstoque() - quantidade;
					if(qtdEstqExistente % 2 ==0){
						qtdEstqExistente=((qtdEstqExistente/2));
						
					}else{
						qtdEstqExistente=(((qtdEstqExistente-1)/2));
						
					}
					vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(qtdEstqExistente);
					produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
					
				}else{
					valido = false;
				}
				
			}else{
				if(vendaProduto.getProduto().getQtdEstoque().equals(0L)){
					valido = false;
				}
			}
		}
		
		if(valido ){
			if(!vendaProduto.getProduto().getTipoServico() && !vendaProduto.getProduto().getTipocomida()){
				vendaProduto.getProduto().setQtdEstoque(vendaProduto.getProduto().getQtdEstoque() - quantidade);
				produtoService.atualizar(vendaProduto.getProduto());
			}
			
			for (VendaProduto vendProd : venda.getVendaProdutos()) {
				if (vendProd.getProduto().equals(vendaProduto.getProduto())) {
					vendProd.setQuantidade(vendProd.getQuantidade() + quantidade );
				}
			}
			vendaService.atualizar(venda);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
			
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
		}
		limparAdd();
	}
	
	public void subtrair() {
		if ( !vendaProduto.getProduto().getTipoServico() &&  !vendaProduto.getProduto().getTipocomida() ) {
			
			if(vendaProduto.getProduto().getDividirEstoque()){
				
				//seta mais dois no saldo do produto base do estoque
				vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(vendaProduto.getProduto().getProdutoVinculado().getQtdEstoque() +2);
				produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
				
			}else if(vendaProduto.getProduto().getProdutoVinculado() != null){
				//se ele for o base retira um
				Long qtdEstqExistente = vendaProduto.getProduto().getQtdEstoque() +1;
				if(qtdEstqExistente % 2 ==0){
					qtdEstqExistente=((qtdEstqExistente/2));
					
				}else{
					qtdEstqExistente=(((qtdEstqExistente-1)/2));
					
				}
				vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(qtdEstqExistente);
				produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
				
			}
			
			vendaProduto.getProduto().setQtdEstoque(vendaProduto.getProduto().getQtdEstoque() + 1);
			produtoService.atualizar(vendaProduto.getProduto());
			
			for (VendaProduto vendProd : venda.getVendaProdutos()) {
				if (vendProd.equals(vendaProduto)) {
					vendProd.setQuantidade(vendProd.getQuantidade() - 1);
				}
			}
			venda = vendaService.atualizar(venda);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
		} else {

			for (VendaProduto vendProd : venda.getVendaProdutos()) {
				if (vendProd.equals(vendaProduto)) {
					vendProd.setQuantidade(vendProd.getQuantidade() - 1);
				}
			}
			venda = vendaService.atualizar(venda);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
		}
		limparAdd();
	}
	
	public void deletar() {
		if (!vendaProduto.getProduto().getTipoServico() && !vendaProduto.getProduto().getTipocomida()) {
			
			if(vendaProduto.getProduto().getDividirEstoque()){
				
				//seta a quantidade x2 no saldo do produto base do estoque
				vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(vendaProduto.getProduto().getProdutoVinculado().getQtdEstoque() +(vendaProduto.getQuantidade()*2));
				produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
				
			}else if(vendaProduto.getProduto().getProdutoVinculado() != null){
				//se ele for o base retira um
				Long qtdEstqExistente = vendaProduto.getProduto().getQtdEstoque() +vendaProduto.getQuantidade();
				if(qtdEstqExistente % 2 ==0){
					qtdEstqExistente=((qtdEstqExistente/2));
					
				}else{
					qtdEstqExistente=(((qtdEstqExistente-1)/2));
					
				}
				vendaProduto.getProduto().getProdutoVinculado().setQtdEstoque(qtdEstqExistente);
				produtoService.atualizar(vendaProduto.getProduto().getProdutoVinculado());
			}
			
			vendaProduto.getProduto().setQtdEstoque(vendaProduto.getProduto().getQtdEstoque() + vendaProduto.getQuantidade());
			produtoService.atualizar(vendaProduto.getProduto());
		}
		
		venda.getVendaProdutos().remove(vendaProduto);
		venda = vendaService.atualizar(venda);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Deletado com sucesso!"));
		limparAdd();
	}

	 public String editar() {
		 return "/manterVenda.jsf?faces-redirect=true";
	 }
	 
	 public String preparaFinalizar() {
		 clientes = clienteService.pesquisarTodos();
		 return "finalizar";
	 }
	 
	 public void imprimirVEndaSemfinalizar(){
		 RelatorioController rc = new RelatorioController();
		 rc.impressaoPedidoFinalizado(venda);
	 }
	 
	 public String finalizar() {
		 if (venda.getVendaProdutos().size() == 0) {
			 FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					 "Não é possível finalizar o pedido sem ao menos selecionar um produto!"));
		 }

		 if(venda.getMesa() != null){
			 Mesa mesa = venda.getMesa();
			 mesa.setUsada(false);
			 vendaService.atualizarMesa(mesa);
		 }

		 venda.setFinalizada(true);
		 if (!venda.getVendaPrazo()) {
			 venda.setPago(true);
		 }
		 venda = vendaService.atualizar(venda);
		 RelatorioController rc = new RelatorioController();
		 rc.impressaoPedidoFinalizado(venda);
		 FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_INFO, "", "Pedido finalizado com sucesso!"));
		 ConversationUtil.terminarConversacao(conversation);
		 return "/consultarVenda.jsf?faces-redirect=true";
	 }


	public String voltar() {
		return "/index.jsf?faces-redirect=true";
	}

	public void limpar() {
		ConversationUtil.iniciarConversacao(conversation);
		venda = new Venda();
		venda.setVendaProdutos(new ArrayList<VendaProduto>());
		mesaSelecionada = new Mesa();
		vendas = new ArrayList<Venda>();
		vendasSelecionadas = new ArrayList<Venda>();
		vendaProduto = new VendaProduto();
		vendaProduto.setVenda(venda);
		dataAtual =new Date();
		//produtoSelecionado = new Produto();
		produtosInseridos = new ArrayList<Produto>();
		clienteSelecionadoPesquisa= new Cliente();
		mesaSelecionadaPesquisa = new Mesa();
	}
	
	public void atualizarSelect(Produto p){
		setProdutoSelecionado(p);
	}
	
	private Mesa getMenorNumeroMesa() {
		Mesa menor = null;
		for (Venda v : vendasSelecionadas) {
			if (v.getMesa() == null) {
				continue;
			} else if (menor == null) {
				menor = v.getMesa();
			} else if (menor.getNumeroMesa() > v.getMesa().getNumeroMesa()) {
				menor = v.getMesa();
			}
		}
		return menor;
	}
	
	public String juntarVendas(){
		if (vendasSelecionadas.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_ERROR,
					"", "Nenhuma venda foi selecionada"));
			return "/consultarVenda.jsf?faces-redirect=true";
		}
		Venda v = new Venda();
		v.setVendaProdutos(new ArrayList<VendaProduto>());
		for (Venda old : vendasSelecionadas) {
			if (v.getCliente() == null) {
				v.setCliente(old.getCliente());
			}
			if (v.getDataVenda() == null) {
				v.setDataVenda(old.getDataVenda());
			}
			
			//TODO questão do produto igual
			for (VendaProduto vpOld : old.getVendaProdutos()) {
				VendaProduto vendProd = new VendaProduto();
				vendProd.setProduto(vpOld.getProduto());
				vendProd.setQuantidade(vpOld.getQuantidade());
				vendProd.setVenda(v);
				v.getVendaProdutos().add(vendProd);
			}
		}
		
		v.setMesa(getMenorNumeroMesa());
		
		
		for (Venda remover : vendasSelecionadas) {
			vendaService.excluir(remover);
		}
		
		v.getMesa().setUsada(true);
		vendaService.atualizarMesa(v.getMesa());
		vendaService.persistir(v);
		pesquisar();
		FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_INFO, "", "As vendas foram unidas com sucesso! "+ "O novo número da mesa é: " + v.getMesa().getNumeroMesa()));
		return "/consultarVenda.jsf?faces-redirect=true";
	}
	
	public List<Produto> popularAutoCompleteProduto(String nomeCod){
		return produtoService.popularAutoCompleteProduto(nomeCod);
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

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public VendaProduto getVendaProduto() {
		return vendaProduto;
	}

	public void setVendaProduto(VendaProduto vendaProduto) {
		this.vendaProduto = vendaProduto;
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

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Mesa> getMesasNaoUsadas() {
		return mesasNaoUsadas;
	}

	public void setMesasNaoUsadas(List<Mesa> mesasNaoUsadas) {
		this.mesasNaoUsadas = mesasNaoUsadas;
	}


	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public Cliente getClienteSelecionadoNovo() {
		return clienteSelecionadoPesquisa;
	}

	public void setClienteSelecionadoNovo(Cliente clienteSelecionadoNovo) {
		this.clienteSelecionadoPesquisa = clienteSelecionadoNovo;
	}

	public Mesa getMesaSelecionadaNovo() {
		return mesaSelecionadaPesquisa;
	}

	public void setMesaSelecionadaNovo(Mesa mesaSelecionadaNovo) {
		this.mesaSelecionadaPesquisa = mesaSelecionadaNovo;
	}

	public List<Venda> getVendasSelecionadas() {
		return vendasSelecionadas;
	}

	public void setVendasSelecionadas(List<Venda> vendasSelecionadas) {
		this.vendasSelecionadas = vendasSelecionadas;
	}
	
}
