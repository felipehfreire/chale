package br.com.chale.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.BaseEntity;
import br.com.chale.entity.Cliente;
import br.com.chale.entity.Produto;

@Named
@ManagedBean
@ApplicationScoped
public class ExclusaoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	@Inject
	private ClienteController clienteController;
	
	@Inject 
	private ProdutoController produtoController;
	
	private BaseEntity entity;
	
	@PostConstruct
	public void iniciar() {
		entity = null;
	}
	
	public void excluir() {
		if (entity instanceof Cliente) {
			clienteController.excluir((Cliente)entity);
		} else if (entity instanceof Produto) {
			produtoController.excluir((Produto) entity);
		}
	}

	public BaseEntity getEntity() {
		return entity;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}
	
	

}
