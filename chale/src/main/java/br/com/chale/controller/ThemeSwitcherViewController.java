package br.com.chale.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Theme;
import br.com.chale.service.ThemeService;
 

@Named
@ManagedBean
@ApplicationScoped
public class ThemeSwitcherViewController implements Serializable{
 
	private static final long serialVersionUID = 1L;

	private List<Theme> themes;
    
    private Theme themeSW;
    
    @Inject
    private ThemeService service;
 
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
   
    public void saveTheme() {           
		System.out.println("sdsadsa");
    }
 
	public List<Theme> getThemes() {
        return themes;
    } 
 
    public void setService(ThemeService service) {
        this.service = service;
    }

	public Theme getThemeSW() {
		return themeSW;
	}

	public void setThemeSW(Theme themeSW) {
		this.themeSW = themeSW;
	}
    
}