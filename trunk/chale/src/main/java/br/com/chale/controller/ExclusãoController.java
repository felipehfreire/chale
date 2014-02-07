package br.com.chale.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@Named
@ManagedBean
@ApplicationScoped
public class ExclusãoController implements Serializable {
	private static final long serialVersionUID = 2847517553472907222L;
	
	private Object ObjectoAExcluir;
	
	@PostConstruct
	public void iniciar() {
		ObjectoAExcluir=new Object();
	}

	public Object getObjectoAExcluir() {
		return ObjectoAExcluir;
	}

	public void setObjectoAExcluir(Object objectoAExcluir) {
		ObjectoAExcluir = objectoAExcluir;
	}
	
}
