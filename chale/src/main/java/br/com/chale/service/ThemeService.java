package br.com.chale.service;

import java.util.List;

import br.com.chale.entity.Theme;

public interface ThemeService {

	List<Theme> getThemes();
	
	void persistir(Theme theme);

	void atualizar(Theme theme);

	void excluir(Theme theme);

	Theme getTheme();

}
