package com.apass.esp.domain.enums;

/**
 * 说明：商品类型枚举，1表示正常，2表示精选
 * 
 * @author xiaohai
 * @version 1.0
 * @date 2016年12月23日
 */
public enum GoodsIsDelete {

	GOOD_NODELETE("01", "有效，未删除，可查询出来"),

	GOOD_DELETE("0", "无效，已删除，不能查询出来");

	private String code;

	private String message;

	private GoodsIsDelete(String code, String message) {
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
}
