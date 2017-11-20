package com.apass.esp.third.party.weizhi.entity;

import java.util.List;

import com.apass.esp.domain.entity.jd.JdSimilarSku;

public class WZJdSimilarSku {
	private List<JdSimilarSku> result;
	private Boolean success;
	private String resultCode;
	private String resultMessage;
	public List<JdSimilarSku> getResult() {
		return result;
	}
	public void setResult(List<JdSimilarSku> result) {
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
