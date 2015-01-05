package br.com.chale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="theme")
@NamedQueries({

	@NamedQuery(name=Theme.CONSULTAR_TODOS, query="Select t from Theme t")
	
	
})
public class Theme {
	
	public static final String CONSULTAR_TODOS = "theme.consultarTodosThemes";
	

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="cod_theme", nullable=false)
	 	private int id;
     
		@Column(name="txt_desplay_name", length= 100, nullable=false)
	    private String displayName;
	    
		@Column(name="txt_name", length= 100, nullable=false)
	    private String name;
	     
	    public Theme() {}
	 
	    public Theme(int id, String displayName, String name) {
	        this.id = id;
	        this.displayName = displayName;
	        this.name = name;
	    }
	 
	    public int getId() {
	        return id;
	    }
	 
	    public void setId(int id) {
	        this.id = id;
	    }
	 
	    public String getDisplayName() {
	        return displayName;
	    }
	 
	    public void setDisplayName(String displayName) {
	        this.displayName = displayName;
	    }
	 
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	     
	    @Override
	    public String toString() {
	        return name;
	    }
}
