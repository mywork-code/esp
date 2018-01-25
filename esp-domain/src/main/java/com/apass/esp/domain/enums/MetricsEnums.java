package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;
/**
 * metrics支持范围
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2018年1月25日 上午10:18:24 
 * @description
 */
public enum MetricsEnums {

	NEWUSER("newuser", "新增用户数"), 
	SESSION("session", "启动次数");

	private String code;

	private String message;

	private MetricsEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String getCode(String message) {
		MetricsEnums[] values = MetricsEnums.values();
		for (MetricsEnums type : values) {
			if (StringUtils.equalsIgnoreCase(message, type.getMessage())) {
				return type.getCode();
			}
		}
		return null;
	}
}
