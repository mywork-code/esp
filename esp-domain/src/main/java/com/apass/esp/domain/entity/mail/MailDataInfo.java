package com.apass.esp.domain.entity.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 发送邮件信息
 * 
 * @author admin
 *
 */
public class MailDataInfo {

	/**
	 * 发件人信息
	 */
	private MailPersonalInfo sender;

	/**
	 * 邮件主题
	 */
	private String subject;

	/**
	 * 收件人
	 */
	private List<MailPersonalInfo> toMails = new ArrayList<MailPersonalInfo>();

	/**
	 * 抄送人
	 */
	private List<MailPersonalInfo> ccMails = new ArrayList<MailPersonalInfo>();

	/**
	 * 邮件正文内容
	 */
	private String content;

	/**
	 * 附件
	 */
	private List<FileBodyInfo> fileBodys = new ArrayList<FileBodyInfo>();

	public MailPersonalInfo getSender() {
		return sender;
	}

	public void setSender(MailPersonalInfo sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<MailPersonalInfo> getToMails() {
		return toMails;
	}

	public void addToMails(MailPersonalInfo toMail) {
		if (toMail == null) {
			return;
		}
		toMails.add(toMail);
	}

	public List<MailPersonalInfo> getCcMails() {
		return ccMails;
	}

	public void addCcMail(MailPersonalInfo ccMail) {
		if (ccMail == null) {
			return;
		}
		ccMails.add(ccMail);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<FileBodyInfo> getFileBodys() {
		return fileBodys;
	}

	public void addFileBody(FileBodyInfo fileBody) {
		if (fileBody == null) {
			return;
		}
		fileBodys.add(fileBody);
	}

}
