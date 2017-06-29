package com.apass.esp.domain.enums;

/**
 * 
 * 交易类型
 * 
 * @author zhanwendong
 *
 */
public enum TxnTypeCode {

	SF_CODE("T01", "首付"),

	XYZF_CODE("T02", "信用支付"),

	XJTX_CODE("T03", "现金提现"),

	XFFQ_CODE("T04", "消费分期"),
	
	KQEZF_CODE("T05", "卡全额支付"),
	
	ALIPAY_CODE("T10","支付宝全额支付"),
	
	ALIPAY_SF_CODE("T11","支付宝首付");

	private String code;

	private String message;

	private TxnTypeCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
