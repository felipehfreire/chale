package com.br.chale.facade;

import java.util.List;

import com.br.chale.dao.ProdutoDAO;
import com.br.chale.entidades.Produto;
import com.br.chale.ifacade.IFacade;


public class Facade implements IFacade{

	private static Facade instance;
	
	ProdutoDAO produtoDAO = new ProdutoDAO(); 
	
	 public static Facade getInstance() {
	
	        if (instance == null) 
	        		instance = new Facade();
	
	           
	       return instance;
	    }

	@Override
	public List<Produto> pesquisar(String nome, Integer codigo) {
		
		return produtoDAO.pesquisar(nome, codigo);
	}
	        

}
