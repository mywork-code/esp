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

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.cart.ShoppingCartService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.gfb.framework.exception.BusinessException;
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
                banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
            }
            
            List<GoodsBasicInfoEntity> recommendGoods = goodService.loadRecommendGoods();
            returnMap.put("banners", banners);
            returnMap.put("recommendGoods", recommendGoods);
            
            for (GoodsBasicInfoEntity goods : recommendGoods) {
                BigDecimal price = commonService.calculateGoodsPrice(goods.getGoodId() ,goods.getGoodsStockId());
                goods.setGoodsPrice(price);
                goods.setGoodsLogoUrl(EncodeUtils.base64Encode(goods.getGoodsLogoUrl()));
                goods.setGoodsSiftUrl(EncodeUtils.base64Encode(goods.getGoodsSiftUrl()));
            }
            return Response.successResponse(returnMap);
        } catch (Exception e) {
            LOGGER.error("indexInit fail", e);
            return Response.fail("首页加载失败");
        }
    }
    
    /**
     * 加载商品列表 
     * 
     * @return
     */
    @POST
    @Path("/loadGoodsList")
    public Response loadGoodsList(Map<String, Object> paramMap){
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
//            String page = CommonUtils.getValue(paramMap, "page");
//            String limit = CommonUtils.getValue(paramMap, "limit");
//            Pagination<GoodsInfoEntity> pagination= goodService.loadGoodsByPages(page,limit);
//            returnMap.put("total", pagination.getTotalCount());
//            returnMap.put("goodsList", pagination.getDataList());
            List<GoodsBasicInfoEntity> goodsList = goodService.loadGoodsList();
            returnMap.put("goodsList", goodsList);
            for (GoodsBasicInfoEntity goodsInfo : goodsList) {
                if (null!=goodsInfo.getGoodId() && null!=goodsInfo.getGoodsStockId()) {
                    BigDecimal price = commonService.calculateGoodsPrice( goodsInfo.getGoodId(),goodsInfo.getGoodsStockId());
                    goodsInfo.setGoodsPrice(price);
                    goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(goodsInfo.getGoodsLogoUrl()));
                    goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(goodsInfo.getGoodsSiftUrl()));
                }
            }
            List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT.getIdentify());
            for(BannerInfoEntity banner : banners){
                banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));
                banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
            }
            returnMap.put("banners", banners);
            return Response.successResponse(returnMap);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsList fail", e);
            return Response.fail("首页加载失败");
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
                return Response.fail("商品号不能为空!");
            }
            if (!StringUtils.isEmpty(userId)) {
                int amountInCart = shoppingCartService.getNumOfTypeInCart(userId);
                returnMap.put("amountInCart", amountInCart);
            }
            goodService.loadGoodsBasicInfoById(goodsId,returnMap);
            return Response.success("加载成功", returnMap);
        } catch (BusinessException e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            return Response.fail(e.getErrorDesc());
        }
        catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsBasicInfo fail", e);
            return Response.fail("获取商品基本信息失败");
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
                return Response.fail("商品号不能为空!");
            }
            List<GoodsStockInfoEntity> goodsStockList = goodService.loadDetailInfoByGoodsId(goodsId);
            for (GoodsStockInfoEntity goodsStock : goodsStockList) {
                BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(),goodsStock.getId());
                goodsStock.setGoodsPrice(price);
                goodsStock.setStockLogo(EncodeUtils.base64Encode(goodsStock.getStockLogo()));
            }
            
            GoodsInfoEntity goodsInfo = goodService.selectByGoodsId(goodsId);
            if (null!=goodsInfo) {
                returnMap.put("skyType", goodsInfo.getGoodsSkuType());
            }
            returnMap.put("goodsStockList", goodsStockList);
            return Response.success("加载成功", returnMap);
        } catch (Exception e) {
            LOGGER.error("ShopHomeController loadGoodsStockInfo fail", e);
            return Response.fail("获取商品库存信息失败");
        }
    } 
    
    
}
