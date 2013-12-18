package com.br.chale.utils;



import java.awt.Component;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 * @author Jhonatan Rodrigues
 *
 */
public class Util {

//	/**
//     * Retorna um ImageIcon com base no path passado como
//     * parametro
//     * @param path Caminho para a busca da imagem 
//     * @return Imagem correspondente a url indicada
//     */
//    public static ImageIcon getFigura(String path) {
//        ImageIcon imagem = null;
//               
//           
//        if(imgURL != null) {       	
//            imagem = new ImageIcon(imgURL);
//        } 
//        
//        return imagem;
//    }
//    
//    public static ImageIcon getIcone(String nomeIcone) {
//        ImageIcon imagem = null;
//        java.net.URL imgURL =  br.com.chaledosventos.view.PrincipalView.class.getResource("imagens/icones/"+nomeIcone);       
//           
//        if(imgURL != null) {        
//            imagem = new ImageIcon(imgURL);
//        } 
//        
//        return imagem;
//        
//    }
//    
    /**
     * Retorna um Relatorio com base no path passado como
     * parametro
     * @param path Caminho para a busca da imagem 
     * @return Relatorio correspondente a url indicada
     */
    public static String getRelatorio(String path) {               
        String caminho = "./relatorio/"+path;
             
        return caminho;
    }
            
 	/**
     * Retorna um NumberFormat para formatar os campos BigDecimal em formato brasileiro 
     * @return NumberFormat
     */
    public static NumberFormat getNumberFormat(){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        
        return numberFormat;
    }
    
    /**
     * Retorna um vetor contendo os meses do ano em Portugues
     * Janeiro estah na posicao 0 (zero)
     * @return
     */
    public static String[] getMeses() {
        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril",
                "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro",
                "Dezembro"};
        return mes;
    }
    
    /**
     * Calcula a diferenca entre duas datas
     * @param dataPrimeira primeira Data
     * @param dataSegunda Data maior
     * @return valor inteiro que representa as seguintes situacoes: 
     * Igual a 0 se as datas forem iguais, 
     * -1 se a dataPrimeira for maior que a dataSegunda, e 
     * 1 se a dataSegunda for maior que a dataPrimeira
     */
    public static int diferencaEntreDatas(Date dataPrimeira, Date dataSegunda){
    	
    	int diferenca = 0;
    	Calendar calendarDataMenor = new GregorianCalendar();
        calendarDataMenor.setTime(dataPrimeira);
        
        Calendar calendarDataMaior = new GregorianCalendar();
        calendarDataMaior.setTime(dataSegunda);
        
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        
        try {
			Date d1 = df.parse(df.format((calendarDataMenor.getTimeInMillis())));
			Date d2 = df.parse(df.format((calendarDataMaior.getTimeInMillis())));
			
			calendarDataMenor.setTime(d1);
			calendarDataMaior.setTime(d2);
			
	        long m1 = calendarDataMenor.getTimeInMillis();
	        long m2 = calendarDataMaior.getTimeInMillis();
	        long result = (m2 - m1);
	        
	        if(result < 0l){
	        	diferenca = -1;
	        }else if(result > 0l){
	        	diferenca = 1;
	        }

		} catch (ParseException e) {
			e.printStackTrace();
		}
		  
        return diferenca;
    }
    
//    /**
//     * Metodo utilizado para limpar os paineis, passando o panel desejado
//     * por parametro
//     * @param panel
//     */
//    public static void limparPainel(JPanel panel){
//
//        Component[] componentes = panel.getComponents();
//        for (int i = 0; i < componentes.length; i++) {
//            if (componentes[i] instanceof JTextField) {
//                ((JTextField)componentes[i]).setText("");
//            }else if (componentes[i] instanceof JComboBox){
//                JComboBox combo = (JComboBox)componentes[i];
//                if (combo.getItemCount() > 0) {
//                    combo.setSelectedIndex(0);
//                }else {
//                    combo.setSelectedIndex(-1);
//                }
//            }else if (componentes[i] instanceof JDateChooser){
//                JDateChooser jDateChooser = ((JDateChooser)componentes[i]);
//                jDateChooser.setDate(null);                
//            }else if (componentes[i] instanceof JTextArea){
//                ((JTextArea)componentes[i]).setText("");
//            }else if (componentes[i] instanceof JFormattedTextField){
//                ((JTextField)componentes[i]).setText("");
//            }else if (componentes[i] instanceof JPasswordField){
//                ((JPasswordField)componentes[i]).setText("");
//            }
//        }
//    }
    
    public static boolean verificaCampoVazio(Object ob) {
    	boolean retorno = false;
    	if (ob instanceof String) {
    		if (Constantes.STRING_VAZIA.equals(ob.toString())) {
    			retorno = true;
    		}
    	} else if (ob instanceof Integer) {
    		if (Constantes.ZERO_INTEGER.equals(Integer.valueOf(ob.toString()))) {
    			retorno = true;
    		}
    	} else if (ob instanceof Long) {
    		if (Constantes.ZERO_LONG.equals(Long.valueOf(ob.toString()))) {
    			retorno = true;
    		}
    	} else if (ob instanceof Double) {
    		if (Constantes.ZERO_DOUBLE.equals(Double.valueOf(ob.toString()))) {
    			retorno = true;
    		}
    	} else if (ob == null) {
    		retorno = true;
    	}
    	return retorno;
    }
    
    public static void mostraMensagemCamposObrigatorios(Component parent, String campos) {
    	String message = montaMensagemCamposObrigatorios(campos);
    	JOptionPane.showMessageDialog(parent, message);
    }
    
    private static String montaMensagemCamposObrigatorios(String campos) {
    	StringBuilder b = new StringBuilder();
    	b.append(Constantes.MSG_CAMPOS_OBRIGATORIOS_INI);
    	b.append(campos.substring(0, campos.length() - 2));
    	b.append(Constantes.MSG_CAMPOS_OBRIGATORIOS_FIM);
    	return b.toString();
    }
    
    public static void mostrarMensagemRegistroSalvo(Component parent) {
    	JOptionPane.showMessageDialog(parent, Constantes.MSG_REGISTRO_SALVO_SUCESSO);
    }

	public static void mostrarMensagemRegistroAtualizado(Component parent) {
		JOptionPane.showMessageDialog(parent, Constantes.MSG_REGISTRO_ATUALIZADO_SUCESSO);
	}
	
	public static MaskFormatter getMascaraTelefone() { 
		MaskFormatter maskFormatter = new MaskFormatter();
		try{  
			maskFormatter.setMask(Constantes.MASCARA_TELEFONE); //Atribui a mascara
			maskFormatter.setPlaceholderCharacter(' '); //Caracter para preencimento   
		}  
		catch (Exception excecao) {  
			excecao.printStackTrace();  
		}   
		return maskFormatter;
	}

}