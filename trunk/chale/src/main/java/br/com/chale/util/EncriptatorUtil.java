package br.com.chale.util;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Adaptado por
 * @author felipe.freire
 */
public class EncriptatorUtil {
			 // Usando chave de 128-bits (16 bytes) para criptografia
		    byte[] chave = "0101010101010101".getBytes();//cahve- 0101010101010101
		    SecretKeySpec  sKeyS = new SecretKeySpec(chave, "AES");// seta a chave e o tipo de criptografia  
		    byte[] encrypted;
		    byte[] decrypted;
        
		    public String  encriptar (int mode, String mensagem){
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
	        	
	        	return new String(encrypted);
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
		    
		    //formata a sneha no padão MD5 do banco
		    public static final String formatMD5(String senha){  
		        String sen = "";  
		        MessageDigest md = null;  
		        try {  
		            md = MessageDigest.getInstance("MD5");  
		        } catch (NoSuchAlgorithmException e) {  
		            e.printStackTrace();  
		        }  
		        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
		        sen = hash.toString(16);              
		        return sen;  
		    }  
}