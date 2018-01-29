package com.apass.esp.domain.vo;

public class DataAppuserAnalysisVo {


	private String daily;//日期
	
	private String newuser;//新增用户
	
	private String session;//启动时长
	
	private String sessionAvg;//平均使用时长
	
	
	private String activeuser;//日活跃用户
	
	private String wau;//周活跃用户
	
	private String mau;//月活跃用户

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
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

	public String getSessionAvg() {
		return sessionAvg;
	}

	public void setSessionAvg(String sessionAvg) {
		this.sessionAvg = sessionAvg;
	}

	public String getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(String activeuser) {
		this.activeuser = activeuser;
	}

	public String getWau() {
		return wau;
	}

	public void setWau(String wau) {
		this.wau = wau;
	}

	public String getMau() {
		return mau;
	}

	public void setMau(String mau) {
		this.mau = mau;
	}
	
}
