package com.apass.gfb.framework.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.gfb.framework.jwt.core.JsonTokenHelper;
import com.apass.gfb.framework.jwt.domains.TokenInfo;
/**
 * token相关的操作
 * @author jiangxiangwei
 */
@Component
public class TokenManager {

	@Autowired
    private JsonTokenHelper jsonTokenHelper;

	/**
	 * 生成token
	 * @param userId appId
	 * @param mobile 手机号
	 * @param tokenExpiresSpace
	 * @return
	 */
	public String createToken(String userId, String mobile, Long tokenExpiresSpace) {
		return jsonTokenHelper.createJsonWebToken(userId, mobile, tokenExpiresSpace);
	}
	
	/**
	 * 校验token是否失效
	 * @param userId appId
	 * @param mobile 手机号
	 * @param token
	 * @return
	 */
	public Boolean verifyToken(String userId, String mobile, String token) {
		// flag：true表示失效；false:没有失效
		Boolean isInvalid = false;
		TokenInfo tokenInfo = jsonTokenHelper.verifyToken(token);
		// 1.mobile的校验
		if (!tokenInfo.getMobile().equals(mobile)) {
			isInvalid = true;
			return isInvalid;
		}
		// 2.是否过期
		if (tokenInfo.isExpire()) {
			isInvalid = true;
			return isInvalid;
		}
		return isInvalid;
	}
	
}