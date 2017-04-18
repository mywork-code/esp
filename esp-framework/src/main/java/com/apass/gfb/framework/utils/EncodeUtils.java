package com.apass.gfb.framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodeUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(EncodeUtils.class);
	private static final String DEFAULT_ENCODING = "utf-8";
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	/**
	 * Base64编码String
	 * 
	 * @param value
	 * @return String
	 */
	public static String base64Encode(String value) {
	    if(StringUtils.isBlank(value)){
	        return null;
	    }
		byte[] data = getBytes(value);
		if (data == null) {
			return null;
		}
		return Base64.encodeBase64String(data);
	}

	/**
	 * Base64解码
	 * 
	 * @param value
	 * @return String
	 */
	public static String base64Decode(String value) {
		byte[] data = Base64.decodeBase64(value);
		return convertString(data);
	}

	/**
	 * 字节数组转String
	 * 
	 * @param data
	 * @return String
	 */
	public static String convertString(byte[] data) {
		String result = null;
		try {
			result = new String(data, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error("convert string fail", e);
		}
		return result;
	}

	/**
	 * 字符转码
	 * 
	 * @param value
	 * @return String
	 */
	public static String urlDecode(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		try {
			return URLDecoder.decode(value, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error(value + "url decode fail", e);
		}
		return null;
	}

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) {
		return Base64.encodeBase64String(getBytes(input));
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base64解码.
	 */
	public static String decodeBase64ToString(String input) {
		return getString(Base64.decodeBase64(input));
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert String To Bytes
	 */
	public static byte[] getBytes(String content) {
		try {
			return content.getBytes(DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert bytep[] to String
	 * 
	 * @param content
	 * @return String
	 */
	public static String getString(byte[] content) {
		try {
			return new String(content, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
