package com.apass.esp.service.wz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.dto.ProGroupGoodsBo;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.goods.GoodsInfoEntity;
import com.apass.esp.domain.entity.goods.GoodsStockInfoEntity;
import com.apass.esp.domain.entity.jd.JdGoodsBooks;
import com.apass.esp.domain.entity.jd.JdSaleAttr;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.domain.entity.jd.JdSimilarSkuTo;
import com.apass.esp.domain.entity.jd.JdSimilarSkuVo;
import com.apass.esp.domain.enums.JdGoodsImageType;
import com.apass.esp.domain.vo.ProCouponGoodsDetailVo;
import com.apass.esp.repository.goods.GoodsRepository;
import com.apass.esp.repository.goods.GoodsStockInfoRepository;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.service.common.CommonService;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.jd.JdGoodsInfoService;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.ProGroupGoodsService;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.gfb.framework.exception.BusinessException;
import com.google.common.collect.Maps;

@Service
public class WeiZhiGoodsInfoService {
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	@Autowired
	private WeiZhiProductService weiZhiProductService;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsStockInfoRepository goodsStockInfoRepository;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProGroupGoodsService proGroupGoodsService;
	@Autowired
	private  GoodsService  goodsService;
	@Autowired
	private CouponManagerService couponManagerService;
	@Autowired
	private JdGoodsInfoService  jdGoodsInfoService;
	/**
	 * 微知根据商品编号获取商品需要展示App信息
	 * 
	 * @throws BusinessException
	 */
	public Map<String, Object> getAppWzGoodsAllInfoBySku(Long sku, String goodsId, Region region,String userId,Map<String,Object> checkMap) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			if (sku.toString().length() == 8) {
				// 查询商品名称（图书音像类目）
				JdGoodsBooks jdGoodsBooks = weiZhiProductApiClient.getWeiZhiRelatedProductDetail(sku.toString());
				map.put("relatedProducts", jdGoodsBooks.getRelatedProducts());
			} else {
				String jddetail = "";
				// 从ES中查询商品详情信息
				Goods goods = IndexManager.getDocument("goods", IndexType.GOODS, Integer.parseInt(goodsId));
				if (null != goods && StringUtils.isNotBlank(goods.getGoodsDetail())) {
					jddetail = goods.getGoodsDetail().replaceAll("src=\"//", "src=\"http://");
				} else {
					// 查询商品名称
					Product product = weiZhiProductApiClient.getWeiZhiProductDetail(sku.toString());
					jddetail = product.getIntroduction().replaceAll("src=\"//", "src=\"http://").replaceAll("href=\'//",
							"href=\'http://");
				}
				
				//*** 微知没有根据skuid 获取 商品详情的css的接口
				// String skuCss =
				// getSkuCss(sku).replaceAll("background-image:url\\(//",
				// "background-image:url\\(http://");
				String introduction = jddetail.replaceAll("width", "width");
				// map.put("googsDetail", StringEscapeUtils.unescapeXml(skuCss +
				// introduction));// 商品详情
				map.put("googsDetail", introduction);
			}
			// 查询商品图片
			List<String> wzSkuPictureList = weiZhiProductService.getWeiZhiSingleProductSkuImage(sku.toString(), JdGoodsImageType.TYPEN1.getCode());
			map.put("jdImagePathList", wzSkuPictureList);
			// 查询商品是否有货
			String jdGoodStock = weiZhiProductService.getStockBySku(sku.toString(), region);
			map.put("goodsStockDes", jdGoodStock);
			checkMap.put("goodsStockDes", jdGoodStock);
			// //查询京东商品规格
			 Map<String, Object> map2 = jdGoodsInfoService.getJdSimilarSkuInfoList(sku, region,userId,checkMap);
			 map.put("JdSimilarSkuToList", map2.get("JdSimilarSkuToList"));
			 map.put("skuId", map2.get("skuId"));
			 map.put("jdSimilarSkuList", map2.get("jdSimilarSkuList"));
			 map.put("jdSimilarSkuListSize",
			 map2.get("jdSimilarSkuListSize"));

		} catch (Exception e) {
			List<JdSimilarSku> jdSimilarSkuList = new ArrayList<>();
			List<JdSimilarSkuTo> JdSimilarSkuToList = new ArrayList<>();
			map.put("JdSimilarSkuToList", JdSimilarSkuToList);
			map.put("skuId", sku);
			map.put("jdSimilarSkuList", jdSimilarSkuList);
			map.put("jdSimilarSkuListSize", jdSimilarSkuList.size());
		}
		return map;
	}

	// 微知查询商品规格（包括库存）
	public Map<String, Object> getJdSimilarSkuInfoList(Long sku, Region region) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		TreeSet<String> skusSet = new TreeSet<String>();
		List<JdSimilarSku> jdSimilarSkuList = weiZhiProductService.getWeiZhiSimilarSku(sku.toString());
		List<JdSimilarSku> jdSimilarSkuList2 = new ArrayList<>();
		for (JdSimilarSku jdsk : jdSimilarSkuList) {
			List<JdSaleAttr> saleAttrList = jdsk.getSaleAttrList();
			List<JdSaleAttr> saleAttrList2 = new ArrayList<>();
			for (JdSaleAttr jdsa : saleAttrList) {
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

			// 为京东商品添加库存
			jdSimilarSkuVo.setStockCurrAmt(Long.parseLong("200"));

			String skuId = iterator.next();
			// 查询商品价格
			GoodsInfoEntity goodsInfo = goodsRepository.selectGoodsByExternalId(skuId);
			jdSimilarSkuVo.setGoodsId(goodsInfo.getId().toString());
			Long goodsId = goodsInfo.getId();
			List<GoodsStockInfoEntity> jdGoodsStockInfoList = goodsStockInfoRepository.loadByGoodsId(goodsId);
			if (jdGoodsStockInfoList.size() == 1) {
				BigDecimal price = commonService.calculateGoodsPrice(goodsId, jdGoodsStockInfoList.get(0).getId());
				jdSimilarSkuVo.setGoodsStockId(jdGoodsStockInfoList.get(0).getId().toString());
				jdSimilarSkuVo.setPrice(price);
				jdSimilarSkuVo
						.setPriceFirst((new BigDecimal("0.1").multiply(price)).setScale(2, BigDecimal.ROUND_DOWN));
			}
			// 查询商品是否有货
			String jdGoodStock = weiZhiProductService.getStockBySku(skuId.toString(), region);
			// 是否支持7天无理由退货,Y、N
			String support7dRefund = weiZhiProductService.getsupport7dRefund(skuId);
			// 满减活动字段
			String activityCfg = goodsService.getActivityInfo(goodsId,skuId);
			// 添加活动id
			ProGroupGoodsBo proGroupGoodsBo = proGroupGoodsService.getBySkuId(goodsId,skuId);
			if (null != proGroupGoodsBo) {
				jdSimilarSkuVo.setProActivityId(proGroupGoodsBo.getActivityId());
			}
			// 获取商品的优惠券
			List<String> proCoupons = getProCouponList(goodsId);
			if (proCoupons.size() > 3) {
				jdSimilarSkuVo.setProCouponList(proCoupons.subList(0, 3));
			} else {
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
			jdSimilarSkuTo.setSkuIdOrder(skuIdOrder);
			jdSimilarSkuTo.setJdSimilarSkuVo(jdSimilarSkuVo);
			JdSimilarSkuToList.add(jdSimilarSkuTo);
			if (skuId.equals(sku.toString())) {
				isSelectSkuIdOrder = skuIdOrder;
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
					}
				}
			}
		} else {
			JdSimilarSkuToList = new ArrayList<>();
			jdSimilarSkuList2 = new ArrayList<>();
		}
		map.put("JdSimilarSkuToList", JdSimilarSkuToList);
		map.put("skuId", String.valueOf(sku));
		map.put("jdSimilarSkuList", jdSimilarSkuList2);
		map.put("jdSimilarSkuListSize", jdSimilarSkuList2.size());
		return map;
	}
	
	/**
	 * 获取商品详情中的的优惠券并按优惠力度从大到小排序
	 */
	public List<String> getProCouponList(Long goodsId){
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
	
	
}
