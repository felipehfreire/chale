package br.com.chale.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
    private List<Theme> themes;
     
    @PostConstruct
    public void init() {
        themes = new ArrayList<Theme>();
        themes.add(new Theme(0L, "Afterdark", "afterdark"));
        themes.add(new Theme(1L, "Afternoon", "afternoon"));
        themes.add(new Theme(2L, "Afterwork", "afterwork"));
        themes.add(new Theme(3L, "Aristo", "aristo"));
        themes.add(new Theme(4L, "Black-Tie", "black-tie"));
        themes.add(new Theme(5L, "Blitzer", "blitzer"));
        themes.add(new Theme(6L, "Bluesky", "bluesky"));
        themes.add(new Theme(7L, "Bootstrap", "bootstrap"));
        themes.add(new Theme(8L, "Casablanca", "casablanca"));
        themes.add(new Theme(9L, "Cupertino", "cupertino"));
        themes.add(new Theme(10L, "Cruze", "cruze"));
        themes.add(new Theme(11L, "Dark-Hive", "dark-hive"));
        themes.add(new Theme(12L, "Delta", "delta"));
        themes.add(new Theme(13L, "Dot-Luv", "dot-luv"));
        themes.add(new Theme(14L, "Eggplant", "eggplant"));
        themes.add(new Theme(15L, "Excite-Bike", "excite-bike"));
        themes.add(new Theme(16L, "Flick", "flick"));
        themes.add(new Theme(17L, "Glass-X", "glass-x"));
        themes.add(new Theme(18L, "Home", "home"));
        themes.add(new Theme(19L, "Hot-Sneaks", "hot-sneaks"));
        themes.add(new Theme(20L, "Humanity", "humanity"));
        themes.add(new Theme(21L, "Le-Frog", "le-frog"));
        themes.add(new Theme(22L, "MetroUI", "metroui"));
        themes.add(new Theme(23L, "Midnight", "midnight"));
        themes.add(new Theme(24L, "Mint-Choc", "mint-choc"));
        themes.add(new Theme(25L, "Overcast", "overcast"));
        themes.add(new Theme(26L, "Pepper-Grinder", "pepper-grinder"));
        themes.add(new Theme(27L, "Redmond", "redmond"));
        themes.add(new Theme(28L, "Rocket", "rocket"));
        themes.add(new Theme(29L, "Sam", "sam"));
        themes.add(new Theme(30L, "Smoothness", "smoothness"));
        themes.add(new Theme(31L, "South-Street", "south-street"));
        themes.add(new Theme(32L, "Start", "start"));
        themes.add(new Theme(33L, "Sunny", "sunny"));
        themes.add(new Theme(34L, "Swanky-Purse", "swanky-purse"));
        themes.add(new Theme(35L, "Trontastic", "trontastic"));
        themes.add(new Theme(36L, "UI-Darkness", "ui-darkness"));
        themes.add(new Theme(37L, "UI-Lightness", "ui-lightness"));
        themes.add(new Theme(38L, "Vader", "vader"));
    }
     
    public List<Theme> getThemes() {
        return themes;
    }

	@Override
	public Theme getTheme() {
		return themeDAO.getTheme();
	}

	@Override
	public void persistir(Theme theme) {
		themeDAO.insert(theme);
		
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