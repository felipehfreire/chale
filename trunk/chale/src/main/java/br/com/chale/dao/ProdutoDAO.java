package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Produto;

//TODO na genericDAO já tem os principais métodos para consulta, caso precise utilizar algo específico,
		//utilize o EntityManager, somente chamando o seu atributo, que é o "manager".
		//Ex.: manager.createNativeQuery....
public class ProdutoDAO extends GenericDAO<Produto> {

	private static final long serialVersionUID = 2127025132325017019L;

	public List<Produto> pesquisar(String termo) {
		return executeQueryListResult(Produto.QUERY_CONSULTAR_POR_NOME, termo);
		
	}

}
