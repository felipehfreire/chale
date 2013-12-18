package com.br.chale.utils;


import java.io.InputStream;
import java.util.Properties;


/**
 * Classe que armazena as configurações do sistema de acordo com as especificações
 * do arquivo de configuração "projeto.properties", localizado no pacote padrão.
 * @author Felipe Freire
 */
public class ConfigProjeto {

    private static final String URL_ARQUIVO_CONFIGURACAO = "/projeto.properties";

    public static final int SUCESSO = -1;
    public static final int ERRO = 0 ;


    private static ConfigProjeto instance;
   
    private String propriedade;
    
    private ConfigProjeto() {

    }

    public static ConfigProjeto getInstance() {

        if (instance == null) {

            instance = new ConfigProjeto();

            try {
                instance.loadConfigurations();
            } catch (Exception e) {

            }
       }

       return instance;
    }

    private void loadConfigurations () throws Exception {
        
        this.propriedade = instance.getPropertyOfFile(URL_ARQUIVO_CONFIGURACAO,"x");

    }

    public String getPropertyOfFile (String filePath, String propertyName ) throws Exception {
       String path = "";

       try{
            Properties props = new Properties();
            InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);
            props.load(in);
            in.close();
            path = props.getProperty(propertyName);

        } catch(Exception e){
            throw e;
        }

       return path;
    }
    
    public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

}