package br.com.chale.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.com.chale.dao.ThemeDAO;
import br.com.chale.entity.Theme;
 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
public class ThemeServiceBean implements ThemeService, Serializable{

	private static final long serialVersionUID = 6493841011070289463L;

	@Inject
	private ThemeDAO themeDAO;
	
    public String[] getThemes() {
    	return new String[]
    		{ "afterdark", "afternoon", "afterwork", "aristo",
    		"black-tie", "blitzer", "bluesky", "casablanca",
    		"cruze", "cupertino", "dark-hive", "dot-luv",
    		"eggplant", "excite-bike", "flick", "glass-x",
    		"home", "hot-sneaks", "humanity", "le-frog",
    		"midnight", "mint-choc", "overcast", "pepper-grinder",
    		"redmond", "rocket", "sam", "smoothness",
    		"south-street", "start", "sunny", "swanky-purse",
    		"trontastic", "twitter bootstrap", "ui-darkness",
    		"ui-lightness", "vader" };
    }

	@Override
	public Theme getTheme() {
		return themeDAO.getTheme();
	}

	@Override
	public void persistir(String theme) {
		Theme th = getTheme();
		if (th != null) {
			th.setDisplayName(theme);
			th.setName(theme);
			atualizar(th);
		} else {
			th = new Theme();
			th.setDisplayName(theme);
			th.setName(theme);
			themeDAO.insert(th);
		}
	}

	@Override
	public void atualizar(Theme theme) {
		themeDAO.update(theme);
	}

	@Override
	public void excluir(Theme theme) {
		themeDAO.excluir(theme);
		
	} 
    
    
}