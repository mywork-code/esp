package com.apass.esp.web.thirdparty.wz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.third.party.weizhi.client.WeiZhiTokenClient;
import com.apass.esp.third.party.weizhi.entity.TokenEntity;
import com.apass.gfb.framework.cache.CacheManager;
/**
 * @author zengqingshan
 */
@Controller
@RequestMapping("wz")
public class TestWZController {
    private static final String WEIZHI_TOKEN = "WEIZHI_TOKEN";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String EXPIRED_TIME = "EXPIRED_TIME";

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private WeiZhiTokenClient weiZhiTokenClient;
	
	@ResponseBody
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public Response testGetToken() {
		TokenEntity token = weiZhiTokenClient.getToken();
		if(null !=token){
			//将token和其有效期存放到redies中
			cacheManager.set(WEIZHI_TOKEN+":"+ACCESS_TOKEN,token.getAccess_token());
			cacheManager.set(WEIZHI_TOKEN+":"+EXPIRED_TIME,token.getExpired_time());
			return Response.success("微知token获取成功！");
		}
		return Response.fail("微知token获取失败！");
	}
}
