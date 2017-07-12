package org.yicou.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {
	public final static String key="qwead520";
	
	public AesUtil()
	{}
	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String encryptresult = encrypt("username=admin&pwd=admin&timestamp:"+new Date().getTime());
		System.out.println(encryptresult);
		String decryptresult = decrypt(encryptresult);
		System.out.println(decryptresult);
	}*/
	
	
	public static String encrypt(String content)
	{
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128, new SecureRandom(key.getBytes()));
			SecretKey sk = keygen.generateKey();
			byte[] key = sk.getEncoded();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"));
			byte[] result = cipher.doFinal(content.getBytes("UTF-8"));
			String resultstr=toHexString(result);
			return resultstr;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String decrypt(String content)
	{
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(128, new SecureRandom(key.getBytes()));
			SecretKey sk = keygen.generateKey();
			byte[] key = sk.getEncoded();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key,"AES"));
			byte[] result = cipher.doFinal(toBytes(content));
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String toHexString(byte[] bytes)
	{
		StringBuffer result = new StringBuffer();
		for(int i = 0;i<bytes.length;i++)
		{
			int temp = bytes[i] & 0xff;
			String tempHex = Integer.toHexString(temp);
			if(tempHex.length()<2)
			{
				tempHex = "0"+tempHex;
			}
			result.append(tempHex.toUpperCase());
		}
		return result.toString();
	}
	
	public static byte[] toBytes(String hexstr)
	{
		if(hexstr.length()<1) return null;
		byte[] result = new byte[hexstr.length()/2];
		for (int i=0;i<hexstr.length()/2;i++)
		{
			int high = Integer.parseInt(hexstr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexstr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low); 
		}
		return result;
	}
}
