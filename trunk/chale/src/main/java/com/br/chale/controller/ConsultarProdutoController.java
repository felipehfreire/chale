package com.br.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.br.chale.dao.Produto;
import com.br.chale.facade.Facade;
import com.br.chale.ifacade.IFacade;

@ManagedBean
@ViewScoped
public class ConsultarProdutoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//TODO passar a inicialização dos objetos para o metodo initialize
	private String nome = "";
	private Integer codigo= 0;
	private List<Produto> produtos = new  ArrayList<Produto>();
	
	//TODO verificar o problema do facade
	//IFacade facade = Facade.getInstance();
	
	//TODO verificar como inicializar os objetos
	public void initialize(){
		nome = "";
		codigo = new Integer(0);
		produtos = new ArrayList<Produto>();
		
	}
	
	public void pesquisar(){
		initialize();
		
		//TODO descomentar caso tiver 
		//produtos = facade.pesquisar(nome, codigo);
		
		for(int i= 0; i< 3; i++){
			Produto p = new Produto();
			p.setDescricao("Produto"+i);
			p.setIdProd(i);
			p.setPreco(Double.valueOf(i));
			p.setQtdEstoque(i);
			p.setQtdMinEstoque(i);
			p.setTipoServico(true);
			
			produtos.add(p);
		}
		
		 
		produtos.size();
	}
	
	public void novo(){
	//TODO verificar como alternar entre telas	no faces config navagiton rule
		FacesMessage msg = new FacesMessage("novo");  
	    FacesContext.getCurrentInstance().addMessage(null, msg);  
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
