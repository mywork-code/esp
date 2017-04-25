package com.apass.esp.domain.manage;

public class RegisterInfoResponse {
	private String userId;
	private String mobile;
	private String inviteUserId;
	private String inviteMobile;
	private String falge;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInviteUserId() {
		return inviteUserId;
	}
	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	public String getInviteMobile() {
		return inviteMobile;
	}
	public void setInviteMobile(String inviteMobile) {
		this.inviteMobile = inviteMobile;
	}
	public String getFalge() {
		return falge;
	}
	public void setFalge(String falge) {
		this.falge = falge;
	}
}
