package com.br.chale.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.br.chale.dao.Produto;

@ManagedBean
@ViewScoped
public class ConsultarProdutoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String codigo;
	private List<Produto> proutos;
	
	public void initialize(){
		nome = "";
		codigo = "";
		proutos = new ArrayList<Produto>();
		
	}
	
	public void pesquisar(){
		initialize();
		
		for(int i= 0; i< 3; i++){
			Produto p = new Produto();
			p.setDescricao("Produto"+i);
			p.setIdProd(i);
			p.setPreco(Double.valueOf(i));
			p.setQtdEstoque(i);
			p.setQtdMinEstoque(i);
			p.setTipoServico(true);
			
			proutos.add(p);
		}
		proutos.size();
	}
	
	public void novo(){
	//TODO verificar como alternar entre telas	
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Produto> getProutos() {
		return proutos;
	}

	public void setProutos(List<Produto> proutos) {
		this.proutos = proutos;
	}
	
}
