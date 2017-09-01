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
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            String inStr = IOUtils.toString(in);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要, 获得密文
            byte[] md = mdInst.digest(inStr.getBytes());
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte b : md) {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}


}
