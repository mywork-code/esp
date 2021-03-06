package com.apass.esp.service.jd;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.apass.esp.domain.enums.*;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.esp.third.party.weizhi.client.WeiZhiConstants;
import com.apass.gfb.framework.cache.CacheManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.activity.LimitGoodsSkuVo;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdCss;
import com.apass.esp.domain.entity.jd.JdGoodStock;
import com.apass.esp.domain.entity.jd.JdGoods;
import com.apass.esp.domain.entity.jd.JdGoodsBooks;
import com.apass.esp.domain.entity.jd.JdImage;
import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.domain.entity.jd.JdSaleAttr;
import com.apass.esp.domain.entity.jd.JdSellPrice;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.domain.entity.jd.JdSimilarSkuTo;
import com.apass.esp.domain.entity.jd.JdSimilarSkuVo;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.domain.vo.ProCouponGoodsDetailVo;
import com.apass.esp.domain.vo.ProCouponVo;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.activity.LimitCommonService;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.CouponRelService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.service.wz.WeiZhiProductService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.jd.entity.product.Stock;
import com.apass.esp.third.party.weizhi.client.WeiZhiOrderApiClient;
import com.apass.esp.third.party.weizhi.client.WeiZhiPriceApiClient;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.third.party.weizhi.entity.GoodsStock;
import com.apass.esp.third.party.weizhi.entity.StockNum;
import com.apass.esp.third.party.weizhi.entity.WzPicture;
import com.apass.esp.third.party.weizhi.entity.WzSkuPicture;
import com.apass.esp.third.party.weizhi.response.WZPriceResponse;
import com.apass.gfb.framework.exception.BusinessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * @author zengqingshan
 */
@Service
public class JdGoodsInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(JdGoodsInfoService.class);
	
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private  GoodsService  goodsService;
	@Autowired
	private GoodsStockInfoRepository goodsStockInfoRepository;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProGroupGoodsService proGroupGoodsService;
	@Autowired
	private CouponManagerService couponManagerService;
	@Autowired
	private ActivityCfgService activityCfgService;
    @Autowired
    private MyCouponManagerService myCouponManagerService;
    @Autowired
    private CouponRelService couponRelService;
    @Autowired
    private ProMyCouponMapper myCouponMapper;
    @Autowired
    private ProCouponMapper proCouponMapper;
    @Autowired
    private GoodsRepository goodsDao;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private GoodsRepository goodsMapper;
	@Autowired
	private ProCouponRelMapper couponRelMapper;
	@Autowired
	private ProActivityCfgMapper activityCfgMapper;
    @Autowired
    private WeiZhiProductService productService;
    @Autowired
    private WeiZhiProductApiClient productApiClient;
    @Autowired
    private WeiZhiPriceApiClient priceApiClient;
	@Autowired
	private LimitCommonService limitCommonService;

	@Autowired
	private ZYPriceCollecService zyPriceCollecService;

	/**
	 * 根据商品编号获取商品需要展示前端信息
	 */
    public Map<String, Object> getJdGoodsAllInfoBySku(Long sku) throws BusinessException {
		Map<String, Object> map = Maps.newHashMap();
		if (sku.toString().length() == 8) {
			// 查询商品名称（图书音像类目）
			JdGoodsBooks jdGoodsBooks = getJdGoodsBooksInfoBySku(sku);
			map.put("relatedProducts", jdGoodsBooks.getRelatedProducts());
		} else {
			// 查询商品名称
			Product jdGoods = getJdGoodsInfoBySku(sku);
			map.put("goodsName", jdGoods.getName());// 商品名称
			//java字符串转义,把&lt;&gt;转换成<>等字符
			String skuCss = getSkuCss(sku);
			String introduction = jdGoods.getIntroduction().replaceAll("width","width");
			map.put("googsDetail", StringEscapeUtils.unescapeXml(skuCss + introduction));// 商品详情
		}
		// 查询商品价格
		Collection<Long> skuPrice = new ArrayList<Long>();
		skuPrice.add(sku);

		GoodsInfoEntity goodsInfoEntity = goodsService.selectGoodsByExternalId(String.valueOf(sku));
		if(goodsInfoEntity == null){
			logger.error("数据有误,skuId:{}对应goods_base表无数据",sku.toString());
			throw new RuntimeException("数据有误");
		}
		List<GoodsStockInfoEntity> goodsStockInfoEntityList = goodsStockInfoRepository.loadByGoodsId(goodsInfoEntity.getId());
		BigDecimal goodsPrice = commonService.calculateGoodsPrice(goodsInfoEntity.getId(), goodsStockInfoEntityList.get(0).getGoodsStockId());
		map.put("goodsPrice",goodsPrice);

		// 查询商品图片
		List<String> JdImagePathList = getJdImagePathListBySku(sku, JdGoodsImageType.TYPEN1.getCode());
		map.put("jdImagePathList", JdImagePathList);
		// 查询商品规格
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
		map.put("skuId",String.valueOf(sku));
		map.put("jdSimilarSkuList", jdSimilarSkuList);
		map.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
		map.put("goodsName", goodsInfoEntity.getGoodsName());// 商品名称
		return map;
	}

	/**
	 * 根据商品编号获取商品需要展示App信息
	 * 
	 * @throws BusinessException
	 */
	public Map<String, Object> getAppJdGoodsAllInfoBySku(Long sku, String goodsId, Region region,String userId) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			if (sku.toString().length() == 8) {
				// 查询商品名称（图书音像类目）
				JdGoodsBooks jdGoodsBooks = getJdGoodsBooksInfoBySku(sku);
				map.put("relatedProducts", jdGoodsBooks.getRelatedProducts());
			} else {
				String jddetail = "";
				// 从ES中查询商品详情信息
				Goods goods = IndexManager.getDocument("goods", IndexType.GOODS, Integer.parseInt(goodsId));
				if (null != goods && StringUtils.isNotBlank(goods.getGoodsDetail())) {
					jddetail = goods.getGoodsDetail().replaceAll("src=\"//", "src=\"http://");
				} else {
					// 查询商品名称
					Product jdGoods = getJdGoodsInfoBySku(sku);
					jddetail = jdGoods.getIntroduction().replaceAll("src=\"//", "src=\"http://").replaceAll("href=\'//","href=\'http://");
				}
				// map.put("goodsName", jdGoods.getName());// 商品名称
				// java字符串转义,把&lt;&gt;转换成<>等字符
				String skuCss = getSkuCss(sku).replaceAll("background-image:url\\(//",
						"background-image:url\\(http://");
				String introduction = jddetail.replaceAll("width", "width");
				map.put("googsDetail", StringEscapeUtils.unescapeXml(skuCss + introduction));// 商品详情
			}
			// 查看商品的邮费
			List<Long> goodsIds = new ArrayList<>();
			goodsIds.add(sku);
			// BigDecimal postage = goodsService.getPostage(goodsIds);
			map.put("postage", "0");
			// 查询商品图片
			List<String> JdImagePathList = getJdImagePathListBySku(sku, JdGoodsImageType.TYPEN1.getCode());
			map.put("jdImagePathList", JdImagePathList);
			// 查询商品是否有货
			String jdGoodStock = getStockBySku(sku.toString(), region);
			map.put("goodsStockDes", jdGoodStock);
			//查询京东商品规格
			Map<String,Object> checkGoodsMap=new HashMap<>();
			Map<String, Object> map2 = getJdSimilarSkuInfoList(goodsId,sku, region,userId,checkGoodsMap);
			map.put("JdSimilarSkuToList", map2.get("JdSimilarSkuToList"));
			map.put("skuId", map2.get("skuId"));
			map.put("jdSimilarSkuList", map2.get("jdSimilarSkuList"));
			map.put("jdSimilarSkuListSize", map2.get("jdSimilarSkuListSize"));
			
		} catch (Exception e) {
			List<JdSimilarSku> jdSimilarSkuList=new ArrayList<>();
			List<JdSimilarSkuTo> JdSimilarSkuToList = new ArrayList<>();
			map.put("JdSimilarSkuToList",JdSimilarSkuToList);
			map.put("skuId",sku);
			map.put("jdSimilarSkuList", jdSimilarSkuList);
			map.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
		}
		return map;
	}
	// 查询商品规格（包括库存），先从缓存中获取
	public Map<String,Object> getJdSimilarSkuInfoList(String goodsIdStr,Long sku,Region region,String userId,Map<String,Object> checkMap) throws BusinessException {
		Map<String, Object> map = Maps.newHashMap();
		TreeSet<String> skusSet = new TreeSet<String>();
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
		List<JdSimilarSku> jdSimilarSkuList2 = new ArrayList<>();
		for (JdSimilarSku jdsk : jdSimilarSkuList) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			List<JdSaleAttr> saleAttrList2 = new ArrayList<>();
			for (JdSaleAttr jdsa : saleAttrList) {
				List<String> skuIds = jdsa.getSkuIds();
				List<String> skuIds2 = new ArrayList<>();
				for (String skuId : skuIds) {
					// 判断该skuId是否已经上架，如何没有则移除该规格下的skuId
					GoodsInfoEntity gty = goodsRepository.selectGoodsInfoByExternalId(skuId);
					if (null != gty) {
						skuIds2.add(skuId);
						skusSet.add(skuId);
					}
				}
				jdsa.setSkuIds(skuIds2);
				if (skuIds2.size() > 0) {
					saleAttrList2.add(jdsa);
				}
			}
			jdsk.setSaleAttrList(saleAttrList2);
			if (saleAttrList2.size() > 0) {
				jdSimilarSkuList2.add(jdsk);
			}
		}
		// 为规格添加唯一标识
		for (JdSimilarSku jdsk : jdSimilarSkuList2) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			for (int i = 0; i < saleAttrList.size(); i++) {
				if (i > 9) {
					saleAttrList.get(i).setSaleValueId(i + "");
				} else {
					saleAttrList.get(i).setSaleValueId("0" + i);
				}
			}
		}
		Map<String,Object> LimitMap=new HashMap<>();
		// 查询商品规格中的商品的价格和库存
		List<JdSimilarSkuTo> JdSimilarSkuToList = new ArrayList<>();
		Iterator<String> iterator = skusSet.iterator();
		String isSelectSkuIdOrder = "";// 商品本身的规格参数
		while (iterator.hasNext()) {
			JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
			JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();

			//为京东商品添加库存
			jdSimilarSkuVo.setStockCurrAmt(Long.parseLong("200"));

			String skuId = iterator.next();
			// 查询商品价格
			jdSimilarSkuVo.setGoodsId(goodsIdStr);
			Long goodsId = Long.valueOf(goodsIdStr);

			// 查询商品是否有货
			String jdGoodStock="";
			//是否支持7天无理由退货,Y、N
			String support7dRefund="N";
			if(StringUtils.equals(sku+"", skuId)){
            	support7dRefund=(String) checkMap.get("support7dRefund");
            	jdGoodStock=(String) checkMap.get("goodsStockDes");
			}else{
				jdGoodStock = getStockBySku(skuId.toString(), region);
				support7dRefund=goodsService.getsupport7dRefund(Long.parseLong(skuId));
			}
			//满减活动字段
			String activityCfg = goodsService.getActivityInfo(goodsId,skuId);
			  //添加活动id
        	ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getBySkuId(goodsId,skuId);
        	if(null !=proGroupGoodsBo){
        		jdSimilarSkuVo.setProActivityId(proGroupGoodsBo.getActivityId());
        	}
        	//获取商品的优惠券
        	List<String> proCoupons=getProCouponList(goodsId,skuId);
        	if(proCoupons.size()>3){
    			jdSimilarSkuVo.setProCouponList(proCoupons.subList(0, 3));
        	}else{
    			jdSimilarSkuVo.setProCouponList(proCoupons);
        	}
			// 查询商品是否有货
			jdSimilarSkuVo.setSkuId(skuId);
			jdSimilarSkuVo.setStockDesc(jdGoodStock);
			jdSimilarSkuVo.setSupport7dRefund(support7dRefund);
			jdSimilarSkuVo.setActivityCfg(activityCfg);

			String skuIdOrder = "";
			for (JdSimilarSku jdsk : jdSimilarSkuList2) {
				if (skuIdOrder.length() == 0) {
					skuIdOrder = jdsk.getDim().toString();
				} else {
					skuIdOrder = skuIdOrder + ";" + jdsk.getDim().toString();
				}
				List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
				for (JdSaleAttr jdsa : saleAttrList) {
					List<String> skuIds = jdsa.getSkuIds();
					for (String skuId2 : skuIds) {
						if (skuId2.equals(skuId)) {
							skuIdOrder = skuIdOrder + jdsa.getSaleValueId();
						}
					}
				}
			}

			List<GoodsStockInfoEntity> jdGoodsStockInfoList = goodsStockInfoRepository.loadByGoodsId(goodsId);
			if (jdGoodsStockInfoList.size() == 1) {
				jdSimilarSkuVo.setGoodsStockId(jdGoodsStockInfoList.get(0).getId().toString());
				BigDecimal price = commonService.calculateGoodsPrice(goodsId, jdGoodsStockInfoList.get(0).getId());
				//根据skuId查询该规格是否参加了限时购活动
				LimitGoodsSkuVo limitGS=limitCommonService.selectLimitByGoodsId(userId,skuId);
				if(null !=limitGS){
					LimitMap.put(skuId, skuIdOrder);
					BigDecimal limitActivityPrice=limitGS.getActivityPrice();
					limitActivityPrice.setScale(2, BigDecimal.ROUND_DOWN);
					jdSimilarSkuVo.setPrice(price);//限时购活动价
					jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
					jdSimilarSkuVo.setLimitActivityPrice(limitActivityPrice);
					jdSimilarSkuVo.setLimitActivityPriceFirst((new BigDecimal("0.1").multiply(limitActivityPrice)).setScale(2, BigDecimal.ROUND_DOWN));
					jdSimilarSkuVo.setIsLimitActivity(true);
					jdSimilarSkuVo.setLimitNum(limitGS.getLimitNum());
					jdSimilarSkuVo.setLimitPersonNum(limitGS.getLimitPersonNum());
					jdSimilarSkuVo.setLimitBuyActId(limitGS.getLimitBuyActId());
					jdSimilarSkuVo.setLimitBuyFalg(limitGS.getLimitFalg());
					jdSimilarSkuVo.setLimitBuyTime(limitGS.getTime());
					jdSimilarSkuVo.setLimitBuyStartTime(limitGS.getStartTime());
					jdSimilarSkuVo.setLimitBuyEndTime(limitGS.getEndTime());
				}else{
					jdSimilarSkuVo.setPrice(price);
					jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
				}

			}
			jdSimilarSkuTo.setSkuIdOrder(skuIdOrder);
			jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
			JdSimilarSkuToList.add(jdSimilarSkuTo);

			if (skuId.equals(sku.toString())) {
				isSelectSkuIdOrder = skuIdOrder;
			}
		}
		// 在所有参加限时购的规格中判断是否含有传入参数sku
		if (!LimitMap.isEmpty()) {
			if (null == LimitMap.get(sku+"")) {
				for (Map.Entry<String, Object> entry : LimitMap.entrySet()) {
					isSelectSkuIdOrder = (String) entry.getValue();
					break;
				}
			} 
		}
		// 为app端标记初始化被选中商品的规格
		if (isSelectSkuIdOrder.length() > 0) {
			String[] orderList = isSelectSkuIdOrder.split(";");
			for (int i = 0; i < orderList.length; i++) {
				String key = orderList[i].substring(1, 3);
				List<JdSaleAttr> jdSaleAttrList = jdSimilarSkuList2.get(i).getSaleAttrList();
				for (JdSaleAttr jdSaleAttr : jdSaleAttrList) {
					if (jdSaleAttr.getSaleValueId().equals(key)) {
						jdSaleAttr.setIsSelect("true");
						break;
					}
				}
			}
		}else{
			JdSimilarSkuToList= new ArrayList<>();
			jdSimilarSkuList2=new ArrayList<>();
		}
		map.put("JdSimilarSkuToList", JdSimilarSkuToList);
		map.put("skuId", String.valueOf(sku));
		map.put("jdSimilarSkuList", jdSimilarSkuList2);
		map.put("jdSimilarSkuListSize", jdSimilarSkuList2.size());

		return map;
	}

	// 查询商品规格（包括不包括库存）
	public Map<String, Object> jdSimilarSkuInfo(Long sku) throws BusinessException {
		Map<String, Object> map = Maps.newHashMap();
		TreeSet<String> skusSet = new TreeSet<String>();
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
		List<JdSimilarSku> jdSimilarSkuList2 = new ArrayList<>();
		for (JdSimilarSku jdsk : jdSimilarSkuList) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			List<JdSaleAttr> saleAttrList2 = new ArrayList<>();
			for (JdSaleAttr jdsa : saleAttrList) {
				//jdsa.setImagePath("http://img13.360buyimg.com/n3/" + jdsa.getImagePath());
				jdsa.setImagePath(jdsa.getImagePath());
				List<String> skuIds = jdsa.getSkuIds();
				List<String> skuIds2 = new ArrayList<>();
				for (String skuId : skuIds) {
					// 判断该skuId是否已经上架，如何没有则移除该规格下的skuId
					GoodsInfoEntity gty = goodsRepository.selectGoodsInfoByExternalId(skuId);
					if (null != gty) {
						skuIds2.add(skuId);
						skusSet.add(skuId);
					}
				}
				jdsa.setSkuIds(skuIds2);
				if (skuIds2.size() > 0) {
					saleAttrList2.add(jdsa);
				}
			}
			jdsk.setSaleAttrList(saleAttrList2);
			if (saleAttrList2.size() > 0) {
				jdSimilarSkuList2.add(jdsk);
			}
		}
		// 为规格添加唯一标识
		for (JdSimilarSku jdsk : jdSimilarSkuList2) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			for (int i = 0; i < saleAttrList.size(); i++) {
				if (i > 9) {
					saleAttrList.get(i).setSaleValueId(i + "");
				} else {
					saleAttrList.get(i).setSaleValueId("0" + i);
				}

			}
		}

		// 查询商品规格中的商品的价格和库存
		List<JdSimilarSkuTo> JdSimilarSkuToList = new ArrayList<>();
		Iterator<String> iterator = skusSet.iterator();
		String isSelectSkuIdOrder = "";// 商品本身的规格参数
		while (iterator.hasNext()) {
			JdSimilarSkuVo jdSimilarSkuVo = new JdSimilarSkuVo();
			JdSimilarSkuTo jdSimilarSkuTo = new JdSimilarSkuTo();
			//为京东商品添加库存
			jdSimilarSkuVo.setStockCurrAmt(Long.parseLong("200"));
			jdSimilarSkuVo.setStockDesc("有货");
			String skuId = iterator.next();
			// 查询商品价格
			GoodsInfoEntity goodsInfo = goodsRepository.selectGoodsByExternalId(skuId);
			jdSimilarSkuVo.setGoodsId(goodsInfo.getId().toString());
			Long goodsId = goodsInfo.getId();
			List<GoodsStockInfoEntity> jdGoodsStockInfoList = goodsStockInfoRepository.loadByGoodsId(goodsId);
			if (jdGoodsStockInfoList.size() == 1) {
				BigDecimal price = commonService.calculateGoodsPrice(goodsId, jdGoodsStockInfoList.get(0).getId());
				jdSimilarSkuVo.setGoodsStockId(jdGoodsStockInfoList.get(0).getId().toString());
				
				//根据skuId查询该规格是否参加了限时购活动
				String userId="";
				LimitGoodsSkuVo limitGS=limitCommonService.selectLimitByGoodsId(userId,skuId);
				if(null !=limitGS){
//					LimitMap.put(skuId, skuIdOrder);
//					LimitGoodsSkuMap.put(skuId, limitGS);
					BigDecimal limitActivityPrice=limitGS.getActivityPrice();
					limitActivityPrice.setScale(2, BigDecimal.ROUND_DOWN);
					jdSimilarSkuVo.setPrice(price);//限时购活动价
					jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
					jdSimilarSkuVo.setLimitActivityPrice(limitActivityPrice);
					jdSimilarSkuVo.setLimitActivityPriceFirst((new BigDecimal("0.1").multiply(limitActivityPrice)).setScale(2, BigDecimal.ROUND_DOWN));
					jdSimilarSkuVo.setIsLimitActivity(true);
					jdSimilarSkuVo.setLimitNum(limitGS.getLimitNum());
					jdSimilarSkuVo.setLimitPersonNum(limitGS.getLimitPersonNum());
					jdSimilarSkuVo.setLimitBuyActId(limitGS.getLimitBuyActId());
					jdSimilarSkuVo.setLimitBuyFalg(limitGS.getLimitFalg());
					jdSimilarSkuVo.setLimitBuyTime(limitGS.getTime());
					jdSimilarSkuVo.setLimitBuyStartTime(limitGS.getStartTime());
					jdSimilarSkuVo.setLimitBuyEndTime(limitGS.getEndTime());
				}else{
					jdSimilarSkuVo.setPrice(price);
					jdSimilarSkuVo.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
				}
			}

			jdSimilarSkuVo.setSkuId(skuId);

			String skuIdOrder = "";
			for (JdSimilarSku jdsk : jdSimilarSkuList2) {
				if (skuIdOrder.length() == 0) {
					skuIdOrder = jdsk.getDim().toString();
				} else {
					skuIdOrder = skuIdOrder + ";" + jdsk.getDim().toString();
				}
				List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
				for (JdSaleAttr jdsa : saleAttrList) {
					List<String> skuIds = jdsa.getSkuIds();
					for (String skuId2 : skuIds) {
						if (skuId2.equals(skuId)) {
							skuIdOrder = skuIdOrder + jdsa.getSaleValueId();
						}
					}
				}
			}
			jdSimilarSkuTo.setSkuIdOrder(skuIdOrder);
			jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
			JdSimilarSkuToList.add(jdSimilarSkuTo);
			if (skuId.equals(sku.toString())) {
				isSelectSkuIdOrder = skuIdOrder;
			}
		}
		//京东商品本身的规格参数
		String jdGoodsSimilarSku="";
		// 为app端标记初始化被选中商品的规格
		if (isSelectSkuIdOrder.length() > 0) {
			String[] orderList = isSelectSkuIdOrder.split(";");
			for (int i = 0; i < orderList.length; i++) {
				String key = orderList[i].substring(1, 3);
				List<JdSaleAttr> jdSaleAttrList = jdSimilarSkuList2.get(i).getSaleAttrList();
				for (JdSaleAttr jdSaleAttr : jdSaleAttrList) {
					if (jdSaleAttr.getSaleValueId().equals(key)) {
						jdSaleAttr.setIsSelect("true");
						jdGoodsSimilarSku=jdSaleAttr.getSaleValue()+" ";
					}
				}
			}
		}
		map.put("JdSimilarSkuToList", JdSimilarSkuToList);
		map.put("jdSimilarSkuList", jdSimilarSkuList2);
		map.put("jdSimilarSkuListSize", jdSimilarSkuList2.size());
		map.put("jdGoodsSimilarSku", jdGoodsSimilarSku);
		return map;
	}
	/**
	 * 获取商品详情中的的优惠券并按优惠力度从大到小排序
	 */
	public List<String> getProCouponList(Long goodsId,String  skuId){
		List<String> proCouponStringList=new ArrayList<>();
		List<ProCouponGoodsDetailVo> proCouponGoodsDetailVos=new ArrayList<>();
		//获取与活动向关联的优惠券
		if(null !=goodsId && StringUtils.isNotBlank(skuId)){
	    	ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getBySkuId(goodsId,skuId);
	    	if(null !=proGroupGoodsBo && proGroupGoodsBo.isValidActivity()){
	    		//排除中原活动id
				if(!hasIncludeZYCouponActivityId(proGroupGoodsBo.getActivityId())){
					List<ProCoupon> proCoupons=couponManagerService.getCouponListsByActivityId(proGroupGoodsBo.getActivityId().toString());
					for (ProCoupon proCoupon : proCoupons) {
						//房易贷用户专享过滤不展示在商品详情页
						if(CouponExtendType.COUPON_FYDYHZX.getCode().equals(proCoupon.getExtendType())
								|| CouponExtendType.COUPON_SMYHZX.getCode().equals(proCoupon.getExtendType())){
							continue;
						}
						ProCouponGoodsDetailVo proCouponGoodsDetailVo=new ProCouponGoodsDetailVo();
						proCouponGoodsDetailVo.setCouponSill(proCoupon.getCouponSill());
						proCouponGoodsDetailVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
						proCouponGoodsDetailVos.add(proCouponGoodsDetailVo);
					}
				}
	    	}
		}
		//排序（按优惠力度的从大小排序）
		if(proCouponGoodsDetailVos.size()>0){
			for(int i=0;i<proCouponGoodsDetailVos.size()-1;i++){
				for(int j=i+1;j<proCouponGoodsDetailVos.size();j++){
					ProCouponGoodsDetailVo proCoupon1=proCouponGoodsDetailVos.get(i);
					ProCouponGoodsDetailVo proCoupon2=proCouponGoodsDetailVos.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						proCouponGoodsDetailVos.set(i, proCoupon2);
						proCouponGoodsDetailVos.set(j, proCoupon1);
					}
				}
			}
		}
		for (ProCouponGoodsDetailVo proCouponGoodsDetailVo : proCouponGoodsDetailVos) {
			BigDecimal zero = BigDecimal.ZERO;
			if (proCouponGoodsDetailVo.getCouponSill().compareTo(zero) <= 0) {
				String  couponSillString=proCouponGoodsDetailVo.getDiscountAmonut().intValue()+".1";
				proCouponStringList.add("满" +couponSillString + "-"
						+ proCouponGoodsDetailVo.getDiscountAmonut().intValue());
			} else {
				proCouponStringList.add("满" + proCouponGoodsDetailVo.getCouponSill().intValue() + "-"
						+ proCouponGoodsDetailVo.getDiscountAmonut().intValue());
			}
		}
		return proCouponStringList;
	}

	public boolean hasIncludeZYCouponActivityId(Long existActivityId){
		if(existActivityId.longValue() == zyPriceCollecService.getZyActicityCollecId()){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取商品详情中的的优惠券并按优惠力度从大到小排序（原来的方法：根据goodsId查询）
	 */
	public List<String> getProCouponList2(Long goodsId){
		List<String> proCouponStringList=new ArrayList<>();
		List<ProCouponGoodsDetailVo> proCouponGoodsDetailVos=new ArrayList<>();
		//获取与活动向关联的优惠券
		if(null !=goodsId){
	    	ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getByGoodsId(goodsId);
	    	if(null !=proGroupGoodsBo && proGroupGoodsBo.isValidActivity()){
	    		List<ProCoupon> proCoupons=couponManagerService.getCouponListsByActivityId(proGroupGoodsBo.getActivityId().toString());
    			for (ProCoupon proCoupon : proCoupons) {
    				ProCouponGoodsDetailVo proCouponGoodsDetailVo=new ProCouponGoodsDetailVo();
    				proCouponGoodsDetailVo.setCouponSill(proCoupon.getCouponSill());
    				proCouponGoodsDetailVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
    				proCouponGoodsDetailVos.add(proCouponGoodsDetailVo);
				}
	    	}
		}
		//排序（按优惠力度的从大小排序）
		if(proCouponGoodsDetailVos.size()>0){
			for(int i=0;i<proCouponGoodsDetailVos.size()-1;i++){
				for(int j=i+1;j<proCouponGoodsDetailVos.size();j++){
					ProCouponGoodsDetailVo proCoupon1=proCouponGoodsDetailVos.get(i);
					ProCouponGoodsDetailVo proCoupon2=proCouponGoodsDetailVos.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						proCouponGoodsDetailVos.set(i, proCoupon2);
						proCouponGoodsDetailVos.set(j, proCoupon1);
					}
				}
			}
		}
		for (ProCouponGoodsDetailVo proCouponGoodsDetailVo : proCouponGoodsDetailVos) {
			BigDecimal zero = BigDecimal.ZERO;
			if (proCouponGoodsDetailVo.getCouponSill().compareTo(zero) <= 0) {
				String  couponSillString=proCouponGoodsDetailVo.getDiscountAmonut().intValue()+".1";
				proCouponStringList.add("满" +couponSillString + "-"
						+ proCouponGoodsDetailVo.getDiscountAmonut().intValue());
			} else {
				proCouponStringList.add("满" + proCouponGoodsDetailVo.getCouponSill().intValue() + "-"
						+ proCouponGoodsDetailVo.getDiscountAmonut().intValue());
			}
		}
		return proCouponStringList;
	}
	/**
	 * 获取商品优惠券列表
	 */
	public Map<String,Object> getProCoupons2(Long goodsId,Long userId){
		Map<String,Object> resultMap=new HashMap<>();
		List<ProCouponVo> proCouponVos=new ArrayList<>();
		List<ProCouponVo> reProCouponList=new ArrayList<>();
        List<ProCouponVo> proCouponList=new ArrayList<>();
        String activityId="";
		//获取与活动向关联的优惠券
		if(null !=goodsId){
	    	ProGroupGoodsBo proGroupGoodsBo=proGroupGoodsService.getByGoodsId(goodsId);
	    	if(null !=proGroupGoodsBo && proGroupGoodsBo.isValidActivity()){
	    	    ProActivityCfg activityCfg = activityCfgService.getById(proGroupGoodsBo.getActivityId());
	    	    activityId= proGroupGoodsBo.getActivityId().toString();
	    		List<ProCoupon> proCoupons=couponManagerService.getCouponsByActivityId(activityId);
    			for (ProCoupon proCoupon : proCoupons) {
    				ProCouponVo proCouponVo=new ProCouponVo();
    				proCouponVo.setId(proCoupon.getId());
    				proCouponVo.setActivityId(Long.parseLong(activityId));
    				proCouponVo.setName(proCoupon.getName());
    				proCouponVo.setCouponSill(proCoupon.getCouponSill());
    				proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
    				SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
    				String startTimeString = formatter.format(activityCfg.getStartTime());
    				String endTimeTimeString = formatter.format(activityCfg.getEndTime());
    				proCouponVo.setStartTime(startTimeString);
    				proCouponVo.setEndTime(endTimeTimeString);
    				proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
    				proCouponVos.add(proCouponVo);
				}
	    	}
		}
		//排序（按优惠力度的从大小排序）
		if(proCouponVos.size()>0){
			for(int i=0;i<proCouponVos.size()-1;i++){
				for(int j=i+1;j<proCouponVos.size();j++){
					ProCouponVo proCoupon1=proCouponVos.get(i);
					ProCouponVo proCoupon2=proCouponVos.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						proCouponVos.set(i, proCoupon2);
						proCouponVos.set(j, proCoupon1);
					}
				}
			}
		}
		for (ProCouponVo proCouponVo : proCouponVos) {
			ProCouponRel proCouponRel=couponRelService.getRelByActivityIdAndCouponId(Long.parseLong(activityId), proCouponVo.getId());
			if(null !=proCouponRel && proCouponRel.getRemainNum()>0){//優惠券的剩餘數量大於零
				List<ProMyCoupon> proMyCouponList=myCouponManagerService.getCouponByUserIdAndRelCouponId(userId, proCouponRel.getId());
				if(null !=proMyCouponList && proMyCouponList.size()>0){
					reProCouponList.add(proCouponVo);
					if(proMyCouponList.size()<proCouponRel.getLimitNum()){//领取的数量小于限领的数量则该优惠券还可以领取
						proCouponList.add(proCouponVo);
					}
				}else{
					proCouponList.add(proCouponVo);
				}
			}
		}
		resultMap.put("reProCouponList", reProCouponList);
		resultMap.put("proCouponList", proCouponList);
		return resultMap;
	}
	/**
	 * 获取商品优惠券列表
	 */
	public Map<String,Object> getProCoupons(Long goodsId,Long userId){
		Map<String, Object> resultMap = new HashMap<>();
		List<ProCouponVo> proCouponVos = new ArrayList<>();// 根据活动的Id，查询所属活动的券
		List<ProCouponVo> reProCouponList = new ArrayList<>();// 已领取的有效的券
		List<ProCouponVo> proCouponList = new ArrayList<>();// 可领取的有效的券
		String activityId = "";
		GoodsInfoEntity goodsBasicInfo = new GoodsInfoEntity();
		// 获取与活动向关联的优惠券
		if (null != goodsId) {
			goodsBasicInfo = goodsDao.select(goodsId);
			ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getByGoodsId(goodsId);
			if (null != proGroupGoodsBo && proGroupGoodsBo.isValidActivity()) {
				ProActivityCfg activityCfg = activityCfgService.getById(proGroupGoodsBo.getActivityId());
				activityId = proGroupGoodsBo.getActivityId().toString();
				List<ProCoupon> proCoupons = couponManagerService.getCouponsByActivityId(activityId);
				for (ProCoupon proCoupon : proCoupons) {
					Boolean falge = false;
					if (StringUtils.equals(proCoupon.getOfferRange() + "", OfferRangeType.RANGE_ZDPP.getCode())) {
						if (StringUtils.equals(goodsBasicInfo.getBrandId(), proCoupon.getBrandId() + "")) {
							falge = true;
						}
					} else if (StringUtils.equals(proCoupon.getOfferRange() + "", OfferRangeType.RANGE_ZDPL.getCode())) {
						if (StringUtils.equals(goodsBasicInfo.getCategoryId1() + "", proCoupon.getCategoryId1())) {
							falge = true;
						}
					} else if (StringUtils.equals(proCoupon.getOfferRange() + "", OfferRangeType.RANGE_ZDSP.getCode())) {
						if (StringUtils.equals(proGroupGoodsBo.getSkuId(), proCoupon.getSkuId())) {
							falge = true;
						}
					} else if(StringUtils.equals(proCoupon.getOfferRange() + "", OfferRangeType.RANGE_ALL.getCode())){
						falge = true;
					}
					if (falge) {
						ProCouponVo proCouponVo = new ProCouponVo();
						proCouponVo.setId(proCoupon.getId());
						proCouponVo.setActivityId(Long.parseLong(activityId));
						proCouponVo.setActivityFalge(true);
						proCouponVo.setName("【限" + activityCfg.getActivityName() + "活动商品】\t" + proCoupon.getName());
						proCouponVo.setCouponSill(proCoupon.getCouponSill());
						proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
						String startTimeString = formatter.format(activityCfg.getStartTime());
						String endTimeTimeString = formatter.format(activityCfg.getEndTime());
						proCouponVo.setStartTime(startTimeString);
						proCouponVo.setEndTime(endTimeTimeString);
						proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
						proCouponVos.add(proCouponVo);
					}
				}
			}
		}
		//排序（按优惠力度的从大小排序）
		if(proCouponVos.size()>0){
			for(int i=0;i<proCouponVos.size()-1;i++){
				for(int j=i+1;j<proCouponVos.size();j++){
					ProCouponVo proCoupon1=proCouponVos.get(i);
					ProCouponVo proCoupon2=proCouponVos.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						proCouponVos.set(i, proCoupon2);
						proCouponVos.set(j, proCoupon1);
					}
				}
			}
		} 
		//获取可用优惠券 
		List<ProMyCoupon> proMyCouponList=new ArrayList<>();
		for (ProCouponVo proCouponVo : proCouponVos) {
			ProCouponRel proCouponRel=couponRelService.getRelByActivityIdAndCouponId(Long.parseLong(activityId), proCouponVo.getId());
			List<ProMyCoupon> proMyCouponList1=myCouponMapper.getCouponByUserIdAndRelId(new ProMyCouponQuery(userId, proCouponRel.getId()));
			if( proCouponRel.getRemainNum() > 0 ){//優惠券的剩餘數量大於零
				if(CollectionUtils.isNotEmpty(proMyCouponList1)){
					if( proMyCouponList1.size() < proCouponRel.getLimitNum() ){//领取的数量小于限领的数量则该优惠券还可以领取
						proCouponList.add(proCouponVo);
					}
				}else{
					proCouponList.add(proCouponVo);
				}
			}
			//已经领取的优惠券中未使用的优惠券
			for (ProMyCoupon proMyCoupon : proMyCouponList1) {
				if(StringUtils.equals(proMyCoupon.getStatus(), CouponStatus.COUPON_N.getCode())){
					proMyCoupon.setActivityFalge(true);
					proMyCouponList.add(proMyCoupon);
				}
			}
		}
		//获取与活动无关的优惠券
		ProMyCouponQuery query=new ProMyCouponQuery();
		query.setUserId(userId);
		query.setCouponRelId(-1L);
		query.setStatus("N");
		query.setMaxDate(new Date());
		List<ProMyCoupon> ProMyCoupons=myCouponMapper.getCouponByStatusAndDate(query);
		for (ProMyCoupon proMyCoupon : ProMyCoupons) {
			proMyCoupon.setActivityFalge(false);
			ProCoupon pro = proCouponMapper.selectByPrimaryKey(proMyCoupon.getCouponId());
			if (StringUtils.equals(pro.getType(), CouponType.COUPON_ZDPL.getCode())) {
				if (StringUtils.equals(goodsBasicInfo.getCategoryId1() + "", pro.getCategoryId1())) {
					proMyCouponList.add(proMyCoupon);
				} else if (StringUtils.equals(goodsBasicInfo.getCategoryId2() + "", pro.getCategoryId2())) {
					proMyCouponList.add(proMyCoupon);
				}
			} else if (StringUtils.equals(pro.getType(), CouponType.COUPON_ZDSP.getCode())) {
				String[] similarGoods = pro.getSimilarGoodsCode().split(",");
				if (Arrays.asList(similarGoods).contains(goodsBasicInfo.getGoodsCode())) {
					proMyCouponList.add(proMyCoupon);
				}
			} else if (StringUtils.equals(pro.getType(), CouponType.COUPON_QPL.getCode())) {
				proMyCouponList.add(proMyCoupon);
			}
		}
		for (ProMyCoupon proMyCoupon : proMyCouponList) {
			ProCoupon proCoupon = proCouponMapper.selectByPrimaryKey(proMyCoupon.getCouponId());
			ProCouponVo proCouponVo=new ProCouponVo();
			proCouponVo.setId(proCoupon.getId());
			if(StringUtils.isNotEmpty(activityId)){
				proCouponVo.setActivityId(Long.parseLong(activityId));
			}else{
				proCouponVo.setActivityId(null);
			}
			if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDPL.getCode())) {
				if (StringUtils.equals(goodsBasicInfo.getCategoryId1() + "", proCoupon.getCategoryId1())) {
					Category categroy = categoryMapper.selectByPrimaryKey(Long.parseLong(proCoupon.getCategoryId1()));
				    proCouponVo.setName("【限"+categroy.getCategoryName()+"类】\t"+proCoupon.getName());
				} else if (StringUtils.equals(goodsBasicInfo.getCategoryId2() + "", proCoupon.getCategoryId2())) {
					Category categroy = categoryMapper.selectByPrimaryKey(Long.parseLong(proCoupon.getCategoryId2()));
				    proCouponVo.setName("【限"+categroy.getCategoryName()+"类】\t"+proCoupon.getName());
				}
			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDSP.getCode())) {
				String[] similarGoods = proCoupon.getSimilarGoodsCode().split(",");
				if (Arrays.asList(similarGoods).contains(goodsBasicInfo.getGoodsCode())) {
					GoodsInfoEntity goods = goodsMapper.selectGoodsByGoodsCode(proCoupon.getGoodsCode());
				    proCouponVo.setName("【指定商品】\t" + goods.getGoodsName());
				}
			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_QPL.getCode())) {
			    proCouponVo.setName("【全品类】\t" + proCoupon.getName());

			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_HDSP.getCode())) {
				String activityName = "";
				if (null != proMyCoupon.getCouponRelId()) {
					ProCouponRel rel = couponRelMapper.selectByPrimaryKey(proMyCoupon.getCouponRelId());
					if (null != rel) {// 此做法主要是适配于测试环境直接删库数据不完全，可能导致的问题
						ProActivityCfg cfg = activityCfgMapper.selectByPrimaryKey(rel.getProActivityId());
						if (null != cfg) {
							activityName = cfg.getActivityName();
						}
					}
				}
				proCouponVo.setName("【限" + activityName + "活动商品】\t" + proCoupon.getName());
			}
			
			proCouponVo.setActivityFalge(proMyCoupon.getActivityFalge());
//			proCouponVo.setName(proCoupon.getName());
			proCouponVo.setCouponSill(proCoupon.getCouponSill());
			proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			String startTimeString = formatter.format(proMyCoupon.getStartDate());
			String endTimeTimeString = formatter.format(proMyCoupon.getEndDate());
			proCouponVo.setStartTime(startTimeString);
			proCouponVo.setEndTime(endTimeTimeString);
			proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
			reProCouponList.add(proCouponVo);
		}
		//排序（按优惠力度的从大小排序）
		if(reProCouponList.size()>0){
			for(int i=0;i<reProCouponList.size()-1;i++){
				for(int j=i+1;j<reProCouponList.size();j++){
					ProCouponVo proCoupon1=reProCouponList.get(i);
					ProCouponVo proCoupon2=reProCouponList.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						reProCouponList.set(i, proCoupon2);
						reProCouponList.set(j, proCoupon1);
					}
				}
			}
		} 
		resultMap.put("reProCouponList", reProCouponList);
		resultMap.put("proCouponList", proCouponList);
		return resultMap;
	}
	/**
	 * 获取商品优惠券列表
	 */
	public Map<String,Object> getProCouponsBySkuId(Long goodsId,Long userId,String skuId){
		Map<String, Object> resultMap = new HashMap<>();
		List<ProCouponVo> proCouponVos = new ArrayList<>();// 根据活动的Id，查询所属活动的券
		List<ProCouponVo> reProCouponList = new ArrayList<>();// 已领取的有效的券
		List<ProCouponVo> proCouponList = new ArrayList<>();// 可领取的有效的券
		String activityId = "";
		GoodsInfoEntity goodsBasicInfo = new GoodsInfoEntity();
		// 获取与活动向关联的优惠券
		if (null != goodsId) {
			goodsBasicInfo = goodsDao.select(goodsId);
//			ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getByGoodsId(goodsId);
			/**
			 * 首先根据商品的Id和SkuID，获取对应的活动信息,如果活动存在，并且没有过期，则正常运行
			 */
			ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getBySkuId(goodsId,skuId);

			if (null != proGroupGoodsBo && proGroupGoodsBo.isValidActivity()) {
				ProActivityCfg activityCfg = activityCfgService.getById(proGroupGoodsBo.getActivityId());
				activityId = proGroupGoodsBo.getActivityId().toString();
				/**
				 * 根据活动Id，获取对应活动下的优惠券信息
				 */
				List<ProCoupon> proCoupons = couponManagerService.getCouponsByActivityId(activityId);
				for (ProCoupon proCoupon : proCoupons) {
						ProCouponVo proCouponVo = new ProCouponVo();
						proCouponVo.setId(proCoupon.getId());
						proCouponVo.setActivityId(Long.parseLong(activityId));
						proCouponVo.setActivityFalge(true);
						proCouponVo.setName("【限" + activityCfg.getActivityName() + "活动商品】\t" + proCoupon.getName());
						proCouponVo.setCouponSill(proCoupon.getCouponSill());
						proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
						String startTimeString = formatter.format(activityCfg.getStartTime());
						String endTimeTimeString = formatter.format(activityCfg.getEndTime());
						proCouponVo.setStartTime(startTimeString);
						proCouponVo.setEndTime(endTimeTimeString);
						proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
						proCouponVos.add(proCouponVo);
				}
			}
		}
		//排序（按优惠力度的从大小排序）
		if(proCouponVos.size()>0){
			for(int i=0;i<proCouponVos.size()-1;i++){
				for(int j=i+1;j<proCouponVos.size();j++){
					ProCouponVo proCoupon1=proCouponVos.get(i);
					ProCouponVo proCoupon2=proCouponVos.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						proCouponVos.set(i, proCoupon2);
						proCouponVos.set(j, proCoupon1);
					}
				}
			}
		} 
		//获取可用优惠券 
		List<ProMyCoupon> proMyCouponList=new ArrayList<>();
		for (ProCouponVo proCouponVo : proCouponVos) {
			ProCouponRel proCouponRel=couponRelService.getRelByActivityIdAndCouponId(Long.parseLong(activityId), proCouponVo.getId());
			List<ProMyCoupon> proMyCouponList1=myCouponMapper.getCouponByUserIdAndRelId(new ProMyCouponQuery(userId, proCouponRel.getId()));
			if( proCouponRel.getRemainNum() > 0 ){//優惠券的剩餘數量大於零
				if(CollectionUtils.isNotEmpty(proMyCouponList1)){
					if( proMyCouponList1.size() < proCouponRel.getLimitNum() ){//领取的数量小于限领的数量则该优惠券还可以领取
						proCouponList.add(proCouponVo);
					}
				}else{
					proCouponList.add(proCouponVo);
				}
			}
			//已经领取的优惠券中未使用的优惠券
			for (ProMyCoupon proMyCoupon : proMyCouponList1) {
				if(StringUtils.equals(proMyCoupon.getStatus(), CouponStatus.COUPON_N.getCode())){
					proMyCoupon.setActivityFalge(true);
					proMyCouponList.add(proMyCoupon);
				}
			}
		}
		//获取与活动无关的优惠券
		ProMyCouponQuery query=new ProMyCouponQuery();
		query.setUserId(userId);
		query.setCouponRelId(-1L);
		query.setStatus("N");
		query.setMaxDate(new Date());
		List<ProMyCoupon> ProMyCoupons=myCouponMapper.getCouponByStatusAndDate(query);
		for (ProMyCoupon proMyCoupon : ProMyCoupons) {
			proMyCoupon.setActivityFalge(false);
			ProCoupon pro = proCouponMapper.selectByPrimaryKey(proMyCoupon.getCouponId());
			if (StringUtils.equals(pro.getType(), CouponType.COUPON_ZDPL.getCode())) {
				if (StringUtils.equals(goodsBasicInfo.getCategoryId1() + "", pro.getCategoryId1())) {
					proMyCouponList.add(proMyCoupon);
				} else if (StringUtils.equals(goodsBasicInfo.getCategoryId2() + "", pro.getCategoryId2())) {
					proMyCouponList.add(proMyCoupon);
				}
			} else if (StringUtils.equals(pro.getType(), CouponType.COUPON_ZDSP.getCode())) {
				String[] similarGoods = pro.getSimilarGoodsCode().split(",");
				if (Arrays.asList(similarGoods).contains(goodsBasicInfo.getGoodsCode())) {
					proMyCouponList.add(proMyCoupon);
				}
			} else if (StringUtils.equals(pro.getType(), CouponType.COUPON_QPL.getCode())) {
				proMyCouponList.add(proMyCoupon);
			}
		}
		for (ProMyCoupon proMyCoupon : proMyCouponList) {
			ProCoupon proCoupon = proCouponMapper.selectByPrimaryKey(proMyCoupon.getCouponId());
			ProCouponVo proCouponVo=new ProCouponVo();
			proCouponVo.setId(proCoupon.getId());
			if(StringUtils.isNotEmpty(activityId)){
				proCouponVo.setActivityId(Long.parseLong(activityId));
			}else{
				proCouponVo.setActivityId(null);
			}
			if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDPL.getCode())) {
				if (StringUtils.equals(goodsBasicInfo.getCategoryId1() + "", proCoupon.getCategoryId1())) {
					Category categroy = categoryMapper.selectByPrimaryKey(Long.parseLong(proCoupon.getCategoryId1()));
				    proCouponVo.setName("【限"+categroy.getCategoryName()+"类】\t"+proCoupon.getName());
				} else if (StringUtils.equals(goodsBasicInfo.getCategoryId2() + "", proCoupon.getCategoryId2())) {
					Category categroy = categoryMapper.selectByPrimaryKey(Long.parseLong(proCoupon.getCategoryId2()));
				    proCouponVo.setName("【限"+categroy.getCategoryName()+"类】\t"+proCoupon.getName());
				}
			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDSP.getCode())) {
				String[] similarGoods = proCoupon.getSimilarGoodsCode().split(",");
				if (Arrays.asList(similarGoods).contains(goodsBasicInfo.getGoodsCode())) {
					GoodsInfoEntity goods = goodsMapper.selectGoodsByGoodsCode(proCoupon.getGoodsCode());
				    proCouponVo.setName("【指定商品】\t" + goods.getGoodsName());
				}
			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_QPL.getCode())) {
			    proCouponVo.setName("【全品类】\t" + proCoupon.getName());

			} else if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_HDSP.getCode())) {
				String activityName = "";
				if (null != proMyCoupon.getCouponRelId()) {
					ProCouponRel rel = couponRelMapper.selectByPrimaryKey(proMyCoupon.getCouponRelId());
					if (null != rel) {// 此做法主要是适配于测试环境直接删库数据不完全，可能导致的问题
						ProActivityCfg cfg = activityCfgMapper.selectByPrimaryKey(rel.getProActivityId());
						if (null != cfg) {
							activityName = cfg.getActivityName();
						}
					}
				}
				proCouponVo.setName("【限" + activityName + "活动商品】\t" + proCoupon.getName());
			}
			
			proCouponVo.setActivityFalge(proMyCoupon.getActivityFalge());
//			proCouponVo.setName(proCoupon.getName());
			proCouponVo.setCouponSill(proCoupon.getCouponSill());
			proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			String startTimeString = formatter.format(proMyCoupon.getStartDate());
			String endTimeTimeString = formatter.format(proMyCoupon.getEndDate());
			proCouponVo.setStartTime(startTimeString);
			proCouponVo.setEndTime(endTimeTimeString);
			proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
			reProCouponList.add(proCouponVo);
		}
		//排序（按优惠力度的从大小排序）
		if(reProCouponList.size()>0){
			for(int i=0;i<reProCouponList.size()-1;i++){
				for(int j=i+1;j<reProCouponList.size();j++){
					ProCouponVo proCoupon1=reProCouponList.get(i);
					ProCouponVo proCoupon2=reProCouponList.get(j);
					if(proCoupon1.getDiscountAmonut().compareTo(proCoupon2.getDiscountAmonut())<0){
						reProCouponList.set(i, proCoupon2);
						reProCouponList.set(j, proCoupon1);
					}
				}
			}
		} 
		resultMap.put("reProCouponList", reProCouponList);
		resultMap.put("proCouponList", proCouponList);
		return resultMap;
	}
	/**
	 * 获取京东商品本身的规格描述
	 * @return
	 */
//	public Map<String,Object> getJdGoodsSimilarSku(Long sku) {
//		Map<String,Object> map=new HashMap<>();
//		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
//		// 京东商品本身的规格参数
//		String jdGoodsSimilarSku = "";
//		for (JdSimilarSku jdSimilarSku : jdSimilarSkuList) {
//			List<JdSaleAttr> saleAttrList = jdSimilarSku.getSaleAttrList();
//			for (JdSaleAttr jdSaleAttr : saleAttrList) {
//				List<String> skuIds = jdSaleAttr.getSkuIds();
//				for (String skuId : skuIds) {
//					if (sku.toString().equals(skuId)) {
//						jdGoodsSimilarSku = jdGoodsSimilarSku+jdSaleAttr.getSaleValue() + " ";
//					}
//				}
//			}
//		}
//		map.put("jdGoodsSimilarSku", jdGoodsSimilarSku);
//		map.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
//		return map;
//	}
	
	/**
	 * 获取京东商品本身的规格描述
	 * @return
	 */
	public Map<String,Object> getJdGoodsSimilarSku(Long sku) {
		Map<String,Object> map=new HashMap<>();
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
		// 京东商品本身的规格参数
		String jdGoodsSimilarSku = "";
		for (JdSimilarSku jdSimilarSku : jdSimilarSkuList) {
			List<JdSaleAttr> saleAttrList = jdSimilarSku.getSaleAttrList();
			for (JdSaleAttr jdSaleAttr : saleAttrList) {
				List<String> skuIds = jdSaleAttr.getSkuIds();
				for (String skuId : skuIds) {
					if (sku.toString().equals(skuId)) {
						jdGoodsSimilarSku = jdGoodsSimilarSku+jdSaleAttr.getSaleValue() + " ";
					}
				}
			}
		}
		map.put("jdGoodsSimilarSku", jdGoodsSimilarSku);
		map.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
		return map;
	}
	
	/**
	 * 根据商品编号，获取商品明细信息(sku为8位时为图书音像类目商品)
	 */
	public JdGoodsBooks getJdGoodsBooksInfoBySku(Long sku) {
		if (sku.toString().length() != 8) {
			return null;
		}
		JdGoodsBooks jdGoodsBooks = new JdGoodsBooks();
		try {
			productApiClient.getWeiZhiRelatedProductDetail(sku+"");
		} catch (Exception e) {
			logger.error("call weizhi method getWeiZhiRelatedProductDetail(goodsBook) is failed!!! {}",e);
		}
		return jdGoodsBooks;
	}
//	public JdGoodsBooks getJdGoodsBooksInfoBySku(Long sku) {
//		if (sku.toString().length() != 8) {
//			return null;
//		}
//		JdGoodsBooks jdGoodsBooks = new JdGoodsBooks();
//		// 查询图书音像类目商品信息
//		JdApiResponse<JSONObject> jdGoodsBooksDetail = jdProductApiClient.productDetailQuery(sku);
//		if (null != jdGoodsBooksDetail && null != jdGoodsBooksDetail.getResult() && jdGoodsBooksDetail.isSuccess()) {
//			jdGoodsBooks = JSONObject.parseObject(jdGoodsBooksDetail.getResult().toString(),  new TypeReference<JdGoodsBooks>(){});
//		}
//		return jdGoodsBooks;
//	}
	/**
	 * 根据商品编号，获取商品明细信息(sku不为8位时为非图书音像类目商品)
	 */
	public Product getJdGoodsInfoBySku(Long sku) {
		if (sku.toString().length() == 8) {
			return null;
		}
		Product jdGoods = new Product();
		try {
			jdGoods = productApiClient.getWeiZhiProductDetail(sku+"");
		} catch (Exception e) {
			logger.error("call weizhi method getWeiZhiRelatedProductDetail(goodsBook) is failed!!! {}",e);
		}
		return jdGoods;
	}
//	public JdGoods getJdGoodsInfoBySku(Long sku) {
//		if (sku.toString().length() == 8) {
//			return null;
//		}
//		JdGoods jdGoods = new JdGoods();
//		// 查询图书音像类目商品信息
//		JdApiResponse<JSONObject> jdGoodsBooksDetail = jdProductApiClient.productDetailQuery(sku);
//		if (null != jdGoodsBooksDetail && null != jdGoodsBooksDetail.getResult() && jdGoodsBooksDetail.isSuccess()) {
//			jdGoods = JSONObject.parseObject(jdGoodsBooksDetail.getResult().toString(),  new TypeReference<JdGoods>(){});
//		}
//		return jdGoods;
//	}

	/**
	 * 根据商品编号，获取商品价格
	 *
	 * @param sku
	 * @return
	 */
	public List<JdSellPrice> getJdSellPriceBySku(List<String> sku) {
		List<JdSellPrice> jdSellPriceList = new ArrayList<>();
		List<WZPriceResponse> priceList = new ArrayList<>();
		try {
			priceList = priceApiClient.getWzPrice(sku);
			if(CollectionUtils.isNotEmpty(priceList)){
				for (WZPriceResponse price : priceList) {
					JdSellPrice jdPrice = new JdSellPrice();
					jdPrice.setJdPrice(new BigDecimal(price.getJDPrice()));
					jdPrice.setPrice(new BigDecimal(price.getWzPrice()));
					jdPrice.setSkuId(Long.parseLong(price.getSkuId()));
					jdSellPriceList.add(jdPrice);
				}
			}
		} catch (Exception e) {
			logger.error("call method getWzPrice is failed!!!{}",e);
		}
		return jdSellPriceList;
	}
//	public List<JdSellPrice> getJdSellPriceBySku(Collection<Long> sku) {
//		List<JdSellPrice> jdSellPriceList = new ArrayList<>();
//		JdApiResponse<JSONArray> jdSellPrice = jdProductApiClient.priceSellPriceGet(sku);
//		if (null != jdSellPrice && null != jdSellPrice.getResult() && jdSellPrice.isSuccess()) {
//			for (int i = 0; i < jdSellPrice.getResult().size(); i++) {
//				JdSellPrice jp = JSONObject.parseObject(jdSellPrice.getResult().getString(i),  new TypeReference<JdSellPrice>(){});
//				jdSellPriceList.add(jp);
//			}
//		}
//		return jdSellPriceList;
//	}
	/**
	 * 查询商品的图片地址信息（根据商品编号和图片类型）
	 * @param sku
	 * @param type{n0(最大图)、n1(350*350px)、n2(160*160px)、n3(130*130px)、n4(100*100px) }
	 * @return
	 */
	public List<String> getJdImagePathListBySku(Long sku, String type) {
		List<Long> skusImage = new ArrayList<>();
		skusImage.add(sku);
		List<String> JdImagePathList = new ArrayList<>();
		try {
			JdImagePathList = productService.getWeiZhiSingleProductSkuImage(sku+"",type);
		} catch (Exception e) {
			logger.error("call weizhi getWeiZhiProductSkuImage is failed!!!,{}",e);
		}
		return JdImagePathList;
	}
//	public List<String> getJdImagePathListBySku(Long sku, String type) {
//		List<Long> skusImage = new ArrayList<>();
//		skusImage.add(sku);
//		List<String> JdImagePathList = new ArrayList<>();
//		JdApiResponse<JSONObject> jdImageResponse = jdProductApiClient.productSkuImageQuery(skusImage);
//		if (null != jdImageResponse && null != jdImageResponse.getResult() && jdImageResponse.isSuccess()) {
//			Map<String, List<JdImage>> jsonImageResult = JSONObject.parseObject(jdImageResponse.getResult().toString(),
//					new TypeReference<Map<String, List<JdImage>>>(){});
//
//			List<JdImage> jdList = jsonImageResult.get(sku.toString());
//			for (int i = 0; i < jdList.size(); i++) {
//				String path = jdList.get(i).getPath();
//				String pathJd = "http://img13.360buyimg.com/" + type + "/" + path;
//				JdImagePathList.add(pathJd);
//			}
//		}
//		return JdImagePathList;
//	}
	/**
	 * 查询商品的图片信息（根据商品编号）
	 * @param sku
	 * @return
	 */
	public List<JdImage> getJdImageListBySku(Long sku) {
		List<JdImage> jdList = new ArrayList<>();
		List<WzPicture> skuPictureList = new ArrayList<>();
		try {
			skuPictureList = productService.getWeiZhiSingleProductSkuImage(sku+"");
			for (WzPicture wz : skuPictureList) {
				JdImage jd = new JdImage();
				jd.setIsPrimary(wz.getIsPrimary());
				jd.setOrderSort(wz.getOrderSort());
				jd.setPath(wz.getPath());
				jdList.add(jd);
			}
		} catch (Exception e) {
			logger.error("call weizhi getWeiZhiProductSkuImage is failed!!!,{}",e);
		}
		return jdList;
	}
//	public List<JdImage> getJdImageListBySku(Long sku) {
//		Gson gson = new Gson();
//		List<Long> skusImage = new ArrayList<>();
//		skusImage.add(sku);
//		List<JdImage> jdList = new ArrayList<>();
//		JdApiResponse<JSONObject> jdImageResponse = jdProductApiClient.productSkuImageQuery(skusImage);
//		if (null != jdImageResponse && null != jdImageResponse.getResult() && jdImageResponse.isSuccess()) {
//			Map<String, List<JdImage>> jsonImageResult = gson.fromJson(jdImageResponse.getResult().toString(),
//					new TypeToken<Map<String, List<JdImage>>>() {
//					}.getType());
//			jdList = jsonImageResult.get(sku);
//		}
//		return jdList;
//	}
	/**
	 * 查询商品的图片信息（根据商品编号(多个sku)）
	 * @param sku
	 * @return
	 */
//	public Map<String, List<JdImage>> getJdImageInfoListBySku(List<Long> skus) {
//		Gson gson = new Gson();
//		Map<String, List<JdImage>> jsonImageResult = new HashMap<>();
//		JdApiResponse<JSONObject> jdImageResponse = jdProductApiClient.productSkuImageQuery(skus);
//		if (null != jdImageResponse && null != jdImageResponse.getResult() && jdImageResponse.isSuccess()) {
//			jsonImageResult = gson.fromJson(jdImageResponse.getResult().toString(),
//					new TypeToken<Map<String, List<JdImage>>>() {
//					}.getType());
//		}
//		return jsonImageResult;
//	}

	/**
	 * 同类商品查询(根据商品编号sku)
	 *
	 * @return
	 * @throws Exception 
	 */
	public List<JdSimilarSku> getJdSimilarSkuList(Long sku) {
		List<JdSimilarSku> JdSimilarSkuList = new ArrayList<>();
		if(null == sku){
			return JdSimilarSkuList;
		}
		try {
			JdSimilarSkuList = productService.getWeiZhiSimilarSku(sku+"");
			if(CollectionUtils.isNotEmpty(JdSimilarSkuList)){
				int size = JdSimilarSkuList.size();
				for (int i=0; i<size; i++) {
					JdSimilarSkuList.get(i).update(JdSimilarSkuList.get(i).getSaleAttrList());
//					JdSimilarSkuList.add(JdSimilarSkuList.get(i));
				}
			}
		} catch (Exception e) {
			logger.error("call method getJdSimilarSkuList is failed!!!--------->>>>{}",e);
			return JdSimilarSkuList;
		}
		return JdSimilarSkuList;
	}


	/**
	 * 京东商品：根据skuId返回相似skuId
	 * @return
     */
	public TreeSet<String> getJdSimilarSkuIdList(String skuId) {
		if(StringUtils.isBlank(skuId)){
			return null;
		}
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(Long.valueOf(skuId));
		TreeSet<String> skusSet = new TreeSet<String>();
		for (JdSimilarSku jdsk : jdSimilarSkuList) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			for (JdSaleAttr jdsa : saleAttrList) {
				skusSet.addAll(jdsa.getSkuIds());
			}
		}
		return skusSet;
	}
	/**
     * 京东商品：根据skuId返回相似skuId   无规格时包括插入本条sku
     * @return
     */
    public TreeSet<String> getJdSimilarSkuId(String skuId) {
        if(StringUtils.isBlank(skuId)){
            return null;
        }
        TreeSet<String> set = getJdSimilarSkuIdList(skuId);
        if(set==null||set.size()==0){
            set.add(skuId);
        }
        return set;
    }
	/**
	 * 判断单个京东商品是否上下架
	 * @return
	 */
	public Boolean jdProductStateQuery(Long sku){
		Boolean falge=false;
		
		List<JdProductState> stateList = new ArrayList<>();
		try {
			stateList = productApiClient.getWeiZhiProductSkuState(sku+"");
			if(CollectionUtils.isNotEmpty(stateList)){
				int state=stateList.get(0).getState();
				return state == 1 ? true : false;
			}
		} catch (Exception e) {
			logger.error("call wz method getWeiZhiProductSkuState is failed!!!!{}",e);
		}
		return falge;
	}
//	public Boolean jdProductStateQuery(Long sku){
//		Boolean falge=false;
//		Collection<Long> skus=new ArrayList<>();
//		skus.add(sku);
//		JdApiResponse<JSONArray> jdProductStateResponse=jdProductApiClient.productStateQuery(skus);
//		List<JdProductState> JdProductStateList=new ArrayList<>();
//		if (null != jdProductStateResponse && null != jdProductStateResponse.getResult() && jdProductStateResponse.isSuccess()) {
//			for(int i = 0; i < jdProductStateResponse.getResult().size(); i++){
//				JdProductState jps = JSONObject.parseObject(jdProductStateResponse.getResult().getString(i),  new TypeReference<JdProductState>(){});
//				JdProductStateList.add(jps);
//			}
//		}
//		int state=JdProductStateList.get(0).getState();
//		if(1==state){
//			 falge=true;
//		}
//		return falge;
//	}
	/**
	 * 查询商品的规格(根据商品sku查询商品本身的规格)
	 * 例如：("颜色":"金色","版本":"全网通")
	 */
	public Map<String,String> getJdGoodsSpecification(Long sku) {
		List<JdSimilarSku> jdSimilarSkuList = getJdSimilarSkuList(sku);
		Map<String, String> map = new HashMap<>();
		for (JdSimilarSku jdSimilarSku : jdSimilarSkuList) {
			String saleName = jdSimilarSku.getSaleName();
			String saleValue = "";
			List<JdSaleAttr> saleAttrList = jdSimilarSku.getSaleAttrList();
			for (JdSaleAttr jdSaleAttr : saleAttrList) {
				List<String> skuIds = jdSaleAttr.getSkuIds();
				for (String skuid : skuIds) {
					if (sku.toString().equals(skuid)) {
						saleValue = jdSaleAttr.getSaleValue();
						break;
					}
				}
			}
			map.put(saleName, saleValue);
		}
		return map;
	}
	/**
	 * 获取商品库存接口
	 */
//	public JdGoodStock stockForListBatget(String sku, Region region) {
//		Gson gson = new Gson();
//		List<JdGoodStock> jdGoodStockList = new ArrayList<>();
//		JdApiResponse<JSONArray> stockForListBatgetResponse = jdProductApiClient.stockForListBatget(sku, region);
//		if (null != stockForListBatgetResponse && null != stockForListBatgetResponse.getResult() && stockForListBatgetResponse.isSuccess()) {
//			for (int i = 0; i < stockForListBatgetResponse.getResult().size(); i++) {
//				JdGoodStock jdstock= gson.fromJson(stockForListBatgetResponse.getResult().getString(i), JdGoodStock.class);
//				jdGoodStockList.add(jdstock);
//			}
//		}
//		return jdGoodStockList.get(0);
//	}
	   /**
     * 获取当个sku库存接口（建议订单详情页、下单使用）
     *
     * @return
     */
	public String getStockBySku(String sku, Region region) {
		return getStockBySkuNum(sku, region, 1);
    }
//    public String getStockBySku(String sku, Region region) {
//    	int isStock=0;
//    	List<SkuNum> skuNums =new ArrayList<>();
//    	SkuNum skuNum=new SkuNum();
//    	skuNum.setSkuId(Long.parseLong(sku));
//    	skuNum.setNum(1);
//    	skuNums.add(skuNum);
//        List<Stock> result =jdProductApiClient.getStock(skuNums, region);
//        if(result.size()==1){
//        	isStock=result.get(0).getStockStateId();
//        }
//        if(33==isStock|| 39==isStock||40==isStock){
//        	return "有货";
//        }else{
//        	return "无货";
//        }
//    }
    /**
     * 获取当个sku库存接口（建议订单详情页、下单使用）
     *
     * @param skuNums
     * @param region
     * @return
     */
	public String getStockBySkuNum(String sku, Region region,Integer num) {
        List<StockNum> skuNums =new ArrayList<>();
		StockNum skuNum=new StockNum();
    	skuNum.setSkuId(Long.parseLong(sku));
    	skuNum.setNum(num);
    	skuNums.add(skuNum);
		List<GoodsStock> stockList = Lists.newArrayList();
		try {
			stockList = productApiClient.getNewStockById(skuNums, region);
		} catch (Exception e) {
			logger.error("call weizhi getNewStockById is failed!!!!{}",e);
		}
        if(CollectionUtils.isEmpty(stockList)){
        	return "无货";
        }
        GoodsStock stock = stockList.get(0);
    	return stock.getStockStateDesc();
    }

    
    /**
     * 根据skuid 获取  商品详情的css
     * @param skuId
     * @return
     */
    public String getSkuCss(Long sku){
    	//TODO
    	//TODO
    	//TODO
		return "";
    }


}
