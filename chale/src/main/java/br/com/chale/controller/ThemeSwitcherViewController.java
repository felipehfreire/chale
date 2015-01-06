package br.com.chale.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.chale.entity.Theme;
import br.com.chale.service.ThemeService;
 

@Named
@ManagedBean
@SessionScoped
public class ThemeSwitcherViewController implements Serializable{
 
	private static final long serialVersionUID = 6460720855720516107L;

	private List<Theme> themes;
    
    private Theme themeBD;
    
    private Theme themeSelecionado;
    
    private Long id;
    
    @Inject
    private ThemeService themeService;
 
    @PostConstruct
    public void init() {
    	limpar();
    	themeBD = themeService.getTheme();
        themes = themeService.getThemes();
    }
    
    public void limpar(){
    	themeSelecionado = new Theme();
    }
   
    public void saveTheme() {
    	//TODO fazer pegar o atributo da tela para salvar
    	//TODO verificar o entityConverter para  THEME
    	//TODO deletar o que ja estiver BD e salvar o NOVO Theme
    	//themeService.persistir(themeSelecionado);
    }
 
	public List<Theme> getThemes() {
        return themes;
    }

	public Theme getThemeBD() {
		return themeBD;
	}

	public void setThemeBD(Theme themeBD) {
		this.themeBD = themeBD;
	}

	public Theme getThemeSelecionado() {
		return themeSelecionado;
	}

	public void setThemeSelecionado(Theme themeSelecionado) {
		this.themeSelecionado = themeSelecionado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
}