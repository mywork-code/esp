package com.apass.esp.domain.enums;

public enum MonitorFlag {


	MONITOR_FLAG0("0", "非配置数据"),

	MONITOR_FLAG1("1", "配置数据");

	private String code;

	private String message;

	private MonitorFlag(String code, String message) {
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
