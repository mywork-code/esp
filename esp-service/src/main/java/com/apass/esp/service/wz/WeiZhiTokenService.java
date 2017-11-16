package com.apass.esp.service.wz;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.third.party.weizhi.client.WeiZhiConstants;
import com.apass.esp.third.party.weizhi.client.WeiZhiTokenClient;
import com.apass.esp.third.party.weizhi.entity.TokenEntity;
import com.apass.gfb.framework.cache.CacheManager;

@Service
public class WeiZhiTokenService {
	@Autowired
	private WeiZhiTokenClient weiZhiTokenClient;
	@Autowired
	private CacheManager cacheManager;

	/**
	 * 从微知获取Token
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getToken() throws Exception {
		TokenEntity token = weiZhiTokenClient.getToken();
		if (null != token && StringUtils.isNotEmpty(token.getAccess_token())) {
			// 将token和其有效期存放到redies中
			cacheManager.set(WeiZhiConstants.WEIZHI_TOKEN + ":" + WeiZhiConstants.ACCESS_TOKEN,
					token.getAccess_token());
			cacheManager.set(WeiZhiConstants.WEIZHI_TOKEN + ":" + WeiZhiConstants.EXPIRED_TIME,
					token.getExpired_time());
			// 设置Token的有效期
			cacheManager.expire(WeiZhiConstants.WEIZHI_TOKEN + ":" + WeiZhiConstants.ACCESS_TOKEN,
					WeiZhiConstants.TOKEN_EXPIRED);
			return "success";
		}
		return "fail";
	}

	/**
	 * 从Redis获取token
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTokenFromRedis() throws Exception {
		String token = "";
		token = cacheManager.get(WeiZhiConstants.WEIZHI_TOKEN + ":" + WeiZhiConstants.ACCESS_TOKEN);
		if (null == token || StringUtils.isBlank(token)) {
			getToken();
			token = cacheManager.get(WeiZhiConstants.WEIZHI_TOKEN + ":" + WeiZhiConstants.ACCESS_TOKEN);
		}
		return token;
	}
}
