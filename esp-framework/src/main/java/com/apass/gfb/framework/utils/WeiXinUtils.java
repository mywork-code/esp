package com.apass.gfb.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;

public class WeiXinUtils {
	private static final String TOKEN = "kkdai";

	/**
	 * 验证是否相应微信Token
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return boolean
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean validate(String signature, String timestamp,
			String nonce) throws NoSuchAlgorithmException {
		String[] str = { TOKEN, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2]; // SHA1加密

		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(EncodeUtils.getBytes(bigStr));
		Formatter formatter = new Formatter();
		for (byte b : crypt.digest()) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return signature.equals(result);
	}

	public static String getCDATA(String value) {
		return "<![CDATA[" + value + "]]>";
	}
}
