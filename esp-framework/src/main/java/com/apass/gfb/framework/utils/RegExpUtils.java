package com.apass.gfb.framework.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证工具
 */
public class RegExpUtils {

	/**
	 * 
	 * @param value
	 * @param min
	 * @param max
	 */
	public static boolean length(String value, int min, int max) {
		int length = StringUtils.isBlank(value) ? 0 : length(value);
		return length <= max && length >= min;
	}

	/**
	 * 字节长度
	 * 
	 * @param value
	 * @return int
	 */
	public static int length(String value) {
		String content = value.replaceAll("[^\\x00-\\xff]", "**");
		return content.length();
	}

	/**
	 * 是否手机号码
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean mobile(String value) {
		return Pattern.matches("^1[0-9]{10}$", value);
	}

	public static boolean mobiles(String value){
		return Pattern.matches("^((13[0-9])|(14[5|7])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$", value);
	}
	/**
	 * 是否合法日期
	 * 
	 * @param value
	 * @param sepKey
	 *            - 分割字符
	 * @return boolean
	 */
	public static boolean isDate(String value, String sepKey) {
		String datePattern = "^(?:(?!0000)[0-9]{4}("
				+ sepKey
				+ ")(?:(?:0?[1-9]|1[0-2])("
				+ sepKey
				+ ")(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])("
				+ sepKey
				+ ")(?:29|30)|(?:0?[13578]|1[02])("
				+ sepKey
				+ ")31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)("
				+ sepKey + ")0?2(" + sepKey + ")29)$";
		return Pattern.matches(datePattern, value);
	}

	/**
	 * 是否合法日期 yyyyMMdd
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isDateYMD(String value) {
		return isDate(value, "");
	}

	/**
	 * 输入正则表达式 和需要判断的值，返回判断结果
	 * 
	 * @param regex
	 * @param value
	 * @return
	 */
	public static boolean checkByRule(String regex, String value) {
		return Pattern.matches(regex, value);
	}

}
