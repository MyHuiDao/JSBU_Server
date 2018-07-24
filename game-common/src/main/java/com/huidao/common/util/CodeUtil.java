package com.huidao.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class CodeUtil {

	/** 加密算法,可用 DES,DESede,Blowfish. */
	private final static String algorithm = "DES";

	public static String md5Encode(String inStr) {
		try {
			return encode(inStr, "MD5");
		} catch (Exception e) {
			return null;
		}
	}

	public static String shaEncode(String inStr) {
		try {
			return encode(inStr, "SHA");
		} catch (Exception e) {
			return null;
		}
	}

	public static MessageDigest getMD5MessageDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String encode(String inStr, String type) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance(type);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
	

	/**
	 * 对数据进行DES解密
	 */
	public final static String desDecrypt(String data,String key){
		StringBuffer sb=new StringBuffer();
		sb.append(key);
		for (int i = key.length(); i < 8; i++) {
			sb.append("0");
		}
		try {
			return new String(desDecrypt(desHex2byte(data.getBytes()), sb.toString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  DES加密数据.
	 */
	public final static String desEncrypt(String data,String key){
		StringBuffer sb=new StringBuffer();
		sb.append(key);
		for (int i = key.length(); i < 8; i++) {
			sb.append("0");
		}
		try {
			return desByte2hex(desEncrypt(data.getBytes(), sb.toString().getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 用指定的key对数据进行DES加密.
	 * 
	 * @param data
	 *            待加密的数据
	 * @param key
	 *            DES加密的key
	 * @return 返回DES加密后的数据
	 */
	private static byte[] desEncrypt(byte[] data, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(algorithm);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(data);
	}

	/**
	 * 用指定的key对数据进行DES解密.
	 * 
	 * @param data
	 *            待解密的数据
	 * @param key
	 *            DES解密的key
	 * @return 返回DES解密后的数据
	 * @throws Exception
	 */
	private static byte[] desDecrypt(byte[] data, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(algorithm);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(data);
	}

	public static byte[] desHex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String desByte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
	
	
	/**
	 * 获取签名
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String,String> map,String key){
		Collection<String> keyset= map.keySet();   
		List<String> list=new ArrayList<String>(keyset);  
		Collections.sort(list);  
		StringBuffer signStr=new StringBuffer();
		for(int i=0;i<list.size();i++){  
			signStr.append(list.get(i)+"="+map.get(list.get(i)));  
		} 
		return desDecrypt(signStr.toString(), key);
	}
	
	/**
	 * 获取签名
	 * @param map
	 * @return
	 */
	public static String getSignParameterMap(Map<String,String []> map,String key){
		Collection<String> keyset= map.keySet();   
		List<String> list=new ArrayList<String>(keyset);  
		Collections.sort(list);  
		StringBuffer signStr=new StringBuffer();
		for(int i=0;i<list.size();i++){  
			StringBuffer parameList=new StringBuffer();
			for (int j = 0; j < map.get(list.get(i)).length; j++) {
				if(j==map.get(list.get(i)).length-1){
					parameList.append(map.get(list.get(i))[j]);
				}else{
					parameList.append(map.get(list.get(i))[j]+",");
				}
			}
			signStr.append(list.get(i)+"="+parameList.toString());  
		} 
		return desDecrypt(signStr.toString(), key);
	}
	
}
