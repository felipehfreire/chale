package br.com.chale.dao;

import br.com.chale.entity.Produto;

public class ProdutoDAO extends GenericDAO<Produto> {

	private static final long serialVersionUID = 2127025132325017019L;

	public void pesquisar() {
		//TODO na genericDAO já tem os principais métodos para consulta, caso precise utilizar algo específico,
		//utilize o EntityManager, somente chamando o seu atributo, que é o "manager".
		//Ex.: manager.createNativeQuery....
		System.out.println("Opa");
	}

}
