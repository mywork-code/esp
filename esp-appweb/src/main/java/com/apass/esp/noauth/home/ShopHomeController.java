package com.apass.esp.noauth.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.apass.esp.service.common.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.cart.ShoppingCartService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.EncodeUtils;

/**
 * 首页
 * @description 
 *
 * @author liuming
 * @version $Id: ShopHomeController.java, v 0.1 2016年12月19日 下午3:33:14 liuming Exp $
 */
@Path("/shop/index")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShopHomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopHomeController.class);
    
    @Autowired
    private GoodsService goodService;

    @Autowired
    private BannerInfoService bannerService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private CategoryInfoService categoryInfoService;
    
    @Autowired
    private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
    private GoodsService goodsService;
    
    /**
     *  首页初始化 加载banner和精品商品 
     * @return
     */
    @POST
    @Path("/init")
    public Response indexInit() {
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            List<BannerInfoEntity> banners = bannerService.loadIndexBanners(ConstantsUtils.BANNERTYPEINDEX);
            for(BannerInfoEntity banner : banners){
                banner.setActivityUrl(banner.getActivityUrl());

                banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
            }
            
            List<GoodsBasicInfoEntity> recommendGoods = goodService.loadRecommendGoods();
            returnMap.put("banners", banners);
            returnMap.put("recommendGoods", recommendGoods);
            
            for (GoodsBasicInfoEntity goods : recommendGoods) {
                BigDecimal price = commonService.calculateGoodsPrice(goods.getGoodId() ,goods.getGoodsStockId());
                goods.setGoodsPrice(price);
                //电商3期511 20170517 根据商品Id查询所有商品库存中市场价格最高的商品的市场价
                Long marketPrice=goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goods.getGoodId());
                goods.setMarketPrice(new BigDecimal(marketPrice));
                
                goods.setGoodsLogoUrlNew(imageService.getImageUrl(goods.getGoodsLogoUrl()));
                goods.setGoodsSiftUrlNew(imageService.getImageUrl(goods.getGoodsSiftUrl()));
                goods.setGoodsLogoUrl(EncodeUtils.base64Encode(goods.getGoodsLogoUrl()));
                goods.setGoodsSiftUrl(EncodeUtils.base64Encode(goods.getGoodsSiftUrl()));
            }
            return Response.successResponse(returnMap);
        } catch (Exception e) {
            LOGGER.error("indexInit fail", e);
            LOGGER.error("首页加载失败");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
        }
    }
    
    /**
     * 加载商品列表 
     * 
     * @return
     */
    @SuppressWarnings("null")
	@POST
    @Path("/loadGoodsList")
    public Response loadGoodsList(Map<String, Object> paramMap){
        try {
              Map<String, Object> returnMap = new HashMap<String, Object>();
              String flage=CommonUtils.getValue(paramMap, "flage");//标记是精选还是类目
              List<GoodsBasicInfoEntity> goodsList=null;
              if(null !=flage && flage.equals("category")){
            	  String categoryId = CommonUtils.getValue(paramMap, "categoryId");//类目Id
        		  String page = CommonUtils.getValue(paramMap, "page");
        		  String rows = CommonUtils.getValue(paramMap, "rows");
        		  if(StringUtils.isEmpty(categoryId)){
        			  LOGGER.error("类目id不能空！");
           			  return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
           		  }
       			 GoodsBasicInfoEntity  goodsInfoEntity=new GoodsBasicInfoEntity();
       			 goodsInfoEntity.setCategoryId1(Long.parseLong(categoryId));//设置1级类目Id
       			 Pagination<GoodsBasicInfoEntity> goodsPageList= goodsService.loadGoodsByCategoryId(goodsInfoEntity,page, rows);
       			 goodsList =goodsPageList.getDataList();
      		     returnMap.put("totalCount", goodsPageList.getTotalCount());
      		     //设置类目张的banner
      		     List<BannerInfoEntity> banners=new ArrayList<BannerInfoEntity>();
      		     BannerInfoEntity  bity=new BannerInfoEntity();
      		     
                 Category category=categoryInfoService.selectNameById(Long.parseLong(categoryId));
                 if("1".equals(String.valueOf(category.getSortOrder()))){//家用电器banner图
                	 bity.setBannerImgUrlNew("http://espapp.sit.apass.cn/static/eshop/other/1496334905399.png");
                	 bity.setBannerImgUrl("http://espapp.sit.apass.cn/static/eshop/other/1496334905399.png");
                 }else if("2".equals(String.valueOf(category.getSortOrder()))){//家居百货banner图
                 	 bity.setBannerImgUrlNew("http://espapp.sit.apass.cn/static/eshop/other/1496334888081.png");
                	 bity.setBannerImgUrl("http://espapp.sit.apass.cn/static/eshop/other/1496334888081.png");
                 }else if("3".equals(String.valueOf(category.getSortOrder()))){//美妆生活banner图
                 	 bity.setBannerImgUrlNew("http://espapp.sit.apass.cn/static/eshop/other/1496401288300.png");
                	 bity.setBannerImgUrl("http://espapp.sit.apass.cn/static/eshop/other/1496401288300.png");
                 }
                 banners.add(bity);
                 returnMap.put("banners", banners);
              }else{
//            	  goodsList = goodService.loadGoodsList();//加载所以商品
            	  goodsList = goodService.loadRecommendGoods();//加载精选商品
            	  
            	    List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT.getIdentify());
                    for(BannerInfoEntity banner : banners){
//                        banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));
                        banner.setActivityUrl(banner.getActivityUrl());

                        banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                        banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
                    }
                    returnMap.put("banners", banners);
              }
              
               for (GoodsBasicInfoEntity goodsInfo : goodsList) {
                if (null!=goodsInfo.getGoodId() && null!=goodsInfo.getGoodsStockId()) {
                    BigDecimal price = commonService.calculateGoodsPrice( goodsInfo.getGoodId(),goodsInfo.getGoodsStockId());
                    goodsInfo.setGoodsPrice(price);
                    //电商3期511 20170517 根据商品Id查询所有商品库存中市场价格最高的商品的市场价
                    Long marketPrice=goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goodsInfo.getGoodId());
                    goodsInfo.setMarketPrice(new BigDecimal(marketPrice));
                    
                    String logoUrl = goodsInfo.getGoodsLogoUrl();
                    String siftUrl = goodsInfo.getGoodsSiftUrl();
                    if(null !=logoUrl){
                        goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
                        goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
                    }else{
                        goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(siftUrl));
                        goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(siftUrl));
                    }
                    goodsInfo.setGoodsSiftUrlNew(imageService.getImageUrl(siftUrl));
                    goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(siftUrl));
                }
            }
            returnMap.put("goodsList", goodsList);
            return Response.successResponse(returnMap);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsList fail", e);
            LOGGER.error("加载商品列表失败！");
            return Response.fail(BusinessErrorCode.LOAD_INFO_FAILED);
        }
    }
    
    /**
     * 获取商品详细信息 基本信息+详细信息(规格 价格 剩余量)
     * 
     * @return
     */
    @POST
    @Path("/loadDetailInfoById")
    public Response loadGoodsBasicInfo(Map<String, Object> paramMap){
        try{
            Map<String,Object> returnMap = new HashMap<>();
            Long goodsId = CommonUtils.getLong(paramMap,"goodsId");
            String userId = CommonUtils.getValue(paramMap, "userId");
            if(null==goodsId){
            	LOGGER.error("商品号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            if (!StringUtils.isEmpty(userId)) {
                int amountInCart = shoppingCartService.getNumOfTypeInCart(userId);
                returnMap.put("amountInCart", amountInCart);
            }
            goodService.loadGoodsBasicInfoById(goodsId,returnMap);
            return Response.success("加载成功", returnMap);
        } catch (BusinessException e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            return Response.fail(BusinessErrorCode.GET_INFO_FAILED);
        }
        catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            LOGGER.error("获取商品基本信息失败");
            return Response.fail(BusinessErrorCode.GET_INFO_FAILED);
        }
    }

    /**
     * 根据商品id获取商品规格详情信息(商品库存表)
     * 
     * @return
     */
    @POST
    @Path("/loadGoodsStockByGoodsId")
    public Response loadGoodsStockInfo(Map<String, Object> paramMap){
        try{
            Map<String,Object> returnMap = new HashMap<>();
            Long goodsId = CommonUtils.getLong(paramMap,"goodsId");
            if(null==goodsId){
            	LOGGER.error("商品号不能为空!");
                return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
            }
            List<GoodsStockInfoEntity> goodsStockList = goodService.loadDetailInfoByGoodsId(goodsId);
            for (GoodsStockInfoEntity goodsStock : goodsStockList) {
                BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(),goodsStock.getId());
                goodsStock.setGoodsPrice(price);
                String stockLogoUrl = goodsStock.getStockLogo();
                goodsStock.setStockLogoNew(imageService.getImageUrl(stockLogoUrl));
                goodsStock.setStockLogo(EncodeUtils.base64Encode(stockLogoUrl));
            }
            
            GoodsInfoEntity goodsInfo = goodService.selectByGoodsId(goodsId);
            if (null!=goodsInfo) {
                returnMap.put("skyType", goodsInfo.getGoodsSkuType());
            }
            returnMap.put("goodsStockList", goodsStockList);
            return Response.success("加载成功", returnMap);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsStockInfo fail", e);
            LOGGER.error("获取商品库存信息失败");
            return Response.fail(BusinessErrorCode.GET_INFO_FAILED);
        }
    } 
    
    
}
