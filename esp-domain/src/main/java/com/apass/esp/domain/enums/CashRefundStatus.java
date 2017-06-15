package com.apass.esp.domain.enums;

public enum CashRefundStatus {

	CASHREFUND_STATUS1("1", "退款提交，等待商家审核"),

	CASHREFUND_STATUS2("2", "同意退款"),

	CASHREFUND_STATUS3("3", "取消退款"),
	
	CASHREFUND_STATUS4("4", "退款成功"),
	
	CASHREFUND_STATUS5("5", "退款失败");

	private String code;

	private String message;

	private CashRefundStatus(String code, String message) {
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
