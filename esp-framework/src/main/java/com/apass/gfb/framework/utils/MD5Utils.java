package com.apass.gfb.framework.utils;

import java.security.MessageDigest;

public class MD5Utils {

	/**
	 * MD5 Encrypt
	 * 
	 * @param info
	 * @return String
	 * @throws Exception
	 */
	public static String encrypt(String info) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(info.getBytes("UTF-8"));
		byte[] result = md5.digest();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < result.length; ++i) {
			String str = Integer.toHexString(result[i] & 0xFF);
			if (str.length() == 1) {
				buffer.append("0");
			}
			buffer.append(str);
		}
		return buffer.toString().toUpperCase();
	}

}
