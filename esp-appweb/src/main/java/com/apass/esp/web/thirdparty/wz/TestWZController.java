package com.apass.esp.web.thirdparty.wz;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.third.party.weizhi.client.WeiZhiTokenClient;
import com.apass.esp.third.party.weizhi.entity.TokenEntity;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * @author zengqingshan
 */
@Controller
@RequestMapping("wz")
public class TestWZController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWZController.class);

    private static final String WEIZHI_TOKEN = "WEIZHI_TOKEN";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String EXPIRED_TIME = "EXPIRED_TIME";

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private WeiZhiTokenClient weiZhiTokenClient;
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	
	@ResponseBody
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public Response testGetToken() {
		try {
			TokenEntity token = weiZhiTokenClient.getToken();
			if(null !=token){
				//将token和其有效期存放到redies中
				cacheManager.set(WEIZHI_TOKEN+":"+ACCESS_TOKEN,token.getAccess_token());
				cacheManager.set(WEIZHI_TOKEN+":"+EXPIRED_TIME,token.getExpired_time());
				return Response.success("微知token获取成功！");
			}
		} catch (Exception e) {
			LOGGER.error("微知token获取出错！");
		}
		return Response.fail("微知token获取失败！");
	}
	 /**
     * 商品详情
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/productDetailQuery", method = RequestMethod.POST)
    public Response productDetailQuery(@RequestBody Map<String, Object> paramMap) {
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		try {
			String wzProductDetail = weiZhiProductApiClient.getWeiZhiProductDetail(sku);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return Response.success("1", "");
    }
}
