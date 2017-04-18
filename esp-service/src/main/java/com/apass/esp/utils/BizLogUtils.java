package com.apass.esp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务日志输出工具
 * 
 * @author admin
 *
 */
public class BizLogUtils {

	/**
	 * 业务行为日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger("com.vcredit.esp.bizlog");

	private BizLogUtils() {

	}

	public static void info(String message) {
		LOG.info(message);
	}

	public static void info(String message, Object... args) {
		LOG.info(message, args);
	}
}
