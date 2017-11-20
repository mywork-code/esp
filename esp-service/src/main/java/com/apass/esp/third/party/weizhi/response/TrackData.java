package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;

public class TrackData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgTime;
	private String content;
	private String operator;
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public TrackData(String msgTime, String content, String operator) {
		super();
		this.msgTime = msgTime;
		this.content = content;
		this.operator = operator;
	}
	public TrackData() {
		super();
	}
	
}
