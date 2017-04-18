package com.apass.gfb.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    /**
     *  乘法
     */
    public static BigDecimal multiply(BigDecimal numOne, BigDecimal numTwo) {
        if (numOne == null || numTwo == null) {
            return BigDecimal.ZERO;
        }
        return numOne.multiply(numTwo);
    }

    /**
     * 格式化金额
     * 
     * @param decimal
     * @return
     */
    public static String formatMoney(BigDecimal decimal) {
        decimal = (decimal == null) ? BigDecimal.ZERO : decimal;
        return formatMoney(decimal, 2);
    }

    /**
     * 乘法格式金额
     */
    public static String formatMultiplyMonty(BigDecimal decimal, BigDecimal decimalTwo) {
        return formatMoney(multiply(decimal, decimalTwo));
    }

    /**
     * 乘法格式金额
     */
    public static String formatMultiplyPercent(BigDecimal decimal, BigDecimal decimalTwo) {
        return formatPercent(multiply(decimal, decimalTwo));
    }

    /**
     * 格式化费率
     * 
     * @param decimal
     * @return
     */
    public static String formatPercent(BigDecimal decimal) {
        decimal = (decimal == null) ? BigDecimal.ZERO : decimal;
        return formatPercent(decimal, 2);
    }

    /**
     * 格式化费率
     * 
     * @param decimal
     * @return
     */
    public static String formatPercent(BigDecimal decimal, int scale) {
        return formatPercentNum(decimal, scale) + "%";
    }

    /**
     * 格式化百分比数字(不带百分号)
     * 
     * @param decimal
     * @return
     */
    public static String formatPercentNum(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "0.00";
        }
        BigDecimal num = decimal.multiply(new BigDecimal("100")).setScale(scale, RoundingMode.HALF_UP);
        return String.valueOf(num);
    }

    /**
     * 格式化数字
     * 
     * @param decimal
     * @param scale
     * @return
     */
    public static String formatMoney(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "";
        }
        return String.valueOf(decimal.setScale(scale, RoundingMode.HALF_UP));
    }

    public static BigDecimal formatDecimal(String value, int scale) {
        BigDecimal decimal = getDecimal(new BigDecimal(value));
        return decimal.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 身份证号取男女
     * 
     * @param identityNo
     * @return
     */
    public static String getIdentityGender(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        Integer val = Integer.parseInt(String.valueOf(identityNo.charAt(16)));
        return (val % 2 == 0) ? "女" : "男";
    }

    /**
     * 身份证号取生日
     * 
     * @param identityNo
     * @return
     */
    public static String getIdentityBirth(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        return identityNo.substring(6, 14);
//        return DateFormatUtil.string2string(birth, "yyyyMMdd", "yyyy-MM-dd");
    }

    /**
     * 查询参数
     * 
     * @param paramMap
     * @param name
     * @return String
     */
    public static String getValue(Map<String, Object> paramMap, String name) {
        if (paramMap == null || paramMap.isEmpty() || StringUtils.isBlank(name) || !paramMap.containsKey(name)) {
            return null;
        }
        Object obj = paramMap.get(name);
        if (obj == null) {
            return null;
        }
        String value = obj.toString();
        return value.trim();
    }

    /**
     * 查询参数
     * 
     * @param paramMap
     * @param name
     * @return String
     */
    public static Long getLong(Map<String, Object> paramMap, String name) {
        if (paramMap == null || paramMap.isEmpty() || StringUtils.isBlank(name) || !paramMap.containsKey(name)) {
            return null;
        }
        Object obj = paramMap.get(name);
        if (obj == null) {
            return null;
        }
        String value = obj.toString();
        return getLong(value);
    }
    
    /**
     * Get Long
     * 
     * @param value
     * @return
     */
    public static Long getLong(String value) {
        if (!StringUtils.isNumeric(value)) {
            return null;
        }
        return Long.valueOf(value.trim());
    }
    
    public static String getValue(HttpServletRequest request, String name) {
        Object obj = request.getParameter(name);
        if (obj == null) {
            return null;
        }
        String value = obj.toString();
        return value.trim();
    }

    public static Map<String, Object> getValues(HttpServletRequest request) {
        Map<String, Object> paramMap = Maps.newHashMap();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getParameter(key);
            paramMap.put(key, value);
        }
        return paramMap;
    }

    public static String getValue(Map<String, Object> paramMap, String name, String defaultValue) {
        return CommonUtils.getValue(getValue(paramMap, name), defaultValue);
    }

    /**
     * 获取Integer的字符串类型
     * 
     * @param value
     * @return
     */
    public static String getValue(Integer value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 获取Integer的字符串类型
     * 
     * @param value
     * @return
     */
    public static String getValue(BigDecimal value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 获取Long的字符串
     * 
     * @param value
     * @return
     */
    public static String getValue(Long value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 获取String的trimValue
     * 
     * @param value
     * @return
     */
    public static String getValue(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.trim();
    }

    /**
     * 获取Decimal默认值
     * 
     * @param applyAmount
     * @return
     */
    public static BigDecimal getDecimal(BigDecimal applyAmount) {
        return applyAmount == null ? BigDecimal.ZERO : applyAmount;
    }

    /**
     * 如果字符串为空返回默认值
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getValue(String value, String defaultValue) {
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    /**
     * 截取尾數字符
     * 
     * @param value
     * @param len
     * @return
     */
    public static String getLast(String value, int len) {
        if (StringUtils.isBlank(value) || len <= 0 || value.length() - len <= 0) {
            return getValue(value);
        }
        return value.substring(value.length() - len, value.length());
    }

    /**
     * <pre>
     * 字符串替换显示*
     * 
     * 比如身份证要显示成：370************013
     * 
     * @param value   - 要操作的内容
     * @param start   - 替换开始位置
     * @param end     - 替换结束位置
     * @param repChar - 要替换成的内容
     * @return String
     * </pre>
     */
    public static String replace(String value, int start, int end, String repChar) {
        if (StringUtils.isBlank(value) || start > end || repChar == null || value.length() < end) {
            return value;
        }
        String con = value.substring(start, end);
        String repStr = StringUtils.repeat(repChar, con.length());
        return value.replace(con, repStr);
    }

    /**
     * 往对象里设置属性值
     * 
     * @param bean
     * @param name
     * @param value
     */
    public static void setStringValue(Object bean, String name, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("没有对应的属性", e);
        }
    }

    /**
     * 往对象里设置值
     * 
     * @param paramMap
     * @param bean
     * @param paramName
     * @param fieldName
     */
    public static void setStringValue(Map<String, Object> paramMap, Object bean, String paramName, String fieldName) {
        String value = CommonUtils.getValue(paramMap, paramName);
        setStringValue(bean, fieldName, value);
    }

    public static void setStringValue(Map<String, Object> paramMap, Object bean, String fieldName) {
        String value = CommonUtils.getValue(paramMap, fieldName);
        setStringValue(bean, fieldName, value);
    }

    /**
     * 计算百分比
     * 
     * @param numOne
     * @param numTwo
     * @return
     */
    public static String calPercent(long numOne, long numTwo, int scale) {
        if (numTwo == 0) {
            return String.valueOf(BigDecimal.ZERO.setScale(scale)) + "%";
        }
        BigDecimal start = BigDecimal.valueOf(numOne);
        BigDecimal end = BigDecimal.valueOf(numTwo);
        BigDecimal result = start.multiply(new BigDecimal(100)).divide(end, RoundingMode.HALF_UP).setScale(scale);
        return String.valueOf(result) + "%";
    }

    /**
     * 是否小于等于
     * 
     * @param valOne
     * @param valTwo
     * @return boolean
     */
    public static boolean isLessEqThan(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.compareTo(valueTwo) <= 0;
    }

    /**
     * 是否小于
     * 
     * @param valOne
     * @param valTwo
     * @return boolean
     */
    public static boolean isLessThan(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.compareTo(valueTwo) <= 0;
    }
    /**
     * 减法
     * 
     * @param valOne
     * @param valTwo
     * @return boolean
     */
    public static BigDecimal subtract(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.subtract(valueTwo);
    }

    /**
     * +法
     * 
     * @param valOne
     * @param valTwo
     * @return boolean
     */
    public static BigDecimal add(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.add(valueTwo);
    }

    /**
     * 是否包含
     * 
     * @param value
     * @param chars
     * @return boolean
     */
    public static boolean contains(String value, String chars) {
        if (StringUtils.isAnyBlank(value, chars)) {
            return false;
        }
        return value.indexOf(chars) != -1;
    }

    /**
     * 是否包含任一
     * 
     * @param value
     * @param chars
     * @return
     */
    public static boolean containsAny(String value, String... chars) {
        if (chars == null || chars.length == 0) {
            return false;
        }
        boolean result = false;
        for (String content : chars) {
            if (contains(value, content)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Get Int
     * 
     * @param value
     * @return
     */
    public static Integer getInt(String value) {
        if (!StringUtils.isNumeric(value)) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * 随机码
     * 
     * @return String
     */
    public static String getRandom() {
        return String.valueOf(Math.random());
    }

    /**
     * 验证码图片
     * 
     * @param oriURL
     * @return
     */
    public static final String getRandomCodeUrl(String domain, String context, String oriURL) {
        if (StringUtils.isBlank(oriURL)) {
            return "";
        }
        String code = EncodeUtils.base64Encode(oriURL);
        StringBuilder buffer = new StringBuilder();
        buffer.append(domain).append("/");
        buffer.append(context).append("/common/files/random");
        buffer.append("?code=").append(code).append("&ver=").append(getRandom());
        return buffer.toString();
    }

    /**
     * 是否为零
     * 
     * @param value
     * @return boolean
     */
    public static final boolean isZero(BigDecimal value) {
        return value == null || BigDecimal.ZERO.compareTo(value) == 0;
    }

    /**
     * 字符串左补齐
     * 
     * @param value
     * @param length
     * @param sign
     * @return
     */
    public static String leftPad(String value, int length, String sign) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (value.trim().length() >= length) {
            return value;
        }
        int padLen = length - value.trim().length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < padLen; i++) {
            builder.append(sign);
        }
        return builder.toString() + value;
    }

    public static final boolean isAnySame(Integer expectCount, String... values) {
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(values));
        return set.size() != expectCount;
    }

    public static final boolean lt(String numOne, String numTwo) {
        if (!StringUtils.isNumeric(numOne) || !StringUtils.isNumeric(numTwo)) {
            return false;
        }
        BigDecimal start = new BigDecimal(numOne);
        BigDecimal end = new BigDecimal(numTwo);
        return start.compareTo(end) < 0;
    }

    /**
     * 比对身份证（带*）
     * 
     * @param identityNo
     * @param spiderIdentityNo
     * @return
     */
    public static final boolean isEqual(String identityNo, String spiderIdentityNo, String ignoreChar) {
        if (StringUtils.isAnyBlank(identityNo, spiderIdentityNo, ignoreChar)) {
            return true;
        }
        if (identityNo.length() != spiderIdentityNo.length()) {
            return false;
        }
        String[] identityNoArr = identityNo.split("");
        String[] spiderIdentityNoArr = spiderIdentityNo.split("");
        boolean result = true;
        for (int i = 0; i < identityNoArr.length; i++) {
            String content = identityNoArr[i];
            String compareContent = spiderIdentityNoArr[i];
            if (StringUtils.equals(content, ignoreChar) || StringUtils.equals(compareContent, ignoreChar)) {
                continue;
            }
            if (!StringUtils.equalsIgnoreCase(content, compareContent)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 查询参数
     * 
     * @param paramMap
     * @param name
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapValue(Map<String, Object> paramMap, String name) {
        if (paramMap == null || paramMap.isEmpty() || StringUtils.isBlank(name) || !paramMap.containsKey(name)) {
            return null;
        }
        Object obj = paramMap.get(name);
        if (obj == null) {
            return null;
        }
        Map<String, Object> value = null;
        if (obj instanceof Map)
            value = (Map<String, Object>) obj;
        return value;
    }

    public static final String getFileName(String pathname) {
        int index = -1;
        if (pathname.lastIndexOf("/") != -1) {
            index = pathname.lastIndexOf("/");
        }
        if (pathname.lastIndexOf("\\") != -1) {
            index = pathname.lastIndexOf("\\");
        }
        index = index < 0 ? 0 : index;
        return pathname.substring(index + 1);
    }

    /**
     * <pre>
     *  解析Query String -> Map 如：a=1&b=2&c=3   ====> {"a":"1","b":"2","c":"3"}
     * </pre>
     * @param queryString
     * @return map
     */
    public static final Map<String, String> parseQueryString(String queryString) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isBlank(queryString)) {
            return paramMap;
        }
        String tempURL = EncodeUtils.urlDecode(queryString);
        StringTokenizer st = new StringTokenizer(tempURL, "&");
        while (st.hasMoreTokens()) {
            String pairs = st.nextToken();
            String key = pairs.substring(0, pairs.indexOf('='));
            String value = pairs.substring(pairs.indexOf('=') + 1);
            paramMap.put(key, value);
        }
        return paramMap;
    }

    public static final String getErrorMsg(String queryString) {
    	if(null==queryString){
    		return "";
    	}else if(queryString.indexOf("oh...出错了") != -1){
    		return "获取次数过多，请稍后再试";
    	}else if(queryString.indexOf("[5104902]")>=0){
    		return "oh...出错了，再试试？";
    	}else if(queryString.indexOf("[2020010]")>=0){
    		return "交易失败。详情请咨询95516";
    	}else if(queryString.indexOf("Token失效")>=0){
    		return "页面飞啦，请退出重试哦";
    	}else if(queryString.indexOf("卡号输入错误或暂不支持支付")>=0){
    		return "暂不支持该银行，请查看可支持的银行列表";
    	}else{
    		return queryString;
    	}
    }
}
