package com.apass.esp.noauth.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.CategoryLevel;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.web.activity.RegisterInfoController;
import com.apass.gfb.framework.utils.CommonUtils;

@RestController
@RequestMapping("/v1/home/category")
public class ShopHomeCategoryController {
    private static final Logger logger =  LoggerFactory.getLogger(RegisterInfoController.class);
    @Autowired
    private CategoryInfoService categoryInfoService;
    /**
   	 * 1. 首页初始化 加载类目信息
   	 */
   	@RequestMapping(value = "/init",method = RequestMethod.POST)
   	public Response indexCategoryInit(@RequestBody Map<String, Object> paramMap) {
   		try {
             Map<String, Object> returnMap = new HashMap<String, Object>();
             List<CategoryVo> CategoryVos=categoryInfoService.selectCategoryVoList(Long.parseLong(CategoryLevel.CATEGORY_LEVEL1.getCode()));
             returnMap.put("categorys", CategoryVos);
   		     return Response.successResponse(returnMap);
		} catch (Exception e) {
			logger.error("indexCategoryInit fail", e);
            return Response.fail("首页类目信息加载失败");
		}
   	}
    /**
   	 * 2. 查看当前类目下的全部商品列表
   	 */
   	@RequestMapping(value = "/loanGoodsList",method = RequestMethod.POST)
   	public Response loanGoodsListByCategoryId(@RequestBody Map<String, Object> paramMap) {
   		try {
   			 String categoryId = CommonUtils.getValue(paramMap, "categoryId");//类目Id
   			 String page = CommonUtils.getValue(paramMap, "page");
   			 String rows = CommonUtils.getValue(paramMap, "rows");
   			 
             Map<String, Object> returnMap = new HashMap<String, Object>();
             
   		     return Response.successResponse(returnMap);
		} catch (Exception e) {
			logger.error("indexCategoryInit fail", e);
            return Response.fail("首页类目信息加载失败");
		}
   	}
}
