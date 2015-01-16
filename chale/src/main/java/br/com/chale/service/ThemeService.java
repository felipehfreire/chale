package br.com.chale.service;

import br.com.chale.entity.Theme;

public interface ThemeService {

	String[] getThemes();
	
	void persistir(String theme);

	void atualizar(Theme theme);

	void excluir(Theme theme);

	Theme getTheme();

}
