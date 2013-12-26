package com.br.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.br.chale.Service.ProdutoService;
import com.br.chale.ServiceBean.ProdutoServiceBean;
import com.br.chale.entidades.Produto;
import com.br.chale.utils.Constantes;

@ManagedBean
@ViewScoped
public class ManterProdutoController implements Serializable{

	private static final long serialVersionUID = 2L;
	
	private String nome;
	private Integer codigo;
	private List<Produto> produtos;
	
	//TODO verificar o problema do facade
	//@Inject
	//ProdutoService produtoService = ProdutoServiceBean.getInstance();
	
	
	//metódo para a incialização
	@PostConstruct    			//essa anotação mostra qual o metod incializado quando cirado o bean
	public void initialize(){
		nome = "";
		codigo = new Integer(0);
		produtos = new ArrayList<Produto>();
		pesquisar();
		
	}
	
	public void pesquisar(){
		//initialize();
		
		//TODO descomentar caso tiver 
		//produtos = produtoService.pesquisar(nome, codigo);
		
		for(int i= 0; i< 3; i++){
			Produto p = new Produto();
			p.setDescricao("Produto"+(i + 1));
			p.setIdProd(i);
			p.setPreco(Double.valueOf(i+1));
			p.setQtdEstoque(i+1);
			p.setQtdMinEstoque(i+1);
			p.setTipoServico(true);
			
			produtos.add(p);
		}
		
		 
		produtos.size();
	}
	
	public String novoProduto(){
	//TODO verificar como alternar entre telas	no faces config navagiton rule
		//FacesMessage msg = new FacesMessage("novo");  
	    //FacesContext.getCurrentInstance().addMessage(null, msg);  
	    return Constantes.CADASTRO_PRODUTO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
