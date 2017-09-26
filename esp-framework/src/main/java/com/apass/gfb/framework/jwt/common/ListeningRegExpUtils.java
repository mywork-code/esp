package com.apass.gfb.framework.jwt.common;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证工具
 */
public class ListeningRegExpUtils {
    /**
     * 手机正则
     */
    public static final String MOBILE   = "1\\d{10}";

    /**
     * QQ正则
     */
    public static final String QQ       = "[1-9]{1}\\d{5,9}";

    /**
     * password
     */
    public static final String PASSWORD = "[a-zA-Z0-9_!@#$%&]{6,20}";

    /**
     * email
     */
    public static final String EMAIL    = "\\w{1,25}@\\w{1,13}(\\.\\w{2,3}){1,3}";

    /**
     * Check Matches
     * 
     * @param pattern
     * @param value
     * @return boolean
     */
    public static boolean matches(String pattern, String value) {
        return Pattern.matches(pattern, value);
    }

    /**
     * check the bytes length whether between min and max
     * 
     * @param value
     * @param min
     * @param max
     */
    public static boolean length(String value, int min, int max) {
        int length = length(value);
        return length <= max && length >= min;
    }

    public static boolean lengthStr(String value, int min, int max){
    	int length = StringUtils.length(value);
    	return length <= max && length >= min;
    }
    /**
     * get the string value bytes length
     * 
     * @param value
     * @return int
     */
    public static int length(String value) {
        if (ListeningStringUtils.isBlank(value)) {
            return 0;
        }
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

    /**
     * 是否合法日期
     * 
     * @param value
     * @param sepKey
     *            - 分割字符
     * @return boolean
     */
    public static boolean isDate(String value, String sepKey) {
        if (ListeningStringUtils.isBlank(value)) {
            return false;
        }
        StringBuffer pattern = new StringBuffer();
        pattern.append("^(?:(?!0000)[0-9]{4}(").append(sepKey);
        pattern.append(")(?:(?:0[1-9]|1[0-2])(").append(sepKey);
        pattern.append(")(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(").append(sepKey);
        pattern.append(")(?:29|30)|(?:0[13578]|1[02])(").append(sepKey);
        pattern.append(")31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|");
        pattern.append("(?:0[48]|[2468][048]|[13579][26])00)(").append(sepKey);
        pattern.append(")02(").append(sepKey).append(")29)$");
        return Pattern.matches(pattern.toString(), value);
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
     * 是否为数字
     */
    public static boolean isNumberic(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        if (StringUtils.equals("-", value)) {
            return false;
        }
        final int sz = value.length();
        for (int i = 0; i < sz; i++) {
            char character = value.charAt(i);
            boolean isSign = "-".equals(String.valueOf(character));
            boolean isDigit = Character.isDigit(character);
            if (i == 0 && !isSign && !isDigit) {
                return false;
            }
            if (i > 0 && !isDigit) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否Decimal
     */
    public static boolean isDecimal(String value, int presion, int scale) {
        if (presion <= 0 || scale <= 0 || presion - scale - 1 < 0) {
            throw new RuntimeException("invalid presion scale param");
        }
        StringBuffer pattern = new StringBuffer();
        pattern.append("^(([1-9]\\d{0,").append(presion - scale - 1);
        pattern.append("})|0)(\\.\\d{0,").append(scale).append("})?$");
        return Pattern.matches(pattern.toString(), value);
    }

    /**
     * Validate is decimal , has no length limit
     * 
     * @param value
     * @return boolean
     */
    public static boolean isWildDecimal(String value) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("^-?(([1-9]\\d{0,");
        pattern.append("})|0)(\\.\\d{0,").append("})?$");
        return Pattern.matches(pattern.toString(), value);
    }

    /**
     * 是否汉字
     */
    public static boolean isChineseCharacter(String value) {
        return Pattern.matches("[\u4e00-\u9fa5]*", value);
    }
    
    /**
     * 是否为字母
     */
    public static boolean isLetterCharacter(String value){
    	return Pattern.matches("[a-zA-Z]*", value);
    }
    
    /**
     * 是否为汉字和字母
     */
    public static boolean isChineseOrLetterCharacter(String value){
    	return Pattern.matches("^[a-zA-Z\u4e00-\u9fa5]+$", value);
    }
    
    public static boolean isChineseOrLetterOrMath(String value){
    	return Pattern.matches("^[a-zA-Z0-9\u4E00-\u9FA5]+$", value);
    }
    /**
     * Check Is Multi Mails separate by chars
     */
    public static final boolean isMultiMails(String value, String separate) {
        String pattern = "(" + EMAIL + separate + ")*(" + EMAIL + separate + "?)";
        return matches(pattern, value);
    }

    /**
     * 验证字符串长度（汉字*2 其它 1）
     * @param value
     * @return
     */
    public static final int valueLength(String value){
    	int length = 0;
    	if (StringUtils.isBlank(value)) {
            return length;
        }
    	
    	char[] ch = value.toCharArray();
    	
    	for (char c : ch) {
			if(isChineseCharacter(String.valueOf(c))){
				length+=2;
			}else{
				length++;
			}
		}
    	
    	return length;
    }
    
    public static boolean lengthValue(String value, int min, int max){
    	int length = valueLength(value);
    	return length <= max && length >= min;
    }
    
}
