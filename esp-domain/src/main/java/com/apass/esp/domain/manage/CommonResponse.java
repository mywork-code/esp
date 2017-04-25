package com.apass.esp.domain.manage;

public class CommonResponse {
	 /**
     * Static
     */
    private String status;
    /**
     * message
     */
    private String msg;
    /**
     * Response Data
     */
    private String data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
    
}
