package com.apass.gfb.framework.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class HttpWebUtils {

	/**
	 * Get Http Servlet Session
	 * 
	 * @param request
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * Get Param Value From HttpServletRequest
	 */
	public static final String getValue(HttpServletRequest request,
			String param, String def) {
		if (request == null || StringUtils.isBlank(param)) {
			return def;
		}
		Object paramValue = request.getParameter(param);
		if (paramValue == null) {
			return def;
		}
		return String.valueOf(paramValue).trim();
	}

	/**
	 * Get Param Value From HttpServletRequest
	 */
	public static final String getValue(HttpServletRequest request, String param) {
		return getValue(request, param, null);
	}

	/**
	 * 是否为Ajax请求
	 * 
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	/**
	 * 是否为Ajax请求
	 * 
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(ServletRequest request) {
		return isAjaxRequest((HttpServletRequest) request);
	}

	/**
	 * Write Content To Response
	 * 
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void responseWriteJson(HttpServletResponse response,
			String content) {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		responseWrite(response, content);
	}

	/**
	 * Write Content to Response
	 * 
	 * @param response
	 * @param content
	 */
	public static void responseWrite(HttpServletResponse response,
			String content) {
		try {
			Writer writer = response.getWriter();
			writer.write(content);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException("write content to response fail", e);
		}
	}

	/**
	 * Write Content To Response
	 * 
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response, String content)
			throws IOException {
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		Writer writer = response.getWriter();
		writer.write(content);
		writer.flush();
	}

	public static void writeFromInToOut(BufferedInputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
	}

	/**
	 * Write JSON To Response
	 * 
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void writeJson(ServletResponse response, boolean result,
			String message) {
		writeJson((HttpServletResponse) response, result, message);
	}

	/**
	 * Write JSON To Response
	 */
	public static void writeJson(HttpServletResponse response, boolean result,
			String message) {
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("result", result);
		resultMap.put("message", message);
		responseWriteJson(response, GsonUtils.toJson(resultMap));
	}

	public static void setViewHeader(HttpServletResponse response,
			String contentType) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(contentType + ";charset=gb2312");
	}

	/**
	 * Set File Download Header
	 * 
	 * @param response
	 * @param fileName
	 * @param fileLength
	 */
	public static void setDownloadHeader(HttpServletResponse response,
			String fileName, Long fileLength) {
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes()));
		response.addHeader("Content-Length", "" + fileLength);
	}

	/**
	 * Set File Download Header
	 * 
	 * @param response
	 * @param fileName
	 * @param fileLength
	 */
	public static void setDownloadHeader(HttpServletResponse response,
			String fileName, Integer fileLength) {
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes()));
	}

	/**
	 * Set File Download Header
	 * 
	 * @param response
	 * @param fileName
	 * @param fileLength
	 */
	public static void setDownloadHeader(HttpServletResponse response,
			String fileName) {
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes()));
	}

	/**
	 * 
	 * @param response
	 * @param fileName
	 * @param charset
	 *            传递字符编码，简体中文请传入gb2312或者gbk
	 * @throws UnsupportedEncodingException
	 */
	public static void setDownloadHeader(HttpServletResponse response,
			String fileName, String charset)
			throws UnsupportedEncodingException {
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(charset), "ISO8859-1"));
	}

	/**
	 * Get Session Value
	 * 
	 * @param request
	 * @param paramCode
	 * @return String
	 */
	public static String getSessionValue(HttpServletRequest request,
			String paramCode) {
		if (request == null || StringUtils.isBlank(paramCode)) {
			return null;
		}
		Object paramValue = request.getSession().getAttribute(paramCode);
		if (paramValue == null) {
			return null;
		}
		return String.valueOf(paramValue).trim();
	}

}
