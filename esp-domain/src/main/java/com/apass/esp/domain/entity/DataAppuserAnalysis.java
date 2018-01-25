package com.apass.esp.domain.entity;

import java.util.Date;

public class DataAppuserAnalysis {
    private Long id;

    private Date createdTime;

    private Date updatedTime;

    private String isDelete;

    private String txnId;

    private Byte type;

    private Byte platformids;

    private String newuser;
    private String registeruser;
    private String activeuser;
    private String versionupuser;

    private String wau;

    private String mau;

    private String totaluser;

    private String bounceuser;

    private String session;

    private String sessionlength;

    private String avgsessionlength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

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

    public String getNewuser() {
        return newuser;
    }
    public void setNewuser(String newuser) {
        this.newuser = newuser;
    }
    public String getRegisteruser() {
		return registeruser;
	}
	public void setRegisteruser(String registeruser) {
		this.registeruser = registeruser;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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
}