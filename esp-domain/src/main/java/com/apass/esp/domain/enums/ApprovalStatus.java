package com.apass.esp.domain.enums;

/**
 * 
 * @description 审批状态枚举
 * 
 * @author chenbo
 * @version $Id: GoodStatus.java, v 0.1 2017年1月17日 下午3:15:10 chenbo Exp $
 */
public enum ApprovalStatus {

	APPROVAL_VALID("1", "有效"),

	APPROVAL_CHECK("0", "待审核"),

	APPROVAL_INVALID("-1", "无效");

	private String code;

	private String message;

	private ApprovalStatus(String code, String message) {
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
