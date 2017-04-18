package com.apass.gfb.framework.jwt.common;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description 字符串工具类
 * 
 * @author Listening
 * @version $Id: CommonStringUtils.java, v 0.1 2014年11月1日 下午6:53:21 Listening
 *          Exp $
 */
public class ListeningStringUtils {

    private ListeningStringUtils() {

    }

    /**
     * Check value is blank
     * 
     * @param value
     * @return
     */
    public static final boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }

    /**
     * Is Not Blank
     * 
     * @param value
     * @return
     */
    public static final boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }

    /**
     * Trim Blank Characters Or Wraps
     */
    public static final String trimWrap(String value) {
        String tempValue = value == null ? "" : value.trim();
        return tempValue.replaceAll("\\s+", "");
    }

    /**
     * IS Any Blank
     * 
     * @param value
     * @return
     */
    public static final boolean isAnyBlank(String... value) {
        return StringUtils.isAnyBlank(value);
    }

    /**
     * IS All Blank
     * 
     * @param value
     * @return
     */
    public static final boolean isAllBlank(String... values) {
        boolean result = true;
        for (String value : values) {
            if (isNotBlank(value)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Is Contains The Chars In the value
     * 
     * @param value
     * @param chars
     * @return
     */
    public static final boolean contains(String value, String chars) {
        return StringUtils.contains(value, chars);
    }

    /**
     * check string is numeric
     * 
     * @param value
     * @return
     */
    public static final boolean isNumeric(String value) {
        return StringUtils.isNumeric(value);
    }

    /**
     * To Upper Case
     * 
     * @param value
     * @return
     */
    public static final String upperCase(String value) {
        return StringUtils.upperCase(value);
    }

    /**
     * To Upper Case
     * 
     * @param value
     * @return
     */
    public static final String lowerCase(String value) {
        return StringUtils.lowerCase(value);
    }

    /**
     * The First Character to Lower case
     * 
     * @param value
     * @return
     */
    public static final String uncapitalize(String value) {
        return StringUtils.uncapitalize(value);
    }

    /**
     * The First Character to Upper case
     * 
     * @param value
     * @return
     */
    public static final String capitalize(String value) {
        return StringUtils.capitalize(value);
    }

    /**
     * Equals
     * 
     * @param value
     * @return
     */
    public static final boolean equals(String one, String two) {
        return StringUtils.equals(one, two);
    }

    /**
     * Equals
     * 
     * @param value
     * @return
     */
    public static final boolean equalsIgnoreCase(String one, String two) {
        return StringUtils.equalsIgnoreCase(one, two);
    }

    /**
     * 字符串字节长度
     * 
     * @param value
     * @return Integer
     */
    public static Integer bytesLength(String value) {
        // int length = 0;
        // if (ListeningStringUtils.isBlank(value)) {
        // return length;
        // }
        // for (int i = 0; i < value.length(); i++) {
        // int ascii = Character.codePointAt(value, i);
        // if (ascii >= 0 && ascii <= 255) {
        // length += 1;
        // continue;
        // }
        // length += 2;
        // }
        // return length;
        return ListeningRegExpUtils.length(value);
    }

    /**
     * 字符长度区间
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static boolean length(String value, int min, int max) {
        int length = bytesLength(value);
        return length >= min && length <= max;
    }

    /**
     * 格式化字符串
     * 
     * @param value
     * @param params
     * @return String
     */
    public static final String format(String value, Object... params) {
        return MessageFormat.format(value, params);
    }

    /**
     * 判断两个值是否相同
     */
    public static final boolean isSame(String valueOne, String valueTwo) {
        // 都是空则相同(忽略空串和null,默认统一处理)
        if (StringUtils.isBlank(valueOne) && StringUtils.isBlank(valueTwo)) {
            return true;
        }
        // 都不为空,equal比较
        if (!StringUtils.isAnyBlank(valueOne, valueTwo)) {
            return valueOne.trim().equals(valueTwo.trim());
        }
        // 其一为空返回false
        return false;
    }

    public static final String getValue(String value) {
        return StringUtils.isBlank(value) ? "" : value.trim();
    }

    /**
     * 获取要处理对象的trim值,如果是blank,则返回默认值
     */
    public static final String getValue(String value, String def) {
        return StringUtils.isBlank(value) ? def : value.trim();
    }

    /**
     * Get The Request Value From Map
     */
    public static final String getValue(Map<String, Object> paramMap, String paramCode) {
        if (MapUtils.isEmpty(paramMap) || StringUtils.isEmpty(paramCode)) {
            return null;
        }
        Object value = paramMap.get(paramCode);
        return value == null ? null : String.valueOf(value).trim();
    }

    /**
     * 获取要处理对象的trim值,如果是blank,则返回默认值
     */
    public static final String getValue(Integer value, Integer def) {
        return (value == null) ? String.valueOf(def) : String.valueOf(value);
    }

    /**
     * Parse String To Integer
     */
    public static final Integer getIntValue(String value) {
        return getIntValue(value, null);
    }

    /**
     * Parse String To Integer
     */
    public static final Integer getIntValue(String value, Integer def) {
        if (StringUtils.isBlank(value)) {
            return def;
        }
        String trimVal = getValue(value);
        if (!ListeningRegExpUtils.isNumberic(trimVal)) {
            throw new RuntimeException("非数字内容不能进行转换");
        }
        return Integer.parseInt(value);
    }

    /**
     * Parse String To Long
     */
    public static final Long getLongValue(String value) {
        return getLongValue(value, null);
    }

    /**
     * Parse String To Long
     */
    public static final Long getLongValue(String value, Long def) {
        if (StringUtils.isBlank(value)) {
            return def;
        }
        String trimVal = getValue(value);
        if (!ListeningRegExpUtils.isNumberic(trimVal)) {
            throw new RuntimeException("非数字内容不能进行转换");
        }
        return Long.valueOf(trimVal);
    }

    /**
     * Get Integer Value From ParamMap
     */
    public static final Integer getIntValue(Map<String, Object> paramMap, String paramCode, Integer def) {
        String value = getValue(paramMap, paramCode);
        return getIntValue(value, def);
    }

    /**
     * Get Long Value From ParamMap
     */
    public static final Long getLongValue(Map<String, Object> paramMap, String paramCode, Long def) {
        String value = getValue(paramMap, paramCode);
        return getLongValue(value, def);
    }

    /**
     * Long 2 Integer
     * 
     * @param value
     * @return
     */
    public static final Integer long2int(Long value) {
        return value == null ? 0 : Integer.parseInt(String.valueOf(value));
    }

    /**
     * Get Simple Json Content
     */
    public static final String simpleJSON(String success, String message) {
        return "{'success':'" + success + "', 'message':'" + message + "'}";
    }

    /**
     * Get File Suffix Name
     */
    public static final String getFileSuffix(String oriName) {
        if (StringUtils.isBlank(oriName) || oriName.indexOf(".") == -1) {
            return null;
        }
        return oriName.substring(oriName.lastIndexOf("."));
    }

    /**
     * Is Not Emoji Character
     */
    public static boolean isNotEmojiCharacter(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)) {
            return true;
        }
        if ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) {
            return true;
        }
        if ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) {
            return true;
        }
        return ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString().trim();
    }

    /**
     * Get UUID
     */
    public static final String getUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("[-]", "");
    }

    /**
     * <p>
     * Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.
     * </p>
     *
     * <p>
     * {@code null}s are handled without exceptions. Two {@code null} references
     * are considered not equal. Comparison is case insensitive.
     * </p>
     *
     * <pre>
     * StringUtils.equalIgnoreCaseWithAny(null, null) = false
     * StringUtils.equalIgnoreCaseWithAny(null, "abc") = false
     * StringUtils.equalIgnoreCaseWithAny("abc", null, "abc") = true
     * StringUtils.equalIgnoreCaseWithAny("abc", "abc", "bcd") = true
     * StringUtils.equalIgnoreCaseWithAny("abc", "ABC", "bcd") = true
     * </pre>
     *
     * @param value
     *            the first CharSequence, can not be null
     * @param params
     *            the second CharSequence, can not be null
     */
    public static final boolean equalsIgnoreCaseWithAny(String value, String... params) {
        if (StringUtils.isBlank(value) || params == null) {
            return false;
        }
        boolean result = false;
        for (String paramVal : params) {
            if (StringUtils.isBlank(paramVal)) {
                continue;
            }
            if (StringUtils.equalsIgnoreCase(paramVal, value)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
