package com.apass.esp.noauth.home;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.enums.CategoryLevel;
import com.apass.esp.domain.vo.CategoryVo;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.web.activity.RegisterInfoController;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.EncodeUtils;
//@Controller
//@RequestMapping("/v1/home/category")
@Path("")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShopHomeCategoryController {
    private static final Logger logger =  LoggerFactory.getLogger(ShopHomeCategoryController.class);
    @Autowired
    private CategoryInfoService categoryInfoService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
    private BannerInfoService bannerService;
    /**
   	 * 1. 首页初始化 加载类目信息
   	 */
//    @ResponseBody
//    @RequestMapping(value="/init",method=RequestMethod.POST)
    @POST
    @Path("/v1/home/category/init")
   	public Response indexCategoryInit() {
   		try {
             Map<String, Object> returnMap = new HashMap<String, Object>();
             List<CategoryVo> CategoryVos=categoryInfoService.selectCategoryVoList(Long.parseLong(CategoryLevel.CATEGORY_LEVEL1.getCode()));
             returnMap.put("categorys", CategoryVos);
   		     return Response.successResponse(returnMap);
		} catch (Exception e) {
			logger.error("indexCategoryInit fail", e);
			logger.error("首页类目信息加载失败");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
   	}
    @POST
    @Path("/v2/home/category/init")
   	public Response indexCategoryInitJd() {
   		try {
             Map<String, Object> returnMap = new HashMap<String, Object>();
             List<CategoryVo> CategoryVos=categoryInfoService.selectCategoryVoListJd(Long.parseLong(CategoryLevel.CATEGORY_LEVEL1.getCode()));
             returnMap.put("categorys", CategoryVos);
   		     return Response.successResponse(returnMap);
		} catch (Exception e) {
			logger.error("indexCategoryInit fail", e);
			logger.error("首页类目信息加载失败");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
   	}
    /**
   	 * 2. 查看当前类目下的全部商品列表
   	 */
//   	@ResponseBody
//   	@RequestMapping(value = "/loanGoodsList",method = RequestMethod.POST)
    @POST
    @Path("/v1/home/category/loanGoodsList")
   	public Response loanGoodsListByCategoryId(@RequestBody Map<String, Object> paramMap) {
   		try {
   			 String categoryId = CommonUtils.getValue(paramMap, "categoryId");//类目Id
   			 String page = CommonUtils.getValue(paramMap, "page");
   			 String rows = CommonUtils.getValue(paramMap, "rows");
   			 if(StringUtils.isEmpty(categoryId)){
   				 logger.error("类目id不能空！");
   				 return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
   			 }
   			 Map<String, Object> returnMap = new HashMap<String, Object>();
   			 
   			 GoodsBasicInfoEntity  goodsInfoEntity=new GoodsBasicInfoEntity();
   			 goodsInfoEntity.setCategoryId1(Long.parseLong(categoryId));//设置1级类目Id
   			 Pagination<GoodsBasicInfoEntity> goodsPageList= goodsService.loadGoodsByCategoryId(goodsInfoEntity,page, rows);
   			 List<GoodsBasicInfoEntity> goodsList =goodsPageList.getDataList();
   		      for (GoodsBasicInfoEntity goodsInfo : goodsList) {
               if (null!=goodsInfo.getGoodId() && null!=goodsInfo.getGoodsStockId()) {
                   BigDecimal price = commonService.calculateGoodsPrice( goodsInfo.getGoodId(),goodsInfo.getGoodsStockId());
                   goodsInfo.setGoodsPrice(price);
                   //电商3期511 20170517 根据商品Id查询所有商品库存中市场价格最高的商品的市场价
                   Long marketPrice=goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goodsInfo.getGoodId());
                   goodsInfo.setMarketPrice(new BigDecimal(marketPrice));
                   
                   String logoUrl = goodsInfo.getGoodsLogoUrl();
                   String siftUrl = goodsInfo.getGoodsSiftUrl();
                   goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
                   goodsInfo.setGoodsSiftUrlNew(imageService.getImageUrl(siftUrl));
                   goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
                   goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(siftUrl));
                  }
               }
   		      returnMap.put("totalCount", goodsPageList.getTotalCount());
   		      returnMap.put("goodsList", goodsList);
   		      
   		       List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT.getIdentify());
               for(BannerInfoEntity banner : banners){
                banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));

                banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
              }
             returnMap.put("banners", banners);
   			 return Response.successResponse(returnMap);
		} catch (Exception e) {
			logger.error("indexCategoryInit fail", e);
			logger.error("首页类目信息加载失败");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
		}
   	}
}
