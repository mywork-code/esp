package com.apass.esp.domain.vo;

public class DataAppuserAnalysisDto {
	
	private Long Id;
	
	private String hourly;
	
	private Byte platformids;
	
	private Byte type;
	
	private String daily;
	
	private String newuser;
	
	private String session;
	
	private String activeuser;
	
	private String versionupuser;
	
	private String wau;
	
	private String mau;
	
	private String totaluser;
	
	private String bounceuser;
	
	private String sessionlength;
	
	private String avgsessionlength;
	
	private String registeruser;
	
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getPlatformids() {
		return platformids;
	}

	public void setPlatformids(Byte platformids) {
		this.platformids = platformids;
	}

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

	public String getActiveuser() {
		return activeuser;
	}

	public void setActiveuser(String activeuser) {
		this.activeuser = activeuser;
	}

	public String getVersionupuser() {
		return versionupuser;
	}

	public void setVersionupuser(String versionupuser) {
		this.versionupuser = versionupuser;
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

	public String getTotaluser() {
		return totaluser;
	}

	public void setTotaluser(String totaluser) {
		this.totaluser = totaluser;
	}

	public String getBounceuser() {
		return bounceuser;
	}

	public void setBounceuser(String bounceuser) {
		this.bounceuser = bounceuser;
	}

	public String getSessionlength() {
		return sessionlength;
	}

	public void setSessionlength(String sessionlength) {
		this.sessionlength = sessionlength;
	}

	public String getAvgsessionlength() {
		return avgsessionlength;
	}

	public void setAvgsessionlength(String avgsessionlength) {
		this.avgsessionlength = avgsessionlength;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	public String getRegisteruser() {
		return registeruser;
	}

	public void setRegisteruser(String registeruser) {
		this.registeruser = registeruser;
	}
	
}
