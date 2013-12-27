package com.br.chale.ServiceBean;

import java.util.List;

import javax.inject.Inject;

import com.br.chale.Service.ProdutoService;
import com.br.chale.dao.ProdutoDAO;
import com.br.chale.entidades.Produto;


public class ProdutoServiceBean implements ProdutoService{

	//private static ProdutoServiceBean instance;
	
	
	@Inject
	ProdutoDAO produtoDAO =new ProdutoDAO();
	
//	 public static ProdutoServiceBean getInstance() {
//	
//	        if (instance == null) 
//	        		instance = new ProdutoServiceBean();
//	
//	           
//	       return instance;
//	    }

	@Override
	public List<Produto> pesquisar(String nome, Integer codigo) {
		
		return produtoDAO.pesquisar(nome, codigo);
	}
	        

}
