package com.apass.gfb.framework.exception;

public class AbstractException {
	/**
	 * PAGE_PACKAGE
	 */
	public static final String PAGE_PACKAGE = "common/error";
	/**
	 * Error Page
	 */
	public static final String PAGE_ERROR = "/error";
	/**
	 * 403
	 */
	public static final String PAGE_403 = "/403";
	/**
	 * 404
	 */
	public static final String PAGE_404 = "/404";
	/**
	 * 500
	 */
	public static final String PAGE_500 = "/500";

	/**
	 * 获取映射页面地址
	 * 
	 * @param page
	 * @return String
	 */
	public String getMapping(String page) {
		return PAGE_PACKAGE + page;
	}
}
