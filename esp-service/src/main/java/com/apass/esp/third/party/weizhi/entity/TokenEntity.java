package com.apass.esp.third.party.weizhi.entity;

public class TokenEntity {
	private String  client_id;
	private String  access_token;
	private String  expired_time;
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpired_time() {
		return expired_time;
	}
	public void setExpired_time(String expired_time) {
		this.expired_time = expired_time;
	}
	
}
