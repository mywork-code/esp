package com.apass.esp.noauth.home;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.enums.BannerType;
import com.apass.esp.domain.enums.CategorySort;
import com.apass.esp.domain.utils.ConstantsUtils;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.service.banner.BannerInfoService;
import com.apass.esp.service.cart.ShoppingCartService;
import com.apass.esp.service.category.CategoryInfoService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.jd.JdGoodsInfoService;
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
	@Autowired
	private JdGoodsInfoService jdGoodsInfoService;
	 @Value("${esp.image.uri}")
	 private String              espImageUrl;
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
                	  bity.setBannerImgUrlNew(espImageUrl+"/static/eshop/other/categoryElectricBanner.png");
           	          bity.setBannerImgUrl(espImageUrl+"/static/eshop/other/categoryElectricBanner.png");
                 }else if("2".equals(String.valueOf(category.getSortOrder()))){//家居百货banner图
                	  bity.setBannerImgUrlNew(espImageUrl+"/static/eshop/other/categoryDepotBanner.png");
           	          bity.setBannerImgUrl(espImageUrl+"/static/eshop/other/categoryDepotBanner.png");

                 }else if("3".equals(String.valueOf(category.getSortOrder()))){//美妆生活banner图
               	  bity.setBannerImgUrlNew(espImageUrl+"/static/eshop/other/categoryBeautyBanner.png");
       	          bity.setBannerImgUrl(espImageUrl+"/static/eshop/other/categoryBeautyBanner.png");
                 }
                 banners.add(bity);
                 returnMap.put("banners", banners);
              }else if(null !=flage && flage.equals("recommend")){
//            	  goodsList = goodService.loadRecommendGoods();//加载精选商品
            	  goodsList = goodService.loadRecommendGoodsList();//加载精选商品列表

          	    List<BannerInfoEntity> banners = bannerService.loadIndexBanners(BannerType.BANNER_SIFT.getIdentify());
                  for(BannerInfoEntity banner : banners){
                      banner.setActivityUrl(banner.getActivityUrl());

                      banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));

                      banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
                  }
                  returnMap.put("banners", banners);
              }else{
            	  goodsList = goodService.loadGoodsList();//加载所以商品

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

                    goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
                    goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
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
     * 加载商品列表(根据类目id查询商品)
     *
     * @return
     */
	@POST
    @Path("/loadGoodsListByCategoryId")
    public Response loadGoodsListByCategoryId(Map<String, Object> paramMap){
        try {
              Map<String, Object> returnMap = new HashMap<String, Object>();
              
        	  String categoryId = CommonUtils.getValue(paramMap, "categoryId");//类目Id
        	  String sort=CommonUtils.getValue(paramMap, "sort");//排序字段
        	  String order=CommonUtils.getValue(paramMap, "order");//顺序(desc（降序），asc（升序）)
    		  String page = CommonUtils.getValue(paramMap, "page");
    		  String rows = CommonUtils.getValue(paramMap, "rows");
    		  if(StringUtils.isEmpty(categoryId)){
    			  LOGGER.error("类目id不能空！");
       			  return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
       		  }
    		  if(StringUtils.isEmpty(order)){
    			  order="DESC";//降序
    		  }
    		  Category cy=categoryInfoService.selectNameById(Long.parseLong(categoryId));
    		  Long level=cy.getLevel();
    		  GoodsBasicInfoEntity  goodsInfoEntity=new GoodsBasicInfoEntity();
    		  if("1".equals(level.toString())){
    	   		goodsInfoEntity.setCategoryId1(Long.parseLong(categoryId));
    		  }else if("2".equals(level.toString())){
      	   		goodsInfoEntity.setCategoryId2(Long.parseLong(categoryId));
    		  }else if("3".equals(level.toString())){
      	   		goodsInfoEntity.setCategoryId3(Long.parseLong(categoryId));
    		  }
    		  
    		  List<GoodsBasicInfoEntity> goodsBasicInfoList=null;
    		  Integer    totalCount=0;
    		  if(CategorySort.CATEGORY_SortA.getCode().equals(sort)){//销量
    			  goodsBasicInfoList= goodsService.loadGoodsByCategoryIdAndAmount(goodsInfoEntity,page, rows);
    			  totalCount  =goodsService.loadGoodsByAmountCount(goodsInfoEntity);
    		  }else if(CategorySort.CATEGORY_SortN.getCode().equals(sort)){//新品
    			  goodsInfoEntity.setOrder(order);//升序或降序
    			  goodsBasicInfoList= goodsService.loadGoodsByCategoryIdAndNew(goodsInfoEntity,page, rows);
    			  totalCount=goodsService.loadGoodsCount(goodsInfoEntity);
    		  }else if(CategorySort.CATEGORY_SortP.getCode().equals(sort)){//价格
    			  goodsInfoEntity.setOrder(order);//升序或降序
    			  goodsBasicInfoList= goodsService.loadGoodsByCategoryIdAndPrice(goodsInfoEntity,page, rows);
    			  totalCount=goodsService.loadGoodsCount(goodsInfoEntity);
    		  }else{//默认（商品上架时间降序）
    			  goodsBasicInfoList= goodsService.loadGoodsByCategoryIdDefault(goodsInfoEntity,page, rows);
    			  totalCount=goodsService.loadGoodsCount(goodsInfoEntity);
    		  }
   			     
  		      returnMap.put("totalCount", totalCount);  
  		     
               for (GoodsBasicInfoEntity goodsInfo : goodsBasicInfoList) {
                if (null!=goodsInfo.getGoodId() && null!=goodsInfo.getGoodsStockId()) {
                    BigDecimal price = commonService.calculateGoodsPrice( goodsInfo.getGoodId(),goodsInfo.getGoodsStockId());
                    goodsInfo.setGoodsPrice(price);
                    goodsInfo.setGoodsPriceFirst(price.multiply(new BigDecimal("0.1")));//商品首付价
                    
                    Long marketPrice=goodsStockInfoRepository.getMaxMarketPriceByGoodsId(goodsInfo.getGoodId());
                    goodsInfo.setMarketPrice(new BigDecimal(marketPrice));

                    String logoUrl = goodsInfo.getGoodsLogoUrl();
                    String siftUrl = goodsInfo.getGoodsSiftUrl();

                    goodsInfo.setGoodsLogoUrlNew(imageService.getImageUrl(logoUrl));
                    goodsInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(logoUrl));
                    goodsInfo.setGoodsSiftUrlNew(imageService.getImageUrl(siftUrl));
                    goodsInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(siftUrl));
                }
            }
            returnMap.put("goodsList", goodsBasicInfoList);
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
     * 获取商品详细信息 基本信息+详细信息(规格 价格 剩余量)
     *
     * @return
     */
    @POST
    @Path("/loadDetailInfoByIdJD")
    public Response loadGoodsBasicInfoJD(Map<String, Object> paramMap){
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
            GoodsInfoEntity goodsInfo = goodsService.selectByGoodsId(Long.valueOf(goodsId));
            //判断是否是京东商品
            if("jd".equals(goodsInfo.getSource()) && "1".equals(goodsInfo.getExternalStatus()+"")){//来源于京东且已关联
            	String externalId = goodsInfo.getExternalId();// 外部商品id
            	returnMap = jdGoodsInfoService.getAppJdGoodsAllInfoBySku(Long.valueOf(externalId).longValue());

            	List<GoodsStockInfoEntity> jdGoodsStockInfoList=goodsStockInfoRepository.loadByGoodsId(goodsId);
            	if(jdGoodsStockInfoList.size()==1){
                    BigDecimal price = commonService.calculateGoodsPrice(goodsId, jdGoodsStockInfoList.get(0).getId());
            		returnMap.put("goodsPrice",price);//商品价格
            		returnMap.put("goodsPriceFirstPayment",new BigDecimal("0.1").multiply(price));//商品首付价格
            	}
            	returnMap.put("source", "jd");
            }else{
                goodService.loadGoodsBasicInfoById(goodsId,returnMap);
            }
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

    /**
     * 人气单品
     * @param paramMap
     * @return
     */
    @POST
    @Path("/popularProducts")
    public Response popularProducts(Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        Long pageIndex = CommonUtils.getLong(paramMap,"pageIndex");
        //Long pageSize = CommonUtils.getLong(paramMap,"pageSize");
        int pageSize = 20;
        if( pageIndex==null){
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        if(pageIndex.intValue()>3){
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        Pagination<String> jdGoodSalesVolumePagination = goodsService.jdGoodSalesVolumeByPage(pageIndex.intValue(),pageSize);
        List<GoodsInfoEntity> goodsList=new ArrayList<>();
        try{
        for (String jdGoodSalesVolume:jdGoodSalesVolumePagination.getDataList()){
            GoodsInfoEntity goodsInfoEntity =  goodsService.selectByGoodsId(Long.valueOf(jdGoodSalesVolume));
            if(goodsInfoEntity.getSource()==null){
                goodsInfoEntity.setGoodsLogoUrlNew(imageService.getImageUrl( goodsInfoEntity.getGoodsLogoUrl()));//非京东
                goodsInfoEntity.setGoodsSiftUrlNew(imageService.getImageUrl( goodsInfoEntity.getGoodsSiftUrl()));
            }else{
                goodsInfoEntity.setGoodsLogoUrl("http://img13.360buyimg.com/n3/"+ goodsInfoEntity.getGoodsLogoUrl());
                goodsInfoEntity.setGoodsSiftUrl("http://img13.360buyimg.com/n3/"+ goodsInfoEntity.getGoodsSiftUrl());
            }
            goodsList.add(goodsInfoEntity);
        }
        }catch (Exception e ){
            return Response.fail(BusinessErrorCode.NO);
        }
        resultMap.put("goodsList",goodsList);
        resultMap.put("pageIndex",pageIndex);
        resultMap.put("totalCount",jdGoodSalesVolumePagination.getTotalCount());
        return Response.successResponse(goodsList);
    }

    /**
     * 热销商品
     * @param paramMap
     * @return
     */
    @POST
    @Path("/crazeProducts")
    public Response crazeProducts(Map<String, Object> paramMap){
        Map<String, Object> resultMap = new HashMap<>();
        Long pageIndex = CommonUtils.getLong(paramMap,"pageIndex");
        int pageSize = 20;
        if( pageIndex==null||pageIndex.intValue()>6){
            return Response.fail(BusinessErrorCode.PARAM_VALUE_ERROR);
        }
        Pagination<String> jdGoodSalesVolumePagination =goodsService.jdGoodSalesVolume(pageIndex.intValue(),pageSize);
        List<GoodsInfoEntity> goodsList=new ArrayList<>();
        try{
            for (String jdGoodSalesVolume:jdGoodSalesVolumePagination.getDataList()){
                GoodsInfoEntity goodsInfoEntity =  goodsService.selectByGoodsId(Long.valueOf(jdGoodSalesVolume));
                String goodsLogoUrl = goodsInfoEntity.getGoodsLogoUrl();
                String goodsSiftUrl = goodsInfoEntity.getGoodsSiftUrl();
                if(goodsInfoEntity.getSource()==null){
                    goodsInfoEntity.setGoodsLogoUrlNew(imageService.getImageUrl(goodsLogoUrl));//非京东
                    goodsInfoEntity.setGoodsSiftUrlNew(imageService.getImageUrl(goodsSiftUrl));
                }else{
                    goodsInfoEntity.setGoodsLogoUrl("http://img13.360buyimg.com/n3/"+goodsLogoUrl);
                    goodsInfoEntity.setGoodsSiftUrl("http://img13.360buyimg.com/n3/"+goodsSiftUrl);
                }
                goodsList.add(goodsInfoEntity);
            }
            resultMap.put("goodsList",goodsList);
            resultMap.put("pageIndex",pageIndex);
            resultMap.put("totalCount",120);
        }catch (Exception e ){
            return Response.fail(BusinessErrorCode.NO);
        }
        return Response.successResponse(goodsList);
    }
}
