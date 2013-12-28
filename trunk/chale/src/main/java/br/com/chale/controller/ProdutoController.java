package br.com.chale.controller;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.service.ProdutoService;

@Named
@ManagedBean
@ConversationScoped
public class ProdutoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ProdutoService produtoService;
	
//	@Inject
//	private Conversation conversation;
	
	public void pesquisar() {
//		if (conversation.isTransient())
//			conversation.begin();
		produtoService.pesquisar();
//		i++;
//		System.out.println(i);
//		conversation.end();
	}

}
