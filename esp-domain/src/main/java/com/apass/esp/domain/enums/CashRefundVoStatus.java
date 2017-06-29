package com.apass.esp.domain.enums;

public enum CashRefundVoStatus {

	CASHREFUND_STATUS0("0", "退款"),
	
	CASHREFUND_STATUS1("1", "处理中"),

	CASHREFUND_STATUS2("2", "退款中"),
	
	CASHREFUND_STATUS4("4", "退款成功"),
	
	CASHREFUND_STATUS3("3", "取消退款");

	private String code;

	private String message;

	private CashRefundVoStatus(String code, String message) {
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
