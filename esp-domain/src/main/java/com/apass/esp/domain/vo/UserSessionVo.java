package com.apass.esp.domain.vo;

public class UserSessionVo {
	
	private Long Id;
	
	private String hourly;
	
	private String newuser;
	
	private String session;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getHourly() {
		return hourly;
	}

	public void setHourly(String hourly) {
		this.hourly = hourly;
	}

	public String getNewuser() {
		return newuser;
	}

	public void setNewuser(String newuser) {
		this.newuser = newuser;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
}
