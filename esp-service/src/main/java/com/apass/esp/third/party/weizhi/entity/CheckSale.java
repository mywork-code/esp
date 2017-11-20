package com.apass.esp.third.party.weizhi.entity;

import java.util.List;

public class CheckSale {
	private List<WZCheckSale> result;
	private Boolean success;
	private String resultCode;
	private String resultMessage;
	public List<WZCheckSale> getResult() {
		return result;
	}
	public void setResult(List<WZCheckSale> result) {
		this.result = result;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}
