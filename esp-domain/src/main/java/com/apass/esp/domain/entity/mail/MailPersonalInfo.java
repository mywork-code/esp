package com.apass.esp.domain.entity.mail;

/**
 * 邮件发件人、收件人信息
 * 
 * @author admin
 *
 */
public class MailPersonalInfo {

	/**
	 * 邮箱地址
	 */
	private String mailAddress;

	/**
	 * 邮箱姓名
	 */
	private String mailName;

	/**
	 * 邮箱密码
	 */
	private String mailPassword;

	public MailPersonalInfo(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public MailPersonalInfo(String mailAddress, String mailName) {
		this.mailAddress = mailAddress;
		this.mailName = mailName;
	}

	public MailPersonalInfo(String mailAddress, String mailName, String mailPassword) {
		this.mailAddress = mailAddress;
		this.mailName = mailName;
		this.mailPassword = mailPassword;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

}
