package com.apass.gfb.framework.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class MD5Utils {
	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

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
	 * 加密文件:小文件
	 * @param in
	 * @return
	 */
	public static String getMd5ByFile(InputStream in) {
        char hexDigitsIn[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
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
                str[k++] = hexDigitsIn[b >>> 4 & 0xf];
                str[k++] = hexDigitsIn[b & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}



	/**

	 * @funcion 对文件全文生成MD5摘要

	 * @param file:要加密的文件

	 * @return MD5摘要码

	 */

	public static String getMD5(File file) {

		FileInputStream fis = null;

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			fis = new FileInputStream(file);

			byte[] buffer = new byte[2048];

			int length = -1;

			while ((length = fis.read(buffer)) != -1) {

				md.update(buffer, 0, length);

			}

			byte[] b = md.digest();

			return byteToHexString(b);

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		} finally {

			try {

				fis.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}



	/**

	 * @function 把byte[]数组转换成十六进制字符串表示形式

	 * @param tmp  要转换的byte[]

	 * @return 十六进制字符串表示形式

	 */

	private static String byteToHexString(byte[] tmp) {

		String s;

		// 用字节表示就是 16 个字节

		// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符

		// 比如一个字节为01011011，用十六进制字符来表示就是“5b”

		char str[] = new char[16 * 2];

		int k = 0; // 表示转换结果中对应的字符位置

		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换

			byte byte0 = tmp[i]; // 取第 i 个字节

			str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移

			str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换

		}



		s = new String(str); // 换后的结果转换为字符串

		return s;

	}



	public static void main(String arg[]) {

		String a = getMD5(new File("e:/a.txt"));

		String b = getMD5(new File("e:/b.txt"));

		String c = getMD5(new File("e:/c.txt"));



		System.out.println("a.txt的摘要值为：" + a);

		System.out.println("b.txt的摘要值为：" + b);

		System.out.println("c.txt的摘要值为：" + c);



		if(a.equals(b)) {

			System.out.println("a.txt中的内容与b.txt中的内容一致");

		} else {

			System.out.println("a.txt中的内容与b.txt中的内容不一致");

		}



		if(a.equals(c)) {

			System.out.println("a.txt中的内容与c.txt中的内容一致");

		} else {

			System.out.println("a.txt中的内容与c.txt中的内容不一致");

		}

	}


	public static String getStingMD5(String pwd) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[]  bytes = digest.digest(pwd.getBytes());
			StringBuffer sb = new  StringBuffer();
			for(int i = 0;i<bytes.length;i++){
				String s = Integer.toHexString(0xff&bytes[i]);

				if(s.length()==1){
					sb.append("0"+s);
				}else{
					sb.append(s);
				}
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("String to md5 Exception.....");
		}
	}



}
