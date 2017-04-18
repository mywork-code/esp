package com.apass.gfb.framework.webmvc;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @description Base Inteceptor
 *
 * @author lixining
 * @version $Id: ListeningInterceptorAdapter.java, v 0.1 2015年10月19日 下午1:47:55
 *          lixining Exp $
 */
public abstract class ListeningInterceptorAdapter extends
		HandlerInterceptorAdapter {
	/**
	 * 默认拦截路径
	 */
	public static final String DEFAULT_MAPPING_URL = "/**";

	/**
	 * URL Pattern
	 */
	public abstract String[] getMapping();
}
