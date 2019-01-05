package com.cmnt.nurse.common.utils;


import com.cmnt.nurse.common.exception.BusinessException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;


public class AESUtil {

	private static final String AES_PADDING = "AES/ECB/PKCS5Padding";
	private static final String AES_IOS_PADDING = "AES/CBC/PKCS5Padding";
	private static final String ivParameter = "0392039203920300";
	private static String val = "0";
	private static int pwdLenght = 16;
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * AES加密字符串
	 * 
	 * @param content
	 *            需要被加密的字符串
	 * @param password
	 *            加密需要的密码
	 * @return 密文
	 */
	public static byte[] encrypt(String content, String password) {
		try {

			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(AES_PADDING);// algorithmStr
			byte[] byteContent = content.getBytes(UTF_8);
			cipher.init(Cipher.ENCRYPT_MODE, key);// ʼ
			byte[] result = cipher.doFinal(byteContent);
			return result;

		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] encrypt(byte[] content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(AES_PADDING);// algorithmStr
			cipher.init(Cipher.ENCRYPT_MODE, key);// ʼ
			byte[] result = cipher.doFinal(content);
			return result;

		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密AES加密过的字符串
	 * 
	 * @param content
	 *            AES加密过过的内容
	 * @param password
	 *            加密时的密码
	 * @return 明文
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {

			byte[] keyStr = password.getBytes();
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(AES_PADDING);// algorithmStr
			cipher.init(Cipher.DECRYPT_MODE, key);// ʼ
			byte[] result = cipher.doFinal(content);
			return result; //
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
		return null;
	}

	// key
	public static String toMakekey(String str, int strLength, String val) {

		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(str).append(val);
				str = buffer.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	// 加密
	public static byte[] encryptIos(byte[] content, String sKey) {
		try {

			sKey = toMakekey(sKey, pwdLenght, val);
			Cipher cipher = Cipher.getInstance(AES_IOS_PADDING);
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content);
			return encrypted;// 此处使用BASE64做转码。
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
			throw BusinessException.creat("99996");
		}
	}

	// 解密
	public static byte[] decryptIos(byte[] content, String sKey) {
		try {
			sKey = toMakekey(sKey, pwdLenght, val);
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance(AES_IOS_PADDING);
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(content);
			return original;
		} catch (Exception ex) {
			LogUtil.error(ex.getMessage(), ex);
			throw BusinessException.creat("99996");
		}
	}
}
