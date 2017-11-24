package com.apass.esp.service.wz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.jd.JdGoods;
import com.apass.esp.domain.entity.jd.JdGoodsBooks;
import com.apass.esp.domain.entity.jd.JdSimilarSku;
import com.apass.esp.domain.entity.jd.JdSimilarSkuTo;
import com.apass.esp.domain.enums.JdGoodsImageType;
import com.apass.esp.search.entity.Goods;
import com.apass.esp.search.enums.IndexType;
import com.apass.esp.search.manager.IndexManager;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;
import com.apass.esp.third.party.weizhi.entity.WzSkuPicture;
import com.apass.gfb.framework.exception.BusinessException;
import com.google.common.collect.Maps;

@Service
public class WeiZhiGoodsInfoService {
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	@Autowired
	private WeiZhiProductService weiZhiProductService;
	/**
	 * 微知根据商品编号获取商品需要展示App信息
	 * 
	 * @throws BusinessException
	 */
	public Map<String, Object> getAppWzGoodsAllInfoBySku(Long sku, String goodsId, Region region) {
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
					jddetail = product.getIntroduction().replaceAll("src=\"//", "src=\"http://").replaceAll("href=\'//","href=\'http://");
				}
				// map.put("goodsName", jdGoods.getName());// 商品名称
				// java字符串转义,把&lt;&gt;转换成<>等字符
				
				//微知没有根据skuid 获取  商品详情的css的接口
//				String skuCss = getSkuCss(sku).replaceAll("background-image:url\\(//",
//						"background-image:url\\(http://");
				String introduction = jddetail.replaceAll("width", "width");
//				map.put("googsDetail", StringEscapeUtils.unescapeXml(skuCss + introduction));// 商品详情
				map.put("googsDetail",introduction);
			}
			// 查看商品的邮费
			List<Long> goodsIds = new ArrayList<>();
			goodsIds.add(sku);
			// BigDecimal postage = goodsService.getPostage(goodsIds);
			map.put("postage", "0");
			// 查询商品图片
			List<String> wzSkuPictureList = weiZhiProductService.getWeiZhiSingleProductSkuImage(sku.toString());
			map.put("jdImagePathList", wzSkuPictureList);
			// 查询商品是否有货
			String jdGoodStock = weiZhiProductService.getStockBySku(sku.toString(), region);
			map.put("goodsStockDes", jdGoodStock);
//			//查询京东商品规格
//			Map<String, Object> map2 = getJdSimilarSkuInfoList(sku, region);
//			map.put("JdSimilarSkuToList", map2.get("JdSimilarSkuToList"));
//			map.put("skuId", map2.get("skuId"));
//			map.put("jdSimilarSkuList", map2.get("jdSimilarSkuList"));
//			map.put("jdSimilarSkuListSize", map2.get("jdSimilarSkuListSize"));
			
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
	
	
	
}
