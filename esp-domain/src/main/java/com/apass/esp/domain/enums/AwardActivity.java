package com.apass.esp.domain.enums;

public enum AwardActivity {
	EFFECTIVE("1", "有效"),

	UNEFFECTIVE("0", "无效"),

	GAIN("0", "获得"),

	WITHDRAW("1", "提现"),

	SUCCESS("0", "成功"),

	FAIL("1", "失败"),

	PROCESSING("2", "处理中"),

	PERSONAL("0", "个人"),

	ORGANIZATION("1", "组织");

	private AwardActivity(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;

	private String message;

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
