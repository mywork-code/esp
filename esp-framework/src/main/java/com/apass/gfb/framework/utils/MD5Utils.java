package com.apass.gfb.framework.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

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


	/**
	 * 加密文件
	 * @param in
	 * @return
	 */
	public static String getMd5ByFile(InputStream in) {
		String value = null;
		try {
			String inStr = IOUtils.toString(in);
			//MappedByteBuffer byteBuffer = in..getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			md5.update(inStr.getBytes());
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if (null != in) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return value;
	}


}
