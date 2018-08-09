package com.apass.esp.domain.entity.mail;

import java.io.InputStream;

/**
 * 邮件附件信息
 * 
 * @author admin
 *
 */
public class FileBodyInfo {

	private String fileName;

	private InputStream is;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

}
