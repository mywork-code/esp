package com.apass.esp.web.thirdparty.wz;

import java.util.List;
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
import com.apass.esp.third.party.weizhi.entity.Category;
import com.apass.esp.third.party.weizhi.entity.WzSkuPicture;
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
    /**
     * 查询商品是否上下架
     */
    @RequestMapping(value = "/jdProductStateQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response jdProductStateQuery(@RequestBody Map<String, Object> paramMap){
		String sku = CommonUtils.getValue(paramMap, "sku");// 商品号
		Boolean result;
		try {
			result = weiZhiProductService.getWeiZhiProductSkuState(sku);
		} catch (Exception e) {
			return Response.fail("查询商品是否上下架失败！");
		}
    	 return Response.success("查询商品是否上下架成功！",result);
    }
    /**
     * 查询一级分类列表信息接口
     */
    @RequestMapping(value = "/getWeiZhiFirstCategorys", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiFirstCategorys(@RequestBody Map<String, Object> paramMap){
		try {
			List<Category> categorys=weiZhiProductService.getWeiZhiFirstCategorys();
		    System.out.println(categorys);
		} catch (Exception e) {
			return Response.fail("查询一级分类列表信息接口失败！");
		}
		return Response.success("查询一级分类列表信息接口成功！");
    }
    /**
     * 查询二级分类列表信息接口
     */
    @RequestMapping(value = "/getWeiZhiSecondCategorys", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiSecondCategorys(@RequestBody Map<String, Object> paramMap){
		try {
			List<Category> categorys=weiZhiProductService.getWeiZhiSecondCategorys();
			System.out.println(categorys);
		} catch (Exception e) {
			return Response.fail("查询二级分类列表信息接口失败！");
		}
    	 return Response.success("查询二级分类列表信息接口成功！");
    }
    /**
     * 查询三级分类列表信息接口
     */
    @RequestMapping(value = "/getWeiZhiThirdCategorys", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiThirdCategorys(@RequestBody Map<String, Object> paramMap){
		try {
			List<Category> categorys=weiZhiProductService.getWeiZhiThirdCategorys();
			System.out.println(categorys);
		} catch (Exception e) {
			return Response.fail("查询三级分类列表信息接口失败！");
		}
    	 return Response.success("查询三级分类列表信息接口成功！");
    }
    /**
     *获取分类商品编号接口
     */
    @RequestMapping(value = "/getWeiZhiGetSku", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiGetSku(@RequestBody Map<String, Object> paramMap){
		try {
			List<String> categorys=weiZhiProductService.getWeiZhiGetSku();
			System.out.println(categorys);
		} catch (Exception e) {
			return Response.fail("获取分类商品编号接口失败！");
		}
    	 return Response.success("获取分类商品编号接口成功！");
    }
    /**
     *获取所有图片信息
     */
    @RequestMapping(value = "/getWeiZhiProductSkuImage", method = RequestMethod.POST)
    @ResponseBody
    public Response getWeiZhiProductSkuImage(@RequestBody Map<String, Object> paramMap){
		try {
			List<WzSkuPicture> wzSkuPictureList=weiZhiProductService.getWeiZhiProductSkuImage(1593516+","+1686504);
			System.out.println(wzSkuPictureList);
		} catch (Exception e) {
			return Response.fail("获取所有图片信息失败！");
		}
    	 return Response.success("获取所有图片信息成功！");
    }
}
