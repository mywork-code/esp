package com.apass.esp.domain.vo;

public class UserSessionVo {
	
	private Long Id;
	
	private String hourly;
	
	private String daily;
	
	private String newuser;
	
	private String session;
	
	private String activeusers;
	
	private String versionupusers;
	
	private String waus;
	
	private String maus;
	
	private String totalusers;
	
	private String bounceusers;
	
	private String sessionlengths;
	
	private String avgsessionlengths;
	
	
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

	public String getActiveusers() {
		return activeusers;
	}

	public void setActiveusers(String activeusers) {
		this.activeusers = activeusers;
	}

	public String getVersionupusers() {
		return versionupusers;
	}

	public void setVersionupusers(String versionupusers) {
		this.versionupusers = versionupusers;
	}

	public String getWaus() {
		return waus;
	}

	public void setWaus(String waus) {
		this.waus = waus;
	}

	public String getMaus() {
		return maus;
	}

	public void setMaus(String maus) {
		this.maus = maus;
	}

	public String getTotalusers() {
		return totalusers;
	}

	public void setTotalusers(String totalusers) {
		this.totalusers = totalusers;
	}

	public String getBounceusers() {
		return bounceusers;
	}

	public void setBounceusers(String bounceusers) {
		this.bounceusers = bounceusers;
	}

	public String getSessionlengths() {
		return sessionlengths;
	}

	public void setSessionlengths(String sessionlengths) {
		this.sessionlengths = sessionlengths;
	}

	public String getAvgsessionlengths() {
		return avgsessionlengths;
	}

	public void setAvgsessionlengths(String avgsessionlengths) {
		this.avgsessionlengths = avgsessionlengths;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}
	
}
