package com.apass.gfb.framework.jwt.domains;

import java.util.Calendar;

/**
 * 
 * @description Json Token
 *
 * @author lixining
 * @version $Id: TokenInfo.java, v 0.1 2015年10月28日 上午10:53:42 lixining Exp $
 */
public class TokenInfo {
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * Token生成时间
	 */
	private Long issued;
	/**
	 * Token过期时间
	 */
	private Long expires;

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

	public Long getIssued() {
		return issued;
	}

	public void setIssued(Long issued) {
		this.issued = issued;
	}

	public Long getExpires() {
		return expires;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}

	public boolean isExpire() {
		Calendar calendar = Calendar.getInstance();
		return expires <= calendar.getTimeInMillis() / 1000;
	}
}
