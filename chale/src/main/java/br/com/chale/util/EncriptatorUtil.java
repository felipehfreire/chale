package br.com.chale.util;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 
public class EncriptatorUtil {
			 // Usando chave de 128-bits (16 bytes)
		    byte[] chave = "chave de 16bytes".getBytes();
		    SecretKeySpec  sKeyS = new SecretKeySpec(chave, "AES");
		    byte[] encrypted;
		    byte[] decrypted;
        
		    public byte[]  encriptar (int mode, String mensagem){
	        	try {
	        		 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
					cipher.init(Cipher.ENCRYPT_MODE, sKeyS);
					encrypted= cipher.doFinal(mensagem.getBytes());
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}
	        	
	        	return encrypted;
	        }
		    
		    public String desencriptar (int mode,  byte[]  mensagem){
		    	 try {
		    		 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
					cipher.init(Cipher.DECRYPT_MODE, sKeyS);
					 decrypted= cipher.doFinal(encrypted);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}
		    	
		    	 return new String(decrypted);
		    }
		    
}