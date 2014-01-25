package br.com.chale.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

public class ImpressaoTXTUtil {  
	
	//private static ImpressaoTXTUtil instance = new ImpressaoTXTUtil();
	public ImpressaoTXTUtil(){  
        // seleciona a impressora  
        detectaImpressoras();  
        setAtributos();  
        // cria a tarefa de impressao  
        printJob = impressora.createPrintJob();  
        //determina o tipo a ser impresso txt  
        docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
    }
	//public static ImpressaoTXTUtil getInstance() {  
     //   return instance;  
    //}  
 // variáveis  
    private  PrintService impressora;  
    private PrintRequestAttributeSet printerAttributes;  
    private DocPrintJob printJob;  
    private DocFlavor docFlavor;
  
      
    /**
     * @param mensagem
     * @throws IOException 
     * @throws PrintException 
     */
    public void escreveImpressao(String mensagem) throws IOException, PrintException   {  
  
            // abre o arquivo  
            File arquivo = File.createTempFile("impressao", ".txt", 
                    new File(System.getProperty("user.dir")));
            BufferedWriter out = new BufferedWriter(new FileWriter(arquivo));
            out.write(mensagem);
            out.close();
            
            arquivo.deleteOnExit();
            FileInputStream print = new FileInputStream(arquivo);  
            System.out.println("vai imprimir");  
            imprime(print);  
            arquivo.deleteOnExit();
            print.close();  
            
    }  
  
    public void imprime(InputStream print) throws PrintException {  
       Doc documentoTexto = new SimpleDoc(print, docFlavor, null);  
        // imprime  
        printJob.print(documentoTexto,  (PrintRequestAttributeSet) printerAttributes);  
    }  
    /** 
     * O metodo verifica se existe impressora conectada e a define como padrao. 
     */  
    public void detectaImpressoras() {  
        try {  
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;  
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);  
            for (PrintService p : ps) {  
                System.out.println("Impressora encontrada: " + p.getName());  
                if (p.getName().contains("Generic")) {  
                    System.out.println("Impressora Selecionada: " + p.getName());  
                    impressora = p;  
                    break;  
                }  
            }  
        } catch (Exception e) {  
  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * Definição dos atributos de impressão 
     * @return 
     */  
    private PrintRequestAttributeSet setAtributos() {  
        // determina os atributos  
        PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();  
        // quantidades de copias ****** opcional  
//      printerAttributes.add(new Copies(1));  
//      modelo do papel ****** opcional  
//      printerAttributes.add(MediaSizeName.ISO_A4);  
//      printerAttributes.add(new MediaPrintableArea(0, 0, 54, 85, MediaPrintableArea.MM));  
//      printerAttributes.add(new PrinterResolution(300, 300, PrinterResolution.DPI));  
        //defindo o tamanho do papel  
        MediaSizeName mediaSizeName = MediaSize.findMedia(80, 10000000, MediaPrintableArea.MM);  
        printerAttributes.add(mediaSizeName);  
        return printerAttributes;  
    }  
  
} 