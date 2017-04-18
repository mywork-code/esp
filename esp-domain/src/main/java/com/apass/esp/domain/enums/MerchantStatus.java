package com.apass.esp.domain.enums;

/**
 * 
 * @description 商品状态枚举
 * 
 * @author chenbo
 * @version $Id: GoodStatus.java, v 0.1 2016年12月19日 下午3:15:10 chenbo Exp $
 */
public enum MerchantStatus {

	MERCHANT_VALID("1", "正常"),

	MERCHANT_CHECK("0", "待审核"), // temp表有该状态，主商户表无此状态

	MERCHANT_INVALID("-1", "无效");

	private String code;

	private String message;

	private MerchantStatus(String code, String message) {
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
