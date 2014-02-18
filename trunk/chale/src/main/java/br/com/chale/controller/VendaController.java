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
	private Long idProd;
	private Venda venda;
	private List<Cliente> clientes;

	@PostConstruct
	public void iniciar() {
		ConversationUtil.terminarConversacao(conversation);
		limpar();
		PreencherMesas();
		PreencherProdutos();
		pesquisar();
	}
	
	public void reRenderProduto() {
		produtoSelecionado = produtoService.getById(idProd);
	}
	
	public void reRenderIdProduto() {
		idProd = produtoSelecionado.getId();
		
	}
	
	public void add() {
		if (getProdutoSelecionado().getQtdEstoque().equals(0L) || getProdutoSelecionado().getQtdEstoque() < vendaProduto.getQuantidade()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
			
		} else if (verificaExistenciaProdutoNoPedido()) {
			//quando clicar no add e o produto já estiver na lista, só atualizar os valores
			vendaProduto.setProduto(getProdutoSelecionado());
			adicionar(vendaProduto.getQuantidade());
			
		} else {
			//TODO o cara pode mudar de mesa
			getProdutoSelecionado().setQtdEstoque(getProdutoSelecionado().getQtdEstoque() - vendaProduto.getQuantidade());
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

	private void PreencherMesas() {
		mesas = vendaService.consultarTodasMesas();
		mesasNaoUsadas = vendaService.consultarMesasNaoUsadas();
	}
	
	private void PreencherProdutos() {
		produtosSelect = produtoService.pesquisarTodos();
	}
	
	public void pesquisar() {
		
		if(mesaSelecionada != null && mesaSelecionada.getNumeroMesa() != null){
			vendas = vendaService.pesquisarVendasNaoFinalizadasPorMesa(mesaSelecionada);
		}else{
			vendas = vendaService.pesquisarVendasNaoFinalizadas();
		}
		
		
	}

	public String novo() {
		Mesa mesa = venda.getMesa();
		mesa.setUsada(true);
		vendaService.atualizarMesa(mesa);
		return "/manterVenda.jsf?faces-redirect=true";
	}
	
	/**
	 * Adiciona mais um "pedido" do produto selecionado
	 */
	public void adicionar(Long quantidade) {
		if (!vendaProduto.getProduto().getQtdEstoque().equals(0L)) {
			vendaProduto.getProduto().setQtdEstoque(vendaProduto.getProduto().getQtdEstoque() - quantidade);
			produtoService.atualizar(vendaProduto.getProduto());
			
			for (VendaProduto vendProd : venda.getVendaProdutos()) {
				if (vendProd.getProduto().equals(vendaProduto.getProduto())) {
					vendProd.setQuantidade(vendProd.getQuantidade() + quantidade);
				}
			}
			vendaService.atualizar(venda);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro Atualizado com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
		}
		limparAdd();
	}
	
	
	public void subtrair() {
		if (!vendaProduto.getProduto().getQtdEstoque().equals(0L)) {
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Quantidade insuficiente em estoque!"));
		}
		limparAdd();
	}
	
	public void deletar() {
		vendaProduto.getProduto().setQtdEstoque(vendaProduto.getProduto().getQtdEstoque() + vendaProduto.getQuantidade());
		produtoService.atualizar(vendaProduto.getProduto());
		venda.getVendaProdutos().remove(vendaProduto);
		venda = vendaService.atualizar(venda);
		limparAdd();
	}

	 public String editar() {
		 return "/manterVenda.jsf?faces-redirect=true";
	 }
	 
	 public String preparaFinalizar() {
		 clientes = clienteService.pesquisarTodos();
		 return "finalizar";
	 }
	 
	 public String finalizar() {
		 if (venda.getVendaProdutos().size() == 0) {
			 FacesContext.getCurrentInstance().addMessage(null, new	FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					 "Não é possível finalizar o pedido sem ao menos selecionar um produto!"));
		 }

		 Mesa mesa = venda.getMesa();
		 mesa.setUsada(false);
		 vendaService.atualizarMesa(mesa);

		 venda.setFinalizada(true);
		 if (!venda.getVendaPrazo()) {
			 venda.setPago(true);
		 }
		 venda = vendaService.atualizar(venda);
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
		
		vendaProduto = new VendaProduto();
		vendaProduto.setVenda(venda);
		dataAtual =new Date();
		produtoSelecionado = new Produto();
		produtosInseridos = new ArrayList<Produto>();
	}
	
	public void atualizarSelect(Produto p){
		setProdutoSelecionado(p);
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
	
	
}
