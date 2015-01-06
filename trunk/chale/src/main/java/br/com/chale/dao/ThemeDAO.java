package br.com.chale.dao;

import java.util.List;

import br.com.chale.entity.Theme;

public class ThemeDAO extends GenericDAO<Theme> {

	private static final long serialVersionUID = 2127025132325017019L;
	
	public Theme getTheme() {
		List<Theme> themes =executeQueryListResult(Theme.QUERY_GET_THEME);
		if(themes.size()>0){
			return themes.get(0);
		}else{
			return null;
		}
		
	}
	
	public void excluir(Theme theme) {
		manager.getTransaction().begin();
		theme = manager.find(Theme.class, theme.getId());
		manager.remove(theme);
		manager.getTransaction().commit();
		
	}
	
}
