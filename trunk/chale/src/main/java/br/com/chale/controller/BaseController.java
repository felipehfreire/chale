package br.com.chale.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import br.com.chale.entity.Theme;

public abstract class BaseController<T> implements Serializable {

	private static final long serialVersionUID = -3429381259806038784L;
	
	protected static Theme theme;
	
	@Inject
	private Conversation conversation;

	public static Theme getTheme() {
		return theme;
	}

	public static void setTheme(Theme theme) {
		BaseController.theme = theme;
	}
	
	
	

}
