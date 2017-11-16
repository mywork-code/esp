package com.apass.esp.web.thirdparty.wz;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.service.wz.WeiZhiProductService;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * @author zengqingshan
 */
@Controller
@RequestMapping("wz")
public class TestWZController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWZController.class);
	@Autowired
	private WeiZhiTokenService weiZhiTokenService;
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	@Autowired
	private WeiZhiProductService weiZhiProductService;
	
	@ResponseBody
	@RequestMapping(value = "/getToken", method = RequestMethod.GET)
	public Response testGetToken() {
		try {
			String token = weiZhiTokenService.getToken();
			if(StringUtils.equals("success", token)){
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
			Product wzProductDetail = weiZhiProductService.getWeiZhiProductDetail(sku);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return Response.success("1", "微知获取商品详情成功！");
    }
}
