package br.com.chale.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Theme;
import br.com.chale.service.ThemeService;
 

@Named
@ManagedBean
@ViewScoped
public class ThemeSwitcherViewController implements Serializable{
	
	
 
	private static final long serialVersionUID = 6460720855720516107L;

    private Theme themeBD;
    
    private String themeSelecionado;
    private String theme;//Só usa no web.xml
    
    private Long id;
    
    @Inject
    private ThemeService themeService;
 
    @PostConstruct
    public void init() {
    	themeSelecionado = "cruze";
    	theme = "cruze";
    	Theme th = themeService.getTheme();
    	if (th == null) {
    		themeSelecionado = "cruze";
        	theme = "cruze";
    	} else {
    		themeSelecionado = th.getName();
    		theme = th.getName();
    	}
        //
    }
    
    public void saveTheme() {
    	System.out.println(themeSelecionado);
    	//TODO fazer pegar o atributo da tela para salvar
    	//TODO deletar o que ja estiver BD e salvar o NOVO Theme
    	themeService.persistir(themeSelecionado);
    }
 
	public String[] getThemes() {
		return themeService.getThemes();
    }

	public Theme getThemeBD() {
		return themeBD;
	}

	public void setThemeBD(Theme themeBD) {
		this.themeBD = themeBD;
	}

	public String getThemeSelecionado() {
		return themeSelecionado;
	}

	public void setThemeSelecionado(String themeSelecionado) {
		this.themeSelecionado = themeSelecionado;
		this.theme = themeSelecionado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
}