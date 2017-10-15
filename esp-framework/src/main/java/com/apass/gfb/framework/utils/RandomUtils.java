package com.apass.gfb.framework.utils;

import java.util.Random;

public class RandomUtils {
	/**
	 * 随机数产生工具
	 */
	private static final Random random = new Random();

	/**
	 * 产生范围内的随机数
	 * 
	 * @param begin
	 * @param end
	 * @return int
	 */
	public static int getRandomInt(int begin, int end) {
		return random.nextInt(end - begin) + begin;
	}

	/**
	 * 生成指定位数的随机字符串
	 * 
	 * @param length
	 * @return String
	 */
	public static final String getRandom(int length) {
		StringBuffer buffer = new StringBuffer();
		String s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < length; i++) {
			buffer.append(s.charAt(random.nextInt(s.length())));
		}
		return buffer.toString();
	}
	
	/**
	 * 生成指定位数的随机字符串
	 * 
	 * @param length
	 * @return String
	 */
	public static final String getRandomExcept0ol1(int length) {
		StringBuffer buffer = new StringBuffer();
		String s = "0123456789";
		for (int i = 0; i < length; i++) {
			buffer.append(s.charAt(random.nextInt(s.length())));
		}
		return buffer.toString();
	}

	/**
	 * 生成指定位数的随机字符串
	 *
	 * @param length
	 * @return String
	 */
	@Deprecated
	public static final String getRandomNum(int length) {
		StringBuffer buffer = new StringBuffer();
		String s = "0123456789";
		for (int i = 0; i < length; i++) {
			buffer.append(s.charAt(random.nextInt(s.length())));
		}
		return buffer.toString();
	}

	/**
	 * 获取随机数建议用这个方法
	 * @param length
	 * @return
	 */
	public static String getNum(int length){
		StringBuffer buffer = new StringBuffer();
		String s = "0123456789";
		Random random = new Random(System.currentTimeMillis() + Long.valueOf(getRandomNum(5)));
		for (int i = 0; i < length; i++) {
			buffer.append(s.charAt(random.nextInt(s.length())));
		}
		return buffer.toString();
	}
}
