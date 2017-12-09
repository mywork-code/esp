package com.apass.esp.service.goods;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.dto.goods.GoodsStockSkuDto;
import com.apass.esp.domain.entity.*;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsDetailInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdSaleAttr;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.domain.entity.jd.JdSimilarSkuTo;
import com.apass.esp.domain.entity.jd.JdSimilarSkuVo;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.GoodStatus;
import com.apass.esp.domain.enums.SourceType;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.mapper.JdCategoryMapper;
import com.apass.esp.mapper.JdGoodSalesVolumeMapper;
import com.apass.esp.mapper.JdGoodsMapper;
import com.apass.esp.repository.banner.BannerInfoRepository;
import com.apass.esp.repository.goods.GoodsBasicRepository;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.utils.Pinyin4jUtil;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.common.ImageService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.entity.base.JdCategory;
import com.apass.esp.third.party.jd.entity.base.JdGoods;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.utils.PaginationManage;
import com.apass.esp.utils.ValidateUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RandomUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class GoodsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GoodsService.class);

  @Autowired
  private GoodsRepository goodsDao;

  @Autowired
  private GoodsStockInfoRepository goodsStockDao;

  @Autowired
  private BannerInfoRepository bannerInfoDao;

  @Autowired
  private CommonService commonService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private MerchantInforService merchantInforService;

  @Autowired
  private GoodsBasicRepository goodsBasicRepository;

  @Autowired
  private JdGoodSalesVolumeMapper jdGoodSalesVolumeMapper;

  @Autowired
  private JdCategoryMapper jdCategoryMapper;

  @Autowired
  private JdGoodsInfoService jdGoodsInfoService;

  @Autowired
  private CategoryMapper categoryMapper;

  @Autowired
  private JdGoodsMapper jdGoodsMapper;

  @Autowired
  private JdProductApiClient jdProductApiClient;
  @Autowired
  private ProGroupGoodsService proGroupGoodsService;
  @Autowired
  private ActivityCfgService activityCfgService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private GoodsAttrValService goodsAttrValService;
  @Autowired
  private GoodsAttrService goodsAttrService;
  @Autowired
  private GoodsStockInfoRepository goodsStockInfoRepository;
    @Autowired
  private WeiZhiProductApiClient productApiClient;
  /**
   * app 首页加载精品推荐商品
   *
   * @return
   */
  public Pagination<GoodsBasicInfoEntity> loadRecommendGoods(int pageIndex, int pageSize) {
    	 Pagination<GoodsBasicInfoEntity> result=goodsDao.loadRecommendGoods(pageIndex, pageSize);
    	 List<GoodsBasicInfoEntity> goodsList=result.getDataList();
    	 for (GoodsBasicInfoEntity goodsBasicInfoEntity : goodsList) {
    		 Map<String,Object> map=goodsDao.selectMinGoodsStockByGoodsId(goodsBasicInfoEntity.getGoodId());
    		 Long goodsStockId=(Long) map.get("goodsStockId"); 
    		 BigDecimal goodsPrice=(BigDecimal) map.get("goodsPrice");
    		 goodsBasicInfoEntity.setGoodsStockId(goodsStockId);
    		 goodsBasicInfoEntity.setGoodsPrice(goodsPrice);
		}
    		return result;
  }

  /**
   * app 加载精品推荐商品列表
   *
   * @return
   */
  public List<GoodsBasicInfoEntity> loadRecommendGoodsList() {
     List<GoodsBasicInfoEntity> resultList=goodsDao.loadRecommendGoodsList();
	 for (GoodsBasicInfoEntity goodsBasicInfoEntity : resultList) {
		 Map<String,Object> map=goodsDao.selectMinGoodsStockByGoodsId(goodsBasicInfoEntity.getGoodId());
		 Long goodsStockId=(Long) map.get("goodsStockId");
		 BigDecimal goodsPrice=(BigDecimal) map.get("goodsPrice");
		 goodsBasicInfoEntity.setGoodsStockId(goodsStockId);
		 goodsBasicInfoEntity.setGoodsPrice(goodsPrice);
	}
     return resultList;
  }

  /**
   * 加载商品列表[分页]
   *
   * @param page
   * @param limit
   * @return
   */
  public Pagination<GoodsInfoEntity> loadGoodsByPages(String page, String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    GoodsInfoEntity param = new GoodsInfoEntity();
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = goodsDao.count(param);
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    Page pageParam = new Page(pageInteger, limitInteger);
    return goodsDao.loadGoodsByPages(pageParam, param);
  }

  /**
   * 通过类目id查询商品[客户端分页]
   */
  public Pagination<GoodsBasicInfoEntity> loadGoodsByCategoryId(GoodsBasicInfoEntity param, String page,
                                                                String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = goodsBasicRepository.count(param);
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    Page pageParam = new Page(pageInteger, limitInteger);
    return goodsBasicRepository.loadGoodsPages(pageParam, param);
  }

  /**
   * 通过类目id查询商品[客户端分页](商品上架时间)(按商品销量排列)(商品创建时间)(商品售价)
   */
  public List<GoodsBasicInfoEntity> loadGoodsByParam(GoodsBasicInfoEntity gbinfoty, String page,
                                                     String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = 20;
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    gbinfoty.setPage((pageInteger - 1) * limitInteger);
    gbinfoty.setRows(limitInteger);
    return goodsBasicRepository.loadGoodsByParam(gbinfoty);
  }

  /**
   * 搜索商品（新品，默认）
   *
   * @param page
   * @return
   */
  public List<GoodsBasicInfoEntity> searchPage(GoodsBasicInfoEntity goodsBasicInfoEntity, String page,
                                               String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = 20;
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    goodsBasicInfoEntity.setPage((pageInteger - 1) * limitInteger);
    goodsBasicInfoEntity.setRows(limitInteger);
    return goodsBasicRepository.searchList(goodsBasicInfoEntity);
  }

  /**
   * 搜索商品（销量）
   *
   * @param page
   * @return
   */
  public List<GoodsBasicInfoEntity> searchGoodsListAmount(GoodsBasicInfoEntity goodsBasicInfoEntity,
                                                          String page, String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = 20;
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    goodsBasicInfoEntity.setPage((pageInteger - 1) * limitInteger);
    goodsBasicInfoEntity.setRows(limitInteger);
    return goodsBasicRepository.searchGoodsListAmount(goodsBasicInfoEntity);
  }

  /**
   * 搜索商品（价格）
   *
   * @param page
   * @return
   */
  public List<GoodsBasicInfoEntity> searchGoodsListPrice(GoodsBasicInfoEntity goodsBasicInfoEntity,
                                                         String page, String limit) {
    Integer limitInteger = null;
    Integer pageInteger = null;
    if (StringUtils.isNotEmpty(limit)) {
      limitInteger = Integer.valueOf(limit);
    } else {
      limitInteger = 20;
    }
    pageInteger = StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page);
    goodsBasicInfoEntity.setPage((pageInteger - 1) * limitInteger);
    goodsBasicInfoEntity.setRows(limitInteger);
    return goodsBasicRepository.searchGoodsListPrice(goodsBasicInfoEntity);
  }

  /**
   * 查询可上架的京东商品
   *
   * @return
   */
  public List<GoodsBasicInfoEntity> searchJDGoodsList(GoodsBasicInfoEntity gbinfoty) {
    return goodsBasicRepository.searchJDGoodsList(gbinfoty);
  }

  /**
   * 查询可上架的京东商品数量
   *
   * @return
   */
  public Integer selectJDGoodsCount() {
    return goodsBasicRepository.selectJDGoodsCount();
  }

  /**
   * 通过类目id查询商品[客户端分页](商品上架时间)(按商品销量排列)(商品创建时间)(商品售价)(数量)
   */
  public Integer loadGoodsByParamCount(GoodsBasicInfoEntity gbinfoty) {
    return goodsBasicRepository.loadGoodsByParamCount(gbinfoty);
  }

  /**
   * 搜索商品
   */
  public Integer searchGoodsListCount(GoodsBasicInfoEntity gbinfoty) {
    return goodsBasicRepository.searchGoodsListCount(gbinfoty);
  }

  /**
   * 搜索商品根据goodsId查询商品详情
   */
  public GoodsBasicInfoEntity serchGoodsByGoodsId(String goodsId) {
    return goodsBasicRepository.serchGoodsByGoodsId(goodsId);
  }

  /**
   * 加载商品列表
   * @return
   */
  public List<GoodsBasicInfoEntity> loadGoodsList() {
    return goodsDao.loadGoodsList();
  }

  /**
   * 验证商品是否可售
   * @param goodId
   * @return
   */
  public boolean validateGoodOnShelf(Long goodId){
      GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodId);
      Date now = new Date();//获取当前时间
      if(null == goodsBasicInfo){
          return false;
      }
      boolean ifHasDelistTime = false;
      if(goodsBasicInfo.getDelistTime() != null){
          ifHasDelistTime = now.after(goodsBasicInfo.getDelistTime());

      }
      if (now.before(goodsBasicInfo.getListTime()) || ifHasDelistTime
            || !StringUtils.equals(goodsBasicInfo.getStatus(), GoodStatus.GOOD_UP.getCode())) {
          //下架
         return false;
      }
         
      if(StringUtils.isEmpty(goodsBasicInfo.getSource())){
          List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodId);
             for (GoodsStockInfoEntity goodsStock : goodsList) {
                 if (goodsStock.getStockCurrAmt() > 0 ) {
                   return true;
                 }
             }
      }else{
          String externalId = goodsBasicInfo.getExternalId();// 外部商品id
          //验证商品是否可售（当验证为不可售时，更新数据库商品状态）
          if(orderService.checkGoodsSalesOrNot(externalId)){
             return true;//商品可售
          }
      }
      return false;
  }
  /**
   * 获取商品基本信息（sprint 11） 非京东商品变成多规格商品
   *
   * @param goodsId
   * @return
   * @throws BusinessException
   */
  public void loadGoodsBasicInfoById2(Long goodsId, Map<String, Object> returnMap) throws BusinessException {
      GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
      if (null == goodsBasicInfo) {
          LOGGER.error("商品信息不存在:{}", goodsId);
          throw new BusinessException("商品信息不存在");
      }
      returnMap.put("status", goodsBasicInfo.getStatus());//商品状态
      //根据商品上架时间，下架时间和状态判断商品的状态
      Date now = new Date();
      if (now.before(goodsBasicInfo.getListTime()) || (null != goodsBasicInfo.getDelistTime() && now.after(goodsBasicInfo.getDelistTime()))
              || !GoodStatus.GOOD_UP.getCode().equals(goodsBasicInfo.getStatus())) {
          returnMap.put("status", GoodStatus.GOOD_DOWN.getCode());
      }
      //判断该商品下是否有货
      List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
      if (null == goodsList || goodsList.size() == 0) {
          LOGGER.error("改商品下没有对应的规格信息:{}", goodsId);
          throw new BusinessException("商品规格信息不存在");
      }
      boolean offShelfFlag = true;
      for (GoodsStockInfoEntity goodsStock : goodsList) {
          if (goodsStock.getStockCurrAmt() > 0) {
              offShelfFlag = false;
              break;
          }
      }
      //如果该商品下所有规格都没有库存则该商品的状态为下架状态
      if (offShelfFlag) {
          returnMap.put("status", GoodStatus.GOOD_DOWN.getCode());
      }
      String goodsStockId = (String) returnMap.get("goodsStockId");
      GoodsStockInfoEntity defaultGoodsPriceStock = new GoodsStockInfoEntity();
      BigDecimal defaultPrice = null;
      //如果传了goodsStockId则以这个为默认，否则以价格最低者为默认
      if (StringUtils.isNotEmpty(goodsStockId)) {
          defaultGoodsPriceStock = goodsStockDao.getGoodsStockInfoEntityByStockId(Long.parseLong(goodsStockId));
          defaultPrice = commonService.calculateGoodsPrice(goodsId, Long.parseLong(goodsStockId));
      } else {
          //商品价格最低goodsStock为默认显示的规格
          Map<String, Object> result = getMinPriceNotJdGoods(goodsId);
          defaultGoodsPriceStock = (GoodsStockInfoEntity) result.get("goodsStock");
          defaultPrice = (BigDecimal) result.get("minPrice");
      }

      returnMap.put("unSupportProvince", goodsBasicInfo.getUnSupportProvince());// 不配送区域
      returnMap.put("goodsPrice", defaultPrice);
      if (null != defaultPrice) {
          returnMap.put("goodsPriceFirstPayment", (new BigDecimal("0.1").multiply(defaultPrice)).setScale(2, BigDecimal.ROUND_DOWN));
      }
      returnMap.put("googsDetail", goodsBasicInfo.getGoogsDetail());
      returnMap.put("goodsTitle", goodsBasicInfo.getGoodsTitle());
      returnMap.put("goodsName", goodsBasicInfo.getGoodsName());
      returnMap.put("goodsId", goodsId);
      //不支持发货地址
      Boolean isUnSupport = false;
      if (null != returnMap.get("isUnSupport")) {
          isUnSupport = (Boolean) returnMap.get("isUnSupport");
      }
      if (null != defaultGoodsPriceStock) {
          returnMap.put("skuId", defaultGoodsPriceStock.getSkuId());
          returnMap.put("goodsStockDes", "无货");
          if (null != defaultGoodsPriceStock.getStockCurrAmt() && defaultGoodsPriceStock.getStockCurrAmt() > 0 && !isUnSupport) {
              returnMap.put("goodsStockDes", "有货");
          }
          //商品价格最低
          Map<String, Object> result = getMinPriceNotJdGoods(goodsId);
          GoodsStockInfoEntity MinGoodsPriceStock = (GoodsStockInfoEntity) result.get("goodsStock");
          BigDecimal minPrice = (BigDecimal) result.get("minPrice");
          returnMap.put("goodsPrice", minPrice);
          returnMap.put("goodsPriceFirstPayment", (new BigDecimal("0.1").multiply(minPrice)).setScale(2, BigDecimal.ROUND_DOWN));
          returnMap.put("googsDetail", goodsBasicInfo.getGoogsDetail());
          returnMap.put("skuId", MinGoodsPriceStock.getSkuId());
          returnMap.put("goodsStockDes", "无货");
          if (null != MinGoodsPriceStock.getStockCurrAmt() && MinGoodsPriceStock.getStockCurrAmt() > 0) {
              returnMap.put("goodsStockDes", "有货");
          }

          returnMap.put("source", "notJd");
          // 查询商品图片
          List<String> JdImagePathList = new ArrayList<>();
          List<BannerInfoEntity> goodsBannerList = bannerInfoDao.loadIndexBanners(String.valueOf(goodsId));
          for (BannerInfoEntity banner : goodsBannerList) {
              JdImagePathList.add(imageService.getImageUrl(banner.getBannerImgUrl()));
          }
          List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuListBygoodsId(goodsId, defaultGoodsPriceStock.getAttrValIds());
          List<JdSimilarSkuTo> JdSimilarSkuToList = null;
          if (jdSimilarSkuList != null) {
              JdSimilarSkuToList = getJdSimilarSkuToListByGoodsId(goodsId, jdSimilarSkuList, isUnSupport);
          } else {
              JdSimilarSkuToList = new ArrayList<>();
              JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();
              JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
              jdSimilarSkuVo.setGoodsId(goodsId.toString());
              jdSimilarSkuVo.setSkuId(goodsList.get(0).getSkuId());
              jdSimilarSkuVo.setGoodsStockId(goodsList.get(0).getId().toString());
              BigDecimal price = commonService.calculateGoodsPrice(goodsId, goodsList.get(0).getId());
              jdSimilarSkuVo.setPrice(price);
              jdSimilarSkuVo.setStockCurrAmt(goodsList.get(0).getStockCurrAmt());
              jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2,
                      BigDecimal.ROUND_DOWN));
              jdSimilarSkuVo.setStockDesc(returnMap.get("goodsStockDes").toString());
              jdSimilarSkuTo.setSkuIdOrder("");
              jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
              JdSimilarSkuToList.add(jdSimilarSkuTo);
          }

          returnMap.put("jdImagePathList", JdImagePathList);
          returnMap.put("support7dRefund", goodsBasicInfo.getSupport7dRefund());//是否支持7天无理由退货,Y、N
          returnMap.put("merchantCode", goodsBasicInfo.getMerchantCode());
          returnMap.put("activityCfg", getActivityInfo(goodsId));// 满减活动字段
          if (null != jdSimilarSkuList) {
              returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
              returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
          } else {
              jdSimilarSkuList = new ArrayList<>();
              returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
              returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
          }
          returnMap.put("JdSimilarSkuToList", JdSimilarSkuToList);
          //返回活动id
          ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getByGoodsId(goodsId);
          if (null != proGroupGoodsBo && proGroupGoodsBo.isValidActivity()) {
              returnMap.put("proActivityId", proGroupGoodsBo.getActivityId());
          }
          //获取商品的优惠券
          List<String> proCoupons = jdGoodsInfoService.getProCouponList(goodsId);
          if (proCoupons.size() > 3) {
              returnMap.put("proCouponList", proCoupons.subList(0, 3));
          } else {
              returnMap.put("proCouponList", proCoupons);
          }
          returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
          returnMap.put("JdSimilarSkuToList", JdSimilarSkuToList);
          returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
          returnMap.put("postage", "0");// 电商3期511 添加邮费字段（当邮费为0时显示免运费） 20170517
      }
  }
	/**
	 * 根据商品编号获取商品需要展示前端信息
	 */
	public Map<String, Object> loadAllBannerPicNotJd(Long goodsId) throws BusinessException {
		Map<String, Object> returnMap = Maps.newHashMap();
	    GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
	    if (null == goodsBasicInfo) {
	      LOGGER.error("商品信息不存在:{}", goodsId);
	      throw new BusinessException("商品信息不存在");
	    }
	    //商品价格最低
	    Map<String,Object> result= getMinPriceNotJdGoods(goodsId);
	    GoodsStockInfoEntity MinGoodsPriceStock=(GoodsStockInfoEntity) result.get("goodsStock");
	    BigDecimal minPrice =(BigDecimal) result.get("minPrice");
	    if(BigDecimal.ZERO.compareTo(minPrice)==0){
	    	 returnMap.put("goodsPrice",null);
	    }else{
	    	 returnMap.put("goodsPrice",minPrice);
	    }
	    returnMap.put("googsDetail",goodsBasicInfo.getGoogsDetail());
	    returnMap.put("goodsName",goodsBasicInfo.getGoodsName());
	    returnMap.put("skuId",MinGoodsPriceStock.getSkuId());
	    // 查询商品图片
	 	List<String> JdImagePathList=new ArrayList<>();
	    List<BannerInfoEntity> goodsBannerList = bannerInfoDao.loadIndexBanners(String.valueOf(goodsId));
	    for (BannerInfoEntity banner : goodsBannerList) {
	    	JdImagePathList.add(imageService.getImageUrl(banner.getBannerImgUrl()));
	    }
	    List<JdSimilarSku>  jdSimilarSkuList=new ArrayList<>();
	    if(StringUtils.isNotEmpty(MinGoodsPriceStock.getAttrValIds())){
		   jdSimilarSkuList=getJdSimilarSkuListBygoodsId2(goodsId,MinGoodsPriceStock.getAttrValIds());
	    }
	    if(null ==jdSimilarSkuList ){
	    	 List<JdSimilarSku>  jdSimilarSkuList2=new ArrayList<>();
	 	    returnMap.put("jdSimilarSkuList", jdSimilarSkuList2);
		    returnMap.put("jdSimilarSkuListSize", 0);
	    }else{
		    returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
		    returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
	    }
	    returnMap.put("jdImagePathList",JdImagePathList);
	    return returnMap;
	}
	/**
	 * 根据商品编号获取商品需要展示前端信息
	 */
	public Map<String, Object> loadAllBannerPicNotJd2(String skuId) throws BusinessException {
		Map<String, Object> returnMap = Maps.newHashMap();
	    List<GoodsStockInfoEntity> list=goodsStockInfoRepository.loadBySkuId(Long.parseLong(skuId));
	    if (null == list) {
	      LOGGER.error("商品信息不存在:{}skuId=", skuId);
	      throw new BusinessException("商品信息不存在");
	    }
	    Long goodsId=list.get(0).getGoodsId();
	    GoodsInfoEntity goodsBasicInfo = goodsDao.select(list.get(0).getGoodsId());

		if (BigDecimal.ZERO.compareTo(list.get(0).getGoodsPrice()) == 0) {
			returnMap.put("goodsPrice", null);
		} else {
			returnMap.put("goodsPrice", list.get(0).getGoodsPrice());
		}
	    returnMap.put("googsDetail",goodsBasicInfo.getGoogsDetail());
	    returnMap.put("goodsName",goodsBasicInfo.getGoodsName());
	    returnMap.put("skuId",skuId);
	    // 查询商品图片
	 	List<String> JdImagePathList=new ArrayList<>();
	    List<BannerInfoEntity> goodsBannerList = bannerInfoDao.loadIndexBanners(String.valueOf(goodsId));
	    for (BannerInfoEntity banner : goodsBannerList) {
	    	JdImagePathList.add(imageService.getImageUrl(banner.getBannerImgUrl()));
	    }
	    List<JdSimilarSku>  jdSimilarSkuList=new ArrayList<>();
	    if(null !=list.get(0).getAttrValIds()){
		   jdSimilarSkuList=getJdSimilarSkuListBygoodsId(goodsId,list.get(0).getAttrValIds());
	    }
	    if(null ==jdSimilarSkuList ){
            List<JdSimilarSku>  jdSimilarSkuList2=new ArrayList<>();
	 	    returnMap.put("jdSimilarSkuList", jdSimilarSkuList2);
		    returnMap.put("jdSimilarSkuListSize", 0);
	    }else{
		    returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
		    returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
	    }
	    returnMap.put("jdImagePathList",JdImagePathList);
	    return returnMap;
	}
  /**
   * 获取 非京东商品变成多规格商品规格
   */
  public Map<String, Object> loadGoodsBasicInfoById3(Long goodsId) throws BusinessException {
	Map<String, Object> returnMap =new HashMap<>();
    //商品价格最低
    Map<String,Object> result= getMinPriceNotJdGoods(goodsId);
    GoodsStockInfoEntity MinGoodsPriceStock=(GoodsStockInfoEntity) result.get("goodsStock");
    List<JdSimilarSku>  jdSimilarSkuList=getJdSimilarSkuListBygoodsId(goodsId,MinGoodsPriceStock.getAttrValIds());
    if(null !=jdSimilarSkuList){
      Boolean isUnSupport=false;
      List<JdSimilarSkuTo> JdSimilarSkuToList =getJdSimilarSkuToListByGoodsId(goodsId,jdSimilarSkuList,isUnSupport);
      returnMap.put("JdSimilarSkuToList", JdSimilarSkuToList);
      returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
      returnMap.put("jdSimilarSkuList", jdSimilarSkuList);
    }else{
    	List<JdSimilarSkuTo> JdSimilarSkuToList2=new ArrayList<>();
        JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();
        JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
        jdSimilarSkuVo.setGoodsId(goodsId.toString());
        jdSimilarSkuVo.setSkuId(MinGoodsPriceStock.getSkuId());
        jdSimilarSkuVo.setGoodsStockId(MinGoodsPriceStock.getId().toString());
        BigDecimal price = commonService.calculateGoodsPrice(goodsId, MinGoodsPriceStock.getId());
        jdSimilarSkuVo.setPrice(price);
        jdSimilarSkuVo.setStockCurrAmt(MinGoodsPriceStock.getStockCurrAmt());
        jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2,
                BigDecimal.ROUND_DOWN));
        if (null != MinGoodsPriceStock.getStockCurrAmt() && MinGoodsPriceStock.getStockCurrAmt() > 0) {
            jdSimilarSkuVo.setStockDesc("有货");
		}else{
	        jdSimilarSkuVo.setStockDesc("无货");

		}
        jdSimilarSkuTo.setSkuIdOrder("");
        jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
        JdSimilarSkuToList2.add(jdSimilarSkuTo);
    	List<JdSimilarSku>  jdSimilarSkuList2=new  ArrayList<>();
        returnMap.put("JdSimilarSkuToList", JdSimilarSkuToList2);
        returnMap.put("jdSimilarSkuListSize", jdSimilarSkuList2.size());
        returnMap.put("jdSimilarSkuList", jdSimilarSkuList2);
    }
   
    return returnMap;
  }
	/**
	 * 通过商品goodsId组装非京东上商品的JdSimilarSkuToList
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<JdSimilarSkuTo> getJdSimilarSkuToListByGoodsId(Long goodsId,List<JdSimilarSku> list,Boolean isUnSupport) throws BusinessException {
		GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
		Long proActivityId = null;
		String activityCfg;
		String support7dRefund;
		// 返回活动id
		ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getByGoodsId(goodsId);
		if (null != proGroupGoodsBo && proGroupGoodsBo.isValidActivity()) {
			proActivityId = proGroupGoodsBo.getActivityId();
		}
		// 满减活动满减字段
		activityCfg = getActivityInfo(goodsId);
		// 是否支持7天无理由退货,Y、N
		support7dRefund = goodsBasicInfo.getSupport7dRefund();

		List<String> proCoupons = new ArrayList<>();
		// 获取商品的优惠券
		List<String> proCoupons2 = jdGoodsInfoService.getProCouponList(goodsId);
		if (proCoupons2.size() > 3) {
			proCoupons = proCoupons2.subList(0, 3);
		} else {
			proCoupons = proCoupons2;
		}
		
		// 查询商品规格中的商品的价格和库存
		List<JdSimilarSkuTo> JdSimilarSkuToList = new ArrayList<>();
		List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
		for (GoodsStockInfoEntity goodsStockInfoEntity : goodsStockList) {
			JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();
			String attrValIds=goodsStockInfoEntity.getAttrValIds();
			String[] attrValIdsList=attrValIds.split(":");
			//当t_esp_goods_stock_info 中多规格属性组合排序乱时，重新排序
			//当后台代码按规律排好序时，下列排序代码注释掉
			String[] attrValIdsList2=new String[attrValIdsList.length];
			for (String string : attrValIdsList) {
				for (JdSimilarSku jdSimilarSku : list) {
					List<JdSaleAttr> saleAttrList=jdSimilarSku.getSaleAttrList();
					for (JdSaleAttr jdSaleAttr : saleAttrList) {
							if(StringUtils.equals(jdSaleAttr.getSaleValueId(),string)){
								int index=jdSimilarSku.getDim()-1;
								attrValIdsList2[index]=string;
							}
					}
				}
			}
			for (JdSimilarSku jdSimilarSku : list) {
				List<JdSaleAttr> saleAttrList=jdSimilarSku.getSaleAttrList();
				for (JdSaleAttr jdSaleAttr : saleAttrList) {
					for(int i=0;i<attrValIdsList2.length;i++){
						if(StringUtils.equals(jdSaleAttr.getSaleValueId(),attrValIdsList2[i])){
							attrValIdsList2[i]=jdSimilarSku.getDim()+""+jdSaleAttr.getSaleValueId();
							break;
						}
						
					}
				
				}
			}
			String attrValIds2="";
			for (String string : attrValIdsList2) {
				if(StringUtils.isEmpty(attrValIds2)){
					attrValIds2=string;
				}else{
					attrValIds2=attrValIds2+";"+string;
				}
			}
			jdSimilarSkuTo.setSkuIdOrder(attrValIds2);
			JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
			jdSimilarSkuVo.setSkuId(goodsStockInfoEntity.getSkuId());
			jdSimilarSkuVo.setGoodsId(goodsId.toString());
			jdSimilarSkuVo.setGoodsStockId(goodsStockInfoEntity.getId().toString());
			jdSimilarSkuVo.setStockCurrAmt(goodsStockInfoEntity.getStockCurrAmt());
			BigDecimal price = commonService.calculateGoodsPrice(goodsId, goodsStockInfoEntity.getId());
			jdSimilarSkuVo.setPrice(price);
			jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
			jdSimilarSkuVo.setStockDesc("无货");
			if (null != goodsStockInfoEntity.getStockCurrAmt() && goodsStockInfoEntity.getStockCurrAmt() > 0 && !isUnSupport) {
				jdSimilarSkuVo.setStockDesc("有货");
			}
			jdSimilarSkuVo.setActivityCfg(activityCfg);
			jdSimilarSkuVo.setProActivityId(proActivityId);
			jdSimilarSkuVo.setProCouponList(proCoupons);
			jdSimilarSkuVo.setSupport7dRefund(support7dRefund);
			jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
			JdSimilarSkuToList.add(jdSimilarSkuTo);
		}
		return JdSimilarSkuToList;
	}
	/**
	 * 通过商品goodsId组装非京东上商品的jdSimilarSkuList
	 * 
	 * @return
	 * @throws BusinessException
	 */
      public List<JdSimilarSku> getJdSimilarSkuListBygoodsId(Long goodsId,String attrValId) throws BusinessException {
          LOGGER.info("getJdSimilarSkuListBygoodsId方法执行,参数goodsId:{},attrValId:{}",goodsId,attrValId);
          if(StringUtils.isEmpty(attrValId.trim())){
              return null;
          }
          String[] attrValIdString=attrValId.split(":");
          Map<String,Object> map=new HashMap<>();
          for (int i = 0; i < attrValIdString.length; i++) {
              GoodsAttrVal gv=goodsAttrValService.selectByPrimaryKey(Long.parseLong(attrValIdString[i]));
              map.put(gv.getAttrId().toString(), i+1);
          }
          // 拼凑京东商品jdSimilarSkuList数据格式
          List<JdSimilarSku> jdSimilarSkuList = new ArrayList<>();
          // 查出商品属性
          List<GoodsAttrVal> goodsAttrValList = goodsAttrValService.queryGoodsAttrValsByGoodsId(goodsId);
          // 查询 t_esp_goods_attr_val 商品不同规格下对应值表
          for (GoodsAttrVal goodsAttrVal : goodsAttrValList) {
              JdSimilarSku jdSimilarSku = new JdSimilarSku();
              GoodsAttr goodsAttr = goodsAttrService.selectGoodsAttrByid(goodsAttrVal.getAttrId());
              int dim=(int) map.get(goodsAttrVal.getAttrId().toString());
              String saleName = goodsAttr.getName();// 京东saleName
              jdSimilarSku.setSaleName(saleName);
              jdSimilarSku.setDim(dim);
              List<JdSaleAttr> saleAttrList = new ArrayList<>();
              List<GoodsAttrVal> GoodsAttrValList = goodsAttrValService.queryByGoodsIdAndAttrId(goodsId,
                      goodsAttrVal.getAttrId());
              for (GoodsAttrVal goodsAttrVal2 : GoodsAttrValList) {
                  JdSaleAttr jdSaleAttr = new JdSaleAttr();
                  jdSaleAttr.setSaleValue(goodsAttrVal2.getAttrVal());
                  jdSaleAttr.setSaleValueId(goodsAttrVal2.getId() + "");
                  //默认价格最低的商品的属性规格为默认值
                  if(Arrays.asList(attrValIdString).contains(goodsAttrVal2.getId().toString())){
                      jdSaleAttr.setIsSelect("true");
                  }
                  Boolean isImageFalge=true;
                  List<String> skuIds = new ArrayList<>();
                  List<String> skuIdStrList=new ArrayList<>();
                  List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
                  for (GoodsStockInfoEntity goodsStockInfoEntity : goodsList) {
                      if (StringUtils.isNotEmpty(goodsStockInfoEntity.getAttrValIds())) {
                          String[] attrValIds = goodsStockInfoEntity.getAttrValIds().split(":");
                          if (Arrays.asList(attrValIds).contains(goodsAttrVal2.getId().toString())) {
                              if(isImageFalge){
                                  jdSaleAttr.setImagePath(imageService.getImageUrl(goodsStockInfoEntity.getStockLogo()));
                                  isImageFalge=false;
                              }
                              skuIds.add(goodsStockInfoEntity.getSkuId());
                              skuIdStrList.add(goodsStockInfoEntity.getSkuId());
                          }
                      }
                  }

                  jdSaleAttr.setSkuIds(skuIds);
                  jdSaleAttr.setSkuIdStr(StringUtils.join(skuIdStrList.toArray(), ","));
                  saleAttrList.add(jdSaleAttr);
              }
              jdSimilarSku.setSaleAttrList(saleAttrList);
              jdSimilarSkuList.add(jdSimilarSku);
          }
          //根据ProCouponVo里面的开始时间排序
          Collections.sort(jdSimilarSkuList,new Comparator<JdSimilarSku>(){
              @Override
              public int compare(JdSimilarSku o1, JdSimilarSku o2) {
                  if(o1.getDim() > o2.getDim()){
                      return 1;
                  }else{
                      return -1;
                  }
              }

          });
          return jdSimilarSkuList;
      }

	/**
	 * 通过stockID获取商品的商品描述
	 */
	public String getGoodsStockDesc(Long goodsStockId) {
		GoodsStockInfoEntity goodsStockInfoEntity = goodsStockDao.getGoodsStockInfoEntityByStockId(goodsStockId);
		if (null != goodsStockInfoEntity.getAttrValIds()) {
			StringBuffer sb = new StringBuffer();
			String[] attrValIds = goodsStockInfoEntity.getAttrValIds().split(":");
			for (String string : attrValIds) {
				GoodsAttrVal goodsAttrVal = goodsAttrValService.selectByPrimaryKey(Long.parseLong(string));
				if(null !=goodsAttrVal){
					sb.append(goodsAttrVal.getAttrVal());
					sb.append(" ");
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	/**
	 * 后台显示通过商品goodsId组装非京东上商品的jdSimilarSkuList
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<JdSimilarSku> getJdSimilarSkuListBygoodsId2(Long goodsId,String attrValId) throws BusinessException {
		String[] attrValIdString=null;
		if(StringUtils.isNotEmpty(attrValId.trim())){
			attrValIdString=attrValId.split(":");
		}
		Map<String,Object> map=new HashMap<>();
		if (null != attrValIdString) {
			for (int i = 0; i < attrValIdString.length; i++) {
				GoodsAttrVal gv = goodsAttrValService.selectByPrimaryKey(Long.parseLong(attrValIdString[i]));
				map.put(gv.getAttrId().toString(), i + 1);
			}
		}
		// 拼凑京东商品jdSimilarSkuList数据格式
		List<JdSimilarSku> jdSimilarSkuList = new ArrayList<>();
		// 查出商品属性
		List<GoodsAttrVal> goodsAttrValList = goodsAttrValService.queryGoodsAttrValsByGoodsId(goodsId);
		// 查询 t_esp_goods_attr_val 商品不同规格下对应值表
		for (GoodsAttrVal goodsAttrVal : goodsAttrValList) {
			JdSimilarSku jdSimilarSku = new JdSimilarSku();
			GoodsAttr goodsAttr = goodsAttrService.selectGoodsAttrByid(goodsAttrVal.getAttrId());
			int dim=(int) map.get(goodsAttrVal.getAttrId().toString());
			String saleName = goodsAttr.getName();// 京东saleName
			jdSimilarSku.setSaleName(saleName);
			jdSimilarSku.setDim(dim);
			List<JdSaleAttr> saleAttrList = new ArrayList<>();
			List<GoodsAttrVal> GoodsAttrValList = goodsAttrValService.queryByGoodsIdAndAttrId(goodsId,
					goodsAttrVal.getAttrId());
			for (GoodsAttrVal goodsAttrVal2 : GoodsAttrValList) {
				JdSaleAttr jdSaleAttr = new JdSaleAttr();
				jdSaleAttr.setSaleValue(goodsAttrVal2.getAttrVal());
				Boolean isImageFalge=true;
				List<String> skuIds = new ArrayList<>();
				List<String> skuIdStrList=new ArrayList<>();
				List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
				for (GoodsStockInfoEntity goodsStockInfoEntity : goodsList) {
					if (StringUtils.isNotEmpty(goodsStockInfoEntity.getAttrValIds())) {
						String[] attrValIds = goodsStockInfoEntity.getAttrValIds().split(":");
						if (Arrays.asList(attrValIds).contains(goodsAttrVal2.getId().toString())) {
							skuIds.add(goodsStockInfoEntity.getSkuId());
							skuIdStrList.add(goodsStockInfoEntity.getSkuId());
							if(isImageFalge){
								jdSaleAttr.setImagePath(imageService.getImageUrl(goodsStockInfoEntity.getStockLogo()));
								isImageFalge=false;
							}
						}
					}
				}
				
				jdSaleAttr.setSkuIds(skuIds);
				jdSaleAttr.setSkuIdStr(StringUtils.join(skuIdStrList.toArray(), ","));
				saleAttrList.add(jdSaleAttr);
			}
			jdSimilarSku.setSaleAttrList(saleAttrList);
			jdSimilarSkuList.add(jdSimilarSku);
		}
		//根据ProCouponVo里面的开始时间排序
		Collections.sort(jdSimilarSkuList,new Comparator<JdSimilarSku>(){
			@Override
			public int compare(JdSimilarSku o1, JdSimilarSku o2) {
				if(o1.getDim() > o2.getDim()){
					return 1;
				}else{
					return -1;
				}
			}
			
		});
		return jdSimilarSkuList;
	}
  /**
   * 获取商品基本信息
   *
   * @param goodsId
   * @return
   * @throws BusinessException
   */
  public void loadGoodsBasicInfoById(Long goodsId, Map<String, Object> returnMap) throws BusinessException {
    GoodsInfoEntity goodsBasicInfo = goodsDao.select(goodsId);
    Long totalCurrentAmt = 0L;
    Long CurrentAmtDesc = 11L;
    if (null == goodsBasicInfo) {
      LOGGER.error("商品信息不存在:{}", goodsId);
      throw new BusinessException("商品信息不存在");
    }
    Date now = new Date();
    boolean ifHasDelistTime = false;
    if(goodsBasicInfo.getDelistTime() != null){
        ifHasDelistTime = now.after(goodsBasicInfo.getDelistTime());

    }
    if (now.before(goodsBasicInfo.getListTime()) || ifHasDelistTime
        || !GoodStatus.GOOD_UP.getCode().equals(goodsBasicInfo.getStatus())) {
      goodsBasicInfo.setStatus(GoodStatus.GOOD_DOWN.getCode());
    }
    List<GoodsStockInfoEntity> goodsList = goodsStockDao.loadByGoodsId(goodsId);
    boolean offShelfFlag = true;
    for (GoodsStockInfoEntity goodsStock : goodsList) {
      if (goodsStock.getStockCurrAmt() > 0) {
        offShelfFlag = false;
        break;
      }
    }
    if (offShelfFlag) {
      goodsBasicInfo.setStatus(GoodStatus.GOOD_DOWN.getCode());
    }

    goodsBasicInfo.setGoodsLogoUrlNew(imageService.getImageUrl(goodsBasicInfo.getGoodsLogoUrl()));
    goodsBasicInfo.setGoodsSiftUrlNew(imageService.getImageUrl(goodsBasicInfo.getGoodsSiftUrl()));

    // 20170322
    goodsBasicInfo.setGoodsLogoUrl(EncodeUtils.base64Encode(goodsBasicInfo.getGoodsLogoUrl()));
    goodsBasicInfo.setGoodsSiftUrl(EncodeUtils.base64Encode(goodsBasicInfo.getGoodsSiftUrl()));

    returnMap.put("goodsBasicInfo", goodsBasicInfo);
    List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
    for (GoodsStockInfoEntity goodsStock : goodsStockList) {
      BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(),
          goodsStock.getGoodsStockId());
      goodsStock.setGoodsPrice(price);
      goodsStock.setGoodsPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2,
          BigDecimal.ROUND_DOWN));// 对接京东后新增字段（商品首付价）
      totalCurrentAmt += goodsStock.getStockCurrAmt();
      // 20170322

      goodsStock.setStockLogoNew(imageService.getImageUrl(goodsStock.getStockLogo()));
      goodsStock.setStockLogo(EncodeUtils.base64Encode(goodsStock.getStockLogo()));
      // 接入京东商品修改
      if (CurrentAmtDesc - goodsStock.getStockCurrAmt() > 0) {
        goodsStock.setStockCurrAmtDesc("库存紧张");
      } else {
        goodsStock.setStockCurrAmtDesc("库存充足");
      }
    }
    //返回活动id
    ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getByGoodsId(goodsId);
    if(null !=proGroupGoodsBo && proGroupGoodsBo.isValidActivity()){
        returnMap.put("proActivityId",proGroupGoodsBo.getActivityId());
    }
	//获取商品的优惠券
    List<String> proCoupons=jdGoodsInfoService.getProCouponList(goodsId);
	if(proCoupons.size()>3){
		returnMap.put("proCouponList",proCoupons.subList(0, 3));
	}else{
		 returnMap.put("proCouponList",proCoupons);
	}
    returnMap.put("totalCurrentAmt", totalCurrentAmt);
    returnMap.put("support7dRefund", goodsBasicInfo.getSupport7dRefund());//是否支持7天无理由退货,Y、N
    returnMap.put("activityCfg",getActivityInfo(goodsId));// 满减活动字段
    returnMap.put("goodsStockList", goodsStockList);
    returnMap.put("unSupportProvince", goodsBasicInfo.getUnSupportProvince());
    returnMap.put("postage", "0");// 电商3期511 添加邮费字段（当邮费为0时显示免运费） 20170517
    List<BannerInfoEntity> goodsBannerList = bannerInfoDao.loadIndexBanners(String.valueOf(goodsId));
    // 20170322
    for (BannerInfoEntity banner : goodsBannerList) {
      banner.setActivityUrl(EncodeUtils.base64Encode(banner.getActivityUrl()));
      banner.setBannerImgUrlNew(imageService.getImageUrl(banner.getBannerImgUrl()));
      banner.setBannerImgUrl(EncodeUtils.base64Encode(banner.getBannerImgUrl()));
    }

    returnMap.put("goodsBannerList", goodsBannerList);
    BigDecimal maxPrice = BigDecimal.ZERO;
    BigDecimal minPrice = BigDecimal.ZERO;
    if (null != goodsStockList && goodsStockList.size() > 0) {
      minPrice = goodsStockList.get(0).getGoodsPrice();
      for (GoodsStockInfoEntity stock : goodsStockList) {
        if (stock.getGoodsPrice().compareTo(maxPrice) > 0) {
          maxPrice = stock.getGoodsPrice();
        }
        if (minPrice.compareTo(stock.getGoodsPrice()) > 0) {
          minPrice = stock.getGoodsPrice();
        }
      }
      returnMap.put("minPrice", minPrice);
      returnMap.put("maxPrice", maxPrice);
    }
    // 接入京东商品修改 //计算首付价
    BigDecimal minPriceFistPayment = new BigDecimal("0.1").multiply(minPrice).setScale(2,
        BigDecimal.ROUND_DOWN);
    returnMap.put("minPriceFirstPayment", minPriceFistPayment);
    returnMap.put("source", "notJd");
  }

	// 获取非京东商品的最小价格对象及价格
	public Map<String,Object> getMinPriceNotJdGoods(Long goodsId) throws BusinessException {
		Map<String,Object> resultMap=new HashMap<>();
		List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
		GoodsStockInfoEntity goodsStock = null;
		BigDecimal minPrice = null;
		if (CollectionUtils.isNotEmpty(goodsStockList)) {
			minPrice = commonService.calculateGoodsPrice(goodsId, goodsStockList.get(0).getGoodsStockId());
			goodsStock=goodsStockList.get(0);
			for (GoodsStockInfoEntity stock : goodsStockList) {
				BigDecimal goodsPrice = commonService.calculateGoodsPrice(goodsId, stock.getGoodsStockId());
				if (minPrice.compareTo(goodsPrice) > 0 && stock.getStockCurrAmt() >0) {
					minPrice = goodsPrice;
					goodsStock = stock;
				}
			}
		}
		resultMap.put("minPrice", minPrice);
		resultMap.put("goodsStock", goodsStock);
		return resultMap;
	}
  /**
   * (满减活动)通过goodsId查看该商品是否参加有效活动，如果参加返回相关数据
   */
  public String getActivityInfo(Long goodsId){
      ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getByGoodsId(goodsId);
      String activityCfgDesc="";
      if(null !=proGroupGoodsBo && proGroupGoodsBo.isValidActivity()){
          ProActivityCfg activityCfg = activityCfgService.getById(proGroupGoodsBo.getActivityId());
          if(null !=activityCfg && activityCfg.getActivityType().equals("Y")){
              if(null !=activityCfg.getOfferSill1() && null !=activityCfg.getDiscountAmonut1()){
                  String  offer1   =activityCfg.getOfferSill1().toString();
                  String  discount1=activityCfg.getDiscountAmonut1().toString();
                  activityCfgDesc="满"+offer1+"元，支付立减"+discount1+"元现金\n";
              }
              if(null !=activityCfg.getOfferSill2() && null !=activityCfg.getDiscountAmount2()){
                  String  offer2   =activityCfg.getOfferSill2().toString();
                  String  discount2=activityCfg.getDiscountAmount2().toString();
                  activityCfgDesc=activityCfgDesc+"满"+offer2+"元，支付立减"+discount2+"元现金";
              }
              return activityCfgDesc;
            }
      }
      return null;
  }

    /**
     * (满减活动)通过activityId查看该商品是否参加有效活动，如果参加返回相关数据
     */
    public Map<String,Object> getActivityInfoByActivityId(Long activityId) {
        Map<String,Object> resultMap=new HashMap<>();
        String activityCfgDesc = "";
        ProActivityCfg activityCfg = activityCfgService.getById(activityId);
        if (activityCfg == null) {
            return null;
        }
        if (ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg)) {//活动在进行中
            if (null != activityCfg.getOfferSill1() && null != activityCfg.getDiscountAmonut1()) {
                String offer1 = activityCfg.getOfferSill1().toString();
                String discount1 = activityCfg.getDiscountAmonut1().toString();
                activityCfgDesc = "满" + offer1 + "元，支付立减" + discount1 + "元现金\n";
                resultMap.put("offerSill1", offer1);
                resultMap.put("discountAmonut1", discount1);
            }
            if (null != activityCfg.getOfferSill2() && null != activityCfg.getDiscountAmount2()) {
                String offer2 = activityCfg.getOfferSill2().toString();
                String discount2 = activityCfg.getDiscountAmount2().toString();
                activityCfgDesc = activityCfgDesc + "满" + offer2 + "元，支付立减" + discount2 + "元现金";
                resultMap.put("offerSill2", offer2);
                resultMap.put("discountAmonut2", discount2);
            }
            resultMap.put("activityCfgDesc", activityCfgDesc);
            return resultMap;
        }
        return null;
    }
    /**
     * (满减活动购物车列表)通过activityId查看该商品是否参加有效活动，如果参加返回相关数据
     */
    public Map<String,Object> getCarActivityInfoByActivityId(Long activityId) {
        Map<String,Object> resultMap=new HashMap<>();
        String activityCfgDesc = "";
        ProActivityCfg activityCfg = activityCfgService.getById(activityId);
        if (activityCfg == null) {
            return null;
        }
        if (ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg)) {//活动在进行中
            if (null != activityCfg.getOfferSill1() && null != activityCfg.getDiscountAmonut1()) {
                String offer1 = activityCfg.getOfferSill1().toString();
                String discount1 = activityCfg.getDiscountAmonut1().toString();
                activityCfgDesc = "满" + offer1 + "元减" + discount1 + "元";
                resultMap.put("offerSill1", offer1);
                resultMap.put("discountAmonut1", discount1);
            }
            if (null != activityCfg.getOfferSill2() && null != activityCfg.getDiscountAmount2()) {
                String offer2 = activityCfg.getOfferSill2().toString();
                String discount2 = activityCfg.getDiscountAmount2().toString();
                activityCfgDesc = activityCfgDesc + "，满" + offer2 + "元减" + discount2 + "元";
                resultMap.put("offerSill2", offer2);
                resultMap.put("discountAmonut2", discount2);
            }
            resultMap.put("activityCfgDesc", activityCfgDesc);
            return resultMap;
        }
        return null;
    }
    /**
     * 京东商品是否支持7天无理由退货,Y、N
     */
    public String getsupport7dRefund(Long skuId) {
        String value = "N";
        List<com.apass.esp.third.party.weizhi.entity.SkuNum> skuNumList = new ArrayList<>();
        com.apass.esp.third.party.weizhi.entity.SkuNum skuNum = new com.apass.esp.third.party.weizhi.entity.SkuNum();
        skuNum.setNum(1);
        skuNum.setSkuId(skuId);
        skuNumList.add(skuNum);
        // 验证京东商品是否支持7天退货
        if (orderService.checkGoodsIs7ToReturn(skuNumList)) {
            value = "Y";
        }
        return value;
    }
  /**
   * 获取商品最低价所对应的库存id
   *
   * @param goodsId
   * @return
   * @throws BusinessException
   */
  public Map<String, Object> getMinPriceGoods(Long goodsId) throws BusinessException {
    Map<String, Object> map = new HashMap<>();
    List<GoodsStockInfoEntity> goodsStockList = goodsStockDao.loadByGoodsId(goodsId);
    for (GoodsStockInfoEntity goodsStock : goodsStockList) {
      BigDecimal price = commonService.calculateGoodsPrice(goodsStock.getGoodsId(),
          goodsStock.getGoodsStockId());
      goodsStock.setGoodsPrice(price);
    }
    // BigDecimal maxPrice = BigDecimal.ZERO;
    BigDecimal minPrice = BigDecimal.ZERO;
    String skuAttr = null;
    Long minPriceStockId = 0L;
    // Long maxPriceStockId = 0L;
    if (null != goodsStockList && goodsStockList.size() > 0) {
      minPrice = goodsStockList.get(0).getGoodsPrice();
      for (GoodsStockInfoEntity stock : goodsStockList) {
        // if (stock.getGoodsPrice().compareTo(maxPrice) > 0) {
        // maxPrice = stock.getGoodsPrice();
        // maxPriceStockId = stock.getId();
        // }
        if (minPrice.compareTo(stock.getGoodsPrice()) >= 0) {
          minPrice = stock.getGoodsPrice();
          minPriceStockId = stock.getId();
          skuAttr = stock.getGoodsSkuAttr();
        }
      }
      map.put("minPrice", minPrice);
      map.put("minPriceStockId", minPriceStockId);
      map.put("minSkuAttr", skuAttr);
      // map.put("maxPrice", maxPrice);
      // map.put("maxPriceStockId", maxPriceStockId);
    }
    return map;
  }

  /**
   * 获取商品详细信息(尺寸规格价格大小等)
   *
   * @param goodsId
   * @return
   */
  public List<GoodsStockInfoEntity> loadDetailInfoByGoodsId(Long goodsId) {
    return goodsStockDao.loadByGoodsId(goodsId);
  }

  /**
   * 商品基本信息+商户信息+库存信息
   *
   * @param goodsId
   * @param goodsStockId
   * @return
   */
  public GoodsDetailInfoEntity loadContainGoodsAndGoodsStockAndMerchant(Long goodsStockId) {
    return goodsDao.loadContainGoodsAndGoodsStockAndMerchant(goodsStockId);
  }

  /**
   * 获取商品当前库存量
   *
   * @param goodsStockId
   * @return
   */
  public Long getStockCurrAmt(Long goodsStockId) {
    return goodsStockDao.getStockCurrAmt(goodsStockId);
  }

  /**
   * 商品分页(查询)
   *
   * @param goodsInfoEntity
   * @param pageNo
   * @param pageSize
   * @return
   */
  public PaginationManage<GoodsInfoEntity> pageList(GoodsInfoEntity goodsInfoEntity, String pageNo,
                                                    String pageSize) {
    Integer pageNum = Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo);
    Integer pageSiz = Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize);
    Integer begin = (pageNum - 1) * pageSiz;
    goodsInfoEntity.setBegin(begin);
    goodsInfoEntity.setPageSize(pageSiz);
    // Page page = new Page();
    // page.setPage(Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo));
    // page.setLimit(Integer.valueOf(pageSize) <= 0 ? 1 : Integer.valueOf(pageSize));

    PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();

    List<GoodsInfoEntity> dataList = goodsDao.pageList(goodsInfoEntity);
    Integer totalCount = goodsDao.countByKey(goodsInfoEntity, "goodsPageList");
    for (GoodsInfoEntity goodsInfo : dataList) {
      if ("jd".equals(goodsInfo.getSource())) {
        goodsInfo.setMerchantName("京东");
      }
      if (null != goodsInfo.getListTime()) {
        goodsInfo.setListTimeString(goodsInfo.getListTime());
      }
      if (null != goodsInfo.getDelistTime()) {
        goodsInfo.setDelistTimeString(goodsInfo.getDelistTime());
      }
    }

    result.setDataList(dataList);
    result.setTotalCount(totalCount);
    return result;
  }

  /**
   * 商品(查询)
   *
   * @param goodsInfoEntity
   * @param pageNo
   * @param pageSize
   * @return
   */
  public List<GoodsInfoEntity> pageList(GoodsInfoEntity goodsInfoEntity) {
    return goodsDao.pageList(goodsInfoEntity);
  }
  /**
   *  查询非京东的商品
   */
  public List<GoodsInfoEntity> getNotJDgoodsList() {
      return goodsDao.getNotJDgoodsList();
  }
  /**
   * 新增
   *
   * @param entity
   */
  @Transactional(rollbackFor = Exception.class)
  public GoodsInfoEntity insert(GoodsInfoEntity entity) {

    if (entity.getGoodId() != null) {
      entity.setId(entity.getGoodId());
      updateService(entity);
      return entity;
    }
    int count = goodsDao.insert(entity);
    entity.setGoodId(entity.getId());

    if (count == 1) {
      LOGGER.info("保存商品成功,保存内容：{}", GsonUtils.toJson(entity));
      // 商品编号
      StringBuffer sb = new StringBuffer();
      String merchantCode = entity.getMerchantCode();
      MerchantInfoEntity merchantInfoEntity = merchantInforService.queryByMerchantCode(merchantCode);
      if (merchantInfoEntity != null) {
        String merchantId = String.valueOf(merchantInfoEntity.getId());
        if (merchantId.length() == 1) {
          merchantId = "0" + merchantId;
        } else if (merchantId.length() > 1) {
          merchantId = merchantId.substring(merchantId.length() - 2, merchantId.length());
        }
        sb.append(merchantId);
        String random = RandomUtils.getNum(8);
        sb.append(random);
        entity.setGoodsCode(sb.toString());
        goodsDao.updateGoods(entity);

      }
    }

    return entity;
  }

  /**
   * 修改
   *
   * @param entity
   */
  @Transactional(rollbackFor = Exception.class)
  public Integer updateService(GoodsInfoEntity entity) {
    return goodsDao.updateGoods(entity);
  }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateServiceForBaseInfoColler(GoodsInfoEntity entity) {
        return goodsDao.updateServiceForBaseInfoColler(entity);
    }

  /**
   * 主键查询
   *
   * @param goodsId
   * @return
   */
  public GoodsInfoEntity selectByGoodsId(Long goodsId) {
    return goodsDao.select(goodsId);
  }

  /**
   * 说明：GoodsList
   *
   * @param goodsInfoEntity
   * @param page
   * @return
   * @author xiaohai
   * @time：2016年12月20日 下午2:01:10
   */
  public PaginationManage<GoodsInfoEntity> page(GoodsInfoEntity goodsInfoEntity, Page page) {
    PaginationManage<GoodsInfoEntity> result = new PaginationManage<GoodsInfoEntity>();
    Pagination<GoodsInfoEntity> response = goodsDao.pageForSiftList(goodsInfoEntity, page);

    result.setDataList(response.getDataList());
    result.setPageInfo(page.getPageNo(), page.getPageSize());
    result.setTotalCount(response.getTotalCount());
    return result;
  }
  /**
   * 精选商品列表
   * @param goodsInfoEntity
   * @return
   */
  public List<GoodsInfoEntity> goodsSiftList(GoodsInfoEntity entity) {
      return goodsDao.goodsSiftList(entity);
  }
  /**
   * 说明：查询商品精选数量
   *
   * @return
   * @author xiaohai
   * @time：2016年12月27日 下午3:55:45
   */
  public Integer goodsPageListCount() {
    return goodsDao.goodsPageListCount();
  }

  // 判断费率
  public String ifRate(Long goodsId, BigDecimal merchantSettleRate) {
    List<GoodsStockInfoEntity> list = goodsStockDao.loadByGoodsId(goodsId);
    if (!list.isEmpty()) {
      for (GoodsStockInfoEntity goodsStockInfoEntity : list) {
        if (goodsStockInfoEntity.getMarketPrice() == null
            || goodsStockInfoEntity.getMarketPrice().compareTo(BigDecimal.ZERO) == 0) {
          continue;
        }
        BigDecimal flagtRate = goodsStockInfoEntity.getGoodsCostPrice().divide(
            goodsStockInfoEntity.getMarketPrice(), 6, RoundingMode.HALF_UP);
        if (flagtRate.compareTo(merchantSettleRate) != -1) {
          return "1";
        }
      }
    }
    return "0";
  }

  /**
   * 校验商品下架时间，更新商品状态
   *
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public void updateGoodsStatusByDelisttime() {
    goodsDao.updateGoodsStatusByDelisttime();
  }

  @Transactional(rollbackFor = Exception.class)
  public Integer updateServiceEdit(GoodsInfoEntity dto, String goodsContent) {
    if (StringUtils.isBlank(goodsContent)) {
      dto.setGoogsDetail(null);
    }

    return goodsDao.updateGoodsEdit(dto);
  }

  /**
   * 查询所属分类下属的商品的数量（status!=G03 并且 is_delete !='00'）
   *
   * @return
   */
  public int getBelongCategoryGoodsNumber(long categoryId) {
    return goodsDao.getBelongCategoryGoodsNumber(categoryId);
  }

  public List<GoodsInfoEntity> getBelongCategoryGoods(long categoryId) {
    return goodsDao.getBelongCategoryGoods(categoryId);
  }

  /**
   * 根据类目id查询其类目下所有已经下架了的商品信息
   *
   * @param id
   * @return
   */
  public List<GoodsInfoEntity> getDownCategoryGoodsByCategoryId(long id) {
    return goodsDao.getDownCategoryGoodsByCategoryId(id);
  }

  /**
   * 更新已经下架商品的类目
   *
   * @param id
   */
  public void updateGoodsCategoryStatus(Long id) {
    goodsDao.updateGoodsCategoryStatus(id);
  }

  /**
   * 京东商品
   *
   * @param entity
   * @return
   */
  public GoodsInfoEntity insertJdGoods(GoodsInfoEntity entity) {
    goodsDao.insert(entity);
    entity.setGoodId(entity.getId());
    return entity;
  }

  /**
   * @param ids
   */
  public void deleteJDGoodsBatch(List<String> ids) {
    goodsDao.deleteJDGoodsBatch(ids);
  }

  /**
   * 根据external_id查询商品
   *
   * @param externalId
   * @return
   * @throws BusinessException
   */
  public GoodsInfoEntity selectGoodsByExternalId(String externalId) {
    return goodsDao.selectGoodsByExternalId(externalId);
  }

  public Pagination<String> jdGoodSalesVolumeByPage(int pageIndex, int pageSize) {
    // int totalConut = jdGoodSalesVolumeMapper.jdGoodSalesVolumeCount();
    if (pageIndex == 3) {
      pageSize = 10;
    }
    int pageBegin = pageSize * (pageIndex - 1);
    List<String> list = jdGoodSalesVolumeMapper.jdGoodSalesVolumeByPage(pageBegin, pageSize);
    Pagination<String> pagination = new Pagination<String>();
    pagination.setDataList(list);
    pagination.setTotalCount(50);
    return pagination;
  }

  public Pagination<String> jdGoodSalesVolume(int pageIndex, int pageSize) {
    Pagination<String> pagination = new Pagination<String>();
    int totalConut = goodsBasicRepository.popularGoodsCount();// 热卖单品数量
    int remainderGoodsNewCount = goodsBasicRepository.getRemainderGoodsNewCount();// 必买单品数量
        /*
         * if(remainderGoodsNewCount+totalConut<50){ pagination.setDataList(Collections.EMPTY_LIST);
         * return pagination; }
         */
    int pageBegin = pageSize * (pageIndex - 1);

    // 热卖单品大于170件时 全从热卖单品里取数据
    if (totalConut >= 170) {
      List<String> list = goodsBasicRepository.popularGoods(50 + pageBegin, pageSize);
      pagination.setDataList(list);
    }
    // 热卖单品大于50，小于170时
    // 部分数据可能从热卖单品中取
    if (totalConut >= 50 && totalConut < 170) {
      List<String> list = goodsBasicRepository.popularGoods(50 + pageBegin, pageSize);
      if (CollectionUtils.isEmpty(list) || list.size() != 20) {
        List<String> s = goodsBasicRepository.getRemainderGoodsNew(pageBegin, pageSize);
        pagination.setDataList(s);
      } else {
        pagination.setDataList(list);
      }
    }
    if (totalConut < 50) {
      List<String> s = goodsBasicRepository.getRemainderGoodsNew(50 - totalConut + pageBegin, pageSize);
      pagination.setDataList(s);
    }
    pagination.setTotalCount(120);
    return pagination;
  }

  /**
   * 根据二级类目id查询所有商品
   *
   * @param categoryId
   * @return
   */
  public List<GoodsInfoEntity> selectByCategoryId2(Long categoryId) {
    return goodsDao.selectByCategoryId2(categoryId);
  }
    /**
     * 根据一级类目id查询所有商品
     *
     * @param categoryId
     * @return
     */
    public List<GoodsInfoEntity> selectByCategoryId1(Long categoryId) {
        return goodsDao.selectByCategoryId1(categoryId);
    }

  /**
   * 判断该类目下京东是否存在已上架待审核状态商品
   *
   * @param cateId:京东的三级类目，状态在sql语句中写死(G01,G02,G04)
   * @return
   */
  public boolean selectGoodsByCatId(String cateId) {
    LOGGER.info("京东类目id：{}", cateId);
    boolean b = false;
    JdCategory jdCategory = jdCategoryMapper.getCateGoryByCatId(Long.valueOf(cateId));
    if (jdCategory == null) {
      throw new RuntimeException("数据有误");
    }
    List<JdGoods> jdGoods = jdGoodsMapper.queryGoodsByThirdCateId(jdCategory.getCatId().toString());
    for (JdGoods jdGood : jdGoods) {
      GoodsInfoEntity goodsEntity = goodsDao.selectGoodsByExternalIdAndStatus(jdGood.getSkuId());
      if (goodsEntity != null) {
        LOGGER.info("上架或待审核商品：{}", GsonUtils.toJson(goodsEntity));
        b = true;
        break;
      }
    }
    return b;
  }

  /**
   * 获取单个商品或订单的邮费
   *
   * @param goodsIds 商品id列表
   * @return postage为0时免运费
   * @throws BusinessException
   */
  public BigDecimal getPostage(List<Long> goodsIds) throws BusinessException {
    ValidateUtils.isNullObject(goodsIds, "商品id不能为空");

    BigDecimal goodsPrices = new BigDecimal(0);
    BigDecimal postage = new BigDecimal(0);
    for (Long goodsId : goodsIds) {
      List<GoodsStockSkuDto> goodsStockSkuInfo = goodsStockDao.getGoodsStockSkuInfo(goodsId);
      if (goodsStockSkuInfo != null) {
        for (GoodsStockSkuDto goodsStockSkuDto : goodsStockSkuInfo) {
          if (goodsStockSkuDto.getGoodsPrice() == null) {
            throw new BusinessException("数据有误，goodsId:{}", goodsId.toString());
          }
          goodsPrices = goodsPrices.add(goodsStockSkuDto.getGoodsPrice());
        }
      }
    }

    if (goodsPrices.compareTo(new BigDecimal(99)) > 0) {
      postage = new BigDecimal(6);
    }

    return postage;
  }

  public List<String> popularGoods(int begin, int count) {
    return goodsBasicRepository.popularGoods(begin, count);
  }

  public int popularGoodsCount() {
    return goodsBasicRepository.popularGoodsCount();
  }

  public List<String> getRemainderGoodsNew(int pageIndex, int pageSize) {
    return goodsBasicRepository.getRemainderGoodsNew(pageIndex, pageSize);
  }

  public List<GoodsInfoEntity> pageListForExport(GoodsInfoEntity goodsInfoEntity) {
    return goodsDao.pageListForExport(goodsInfoEntity);
  }

  /**
   * 获取上架的商品 <br/>
   * 2017-08-16
   *
   * @return
   */
  public List<GoodsInfoEntity> selectUpGoods(int index, int size) {
    return goodsDao.selectUpGoods(index, size);
  }

  public List<GoodsInfoEntity> selectJdGoods(int index, int size,SourceType sourceType) {
    return goodsDao.selectJdGoods(index, size,sourceType.getCode());
  }

  public List<Goods> esInit(int index, int size) {
    List<GoodsInfoEntity> selectByCategoryId2 = selectUpGoods(index, size);
    if (CollectionUtils.isEmpty(selectByCategoryId2)) {
      return Collections.EMPTY_LIST;
    }
    return getGoodsList(selectByCategoryId2);
  }

  public List<Goods> getGoodsList(List<GoodsInfoEntity> selectByCategoryId2) {
    List<Goods> goodsList = new ArrayList<>();
    for (GoodsInfoEntity g : selectByCategoryId2) {
      Goods goods = goodsInfoToGoods(g);
      if (null == goods) {
        continue;
      }
      LOGGER.info("goodsList add goodsId {} ...", goods.getId());
      goodsList.add(goods);
    }
    LOGGER.info("goodsList add goodsId {} ...", goodsList.size());
    return goodsList;
  }

  public Goods goodsInfoToGoods(GoodsInfoEntity g) {
    if (g == null) {
      return null;
    }
    Goods goods = new Goods();
    try {
      LOGGER.info("goodsInfoToGoods被调用了:{}", GsonUtils.toJson(g));
      goods.setId(Integer.valueOf(g.getId() + ""));
      goods.setGoodId(g.getGoodId());
      goods.setCategoryId1(g.getCategoryId1());
      Category cate1 = categoryMapper.selectByPrimaryKey(g.getCategoryId1());
      if (null != cate1) {

        goods.setCategoryName1(cate1.getCategoryName());
        goods.setCategoryId1(g.getCategoryId1());
        goods.setCategoryName1Pinyin(Pinyin4jUtil.converterToSpell(cate1.getCategoryName()));
      }

      Category cate2 = categoryMapper.selectByPrimaryKey(g.getCategoryId2());
      if (null != cate2) {

        goods.setCategoryName2(cate2.getCategoryName());
        goods.setCategoryId2(g.getCategoryId2());
        goods.setCategoryName2Pinyin(Pinyin4jUtil.converterToSpell(cate2.getCategoryName()));
      }
      Category cate3 = categoryMapper.selectByPrimaryKey(g.getCategoryId3());
      if (null != cate3) {
        goods.setCategoryId3(g.getCategoryId3());
        goods.setCategoryName3(cate3.getCategoryName());
        goods.setCategoryName3Pinyin(Pinyin4jUtil.converterToSpell(cate3.getCategoryName()));
      }

      goods.setGoodsName(g.getGoodsName());
      goods.setGoodsNamePinyin(Pinyin4jUtil.converterToSpell(g.getGoodsName()));
      goods.setDelistTime(g.getDelistTime());
      goods.setGoodsLogoUrl(g.getGoodsLogoUrl());
      if (StringUtils.equals(g.getSource(), SourceType.WZ.getCode())) {
        goods.setGoodsLogoUrlNew("http://img13.360buyimg.com/n1/" + g.getGoodsLogoUrl());
      } else {
        try {
          goods.setGoodsLogoUrlNew(imageService.getImageUrl(g.getGoodsLogoUrl()));
        } catch (BusinessException e) {
          goods.setGoodsLogoUrlNew("");
        }
      }

      goods.setSource(g.getSource());
      goods.setDelistTimeString(DateFormatUtil.dateToString(g.getDelistTime(), ""));
      if(g.getSordNo() == null){
        goods.setSordNo(0);
      }else {
        goods.setSordNo(g.getSordNo());
      }
      goods.setCreateDate(g.getCreateDate());
      goods.setGoodsTitle(g.getGoodsTitle());
      goods.setGoodsTitlePinyin(Pinyin4jUtil.converterToSpell(g.getGoodsTitle()));
      goods.setListTime(g.getListTime());
      goods.setListTimeString(DateFormatUtil.dateToString(g.getListTime(), ""));
      goods.setNewCreatDate(g.getNewCreatDate());
      goods.setUpdateDate(g.getUpdateDate());

      List<JdGoodSalesVolume> getJdGoodSalesVolume = jdGoodSalesVolumeMapper
          .getJdGoodSalesVolumeByGoodsId(g.getGoodId());

      int goodsSum = 0;
      int goodsSum30 = 0;
      Date date = new Date();
      for (JdGoodSalesVolume jd : getJdGoodSalesVolume) {
        goodsSum += jd.getSalesNum();
        if (jd.getCreateDate().before(date)
            && jd.getCreateDate().after(DateFormatUtil.addDays(date, -30))) {
          goodsSum30 += jd.getSalesNum();
        }
      }
      goods.setSaleNum(goodsSum);
      goods.setSaleNumFor30(goodsSum30);

    } catch (Exception e) {
      LOGGER.error("goodsInfoToGoods error---{}", e);
      return null;
    }

    try {
      Map<String, Object> params = getMinPriceGoods(g.getGoodId());
      if (params.isEmpty()) {
        return null;
      }
      goods.setGoodsPrice(new BigDecimal(String.valueOf(params.get("minPrice"))));
      goods.setFirstPrice(new BigDecimal(String.valueOf(params.get("minPrice"))).multiply(
          new BigDecimal(0.1)).setScale(2, BigDecimal.ROUND_DOWN));
      goods.setGoodsStockId(Long.valueOf(String.valueOf(params.get("minPriceStockId"))));
      if (StringUtils.equals(goods.getSource(), SourceType.WZ.getCode())) {
        Map<String, Object> descMap = new HashMap<String, Object>();
        try {
          descMap = jdGoodsInfoService.getJdGoodsSimilarSku(Long.valueOf(g.getExternalId()));
          String jdGoodsSimilarSku = (String) descMap.get("jdGoodsSimilarSku");
          int jdSimilarSkuListSize = (int) descMap.get("jdSimilarSkuListSize");
          if (StringUtils.isNotBlank(jdGoodsSimilarSku) && jdSimilarSkuListSize > 0) {
            goods.setGoodsSkuAttr(jdGoodsSimilarSku);
          }
        } catch (Exception e) {
          LOGGER.error("-------  jdGoodsSimilarSku has error when building es's index--------", e);
        }
      } else {
        goods.setGoodsSkuAttr(String.valueOf(params.get("minSkuAttr")));
      }
      if (StringUtils.isBlank(goods.getGoodsSkuAttr())) {
        goods.setGoodsSkuAttr("");
      }
      if (StringUtils.isNotEmpty(goods.getGoodsSkuAttr())) {
        goods.setGoodsSkuAttrPinyin(Pinyin4jUtil.converterToSpell(goods.getGoodsSkuAttr()));
      } else {
        goods.setGoodsSkuAttrPinyin("");
      }
      if (SourceType.WZ.getCode().equalsIgnoreCase(g.getSource())) {
    	Product product =   productApiClient.getWeiZhiProductDetail(g.getExternalId());
    	if(product != null){
    		goods.setGoodsDetail(product.getIntroduction());
    	}
//        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.productDetailQuery(Long.valueOf(g.getExternalId()));
//        JSONObject jsonObject = jdApiResponse.getResult();
//        if (jdApiResponse.isSuccess() && jdApiResponse != null && jsonObject != null) {
//          String introduction = (String) jsonObject.get("introduction");
//          goods.setGoodsDetail(introduction);
//        }
      }

    } catch (Exception e) {
      LOGGER.error("-----exception-------{}", e);
      return null;
    }
    return goods;

  }
  /**
   * 当传入的param在数据库中查出了多条数据时，则认为该条数据导入失败
   * @param param
   * @return
   */
  public GoodsBasicInfoEntity getByGoodsBySkuIdOrGoodsCode(String param,SourceType sourceType) {
    GoodsBasicInfoEntity entity = new GoodsBasicInfoEntity();
    entity.setGoodsCode(Long.parseLong(param));
    entity.setExternalId(param);
    entity.setSource(sourceType.getCode());
    List<GoodsBasicInfoEntity> result=goodsBasicRepository.searchGoodsBySkuIdOrGoodsCode(entity);
    if(null !=result && result.size()==1){
        return result.get(0);
    }
    return null;
  }

    /**
     * 添加banner使用
     * @param param
     * @return
     */
    public GoodsBasicInfoEntity getByGoodsBySkuIdOrGoodsCode2(String param,SourceType sourceType) {
        GoodsBasicInfoEntity entity = new GoodsBasicInfoEntity();
        entity.setGoodsCode(Long.parseLong(param));
        entity.setExternalId(param);
        entity.setSource(sourceType.getCode());
        return goodsBasicRepository.searchGoodsBySkuIdOrGoodsCode(entity).get(0);
    }


    public List<GoodsInfoEntity> selectByCategoryId2AndsordNo(Map<String,Object> params) {
        return goodsDao.selectByCategoryId2AndsordNo(params);
    }

    public GoodsInfoEntity selectGoodsByGoodsCode(String goodsCode) {
        return goodsDao.selectGoodsByGoodsCode(goodsCode);
    }

    public List<GoodsInfoEntity> getGoodsListBySkuIds(List<String> skuIdList) {
        return goodsDao.getGoodsListBySkuIds(skuIdList);
    }
}
