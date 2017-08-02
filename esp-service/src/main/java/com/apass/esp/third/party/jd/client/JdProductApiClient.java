package com.apass.esp.third.party.jd.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.common.utils.UrlUtils;
import com.apass.esp.domain.entity.WorkCityJd;
import com.apass.esp.mapper.WorkCityJdMapper;
import com.apass.esp.third.party.jd.entity.base.Region;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.esp.third.party.jd.entity.product.SearchCondition;
import com.apass.esp.third.party.jd.entity.product.Stock;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdProductApiClient extends JdApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdProductApiClient.class);

    @Autowired
    private WorkCityJdMapper workCityJdMapper;
    /**
     * 查询用户对应的商品池编号
     *
     * @param
     * @return
     */
    public JdApiResponse<JSONArray> productPageNumQuery() {
        return request("biz.product.PageNum.query", null, "biz_product_PageNum_query_response", JSONArray.class);
    }

    /**
     * 根据区商品池编号，获取池内商品编号
     *
     * @param pageNum
     * @return
     */
    public JdApiResponse<String> productSkuQuery(int pageNum) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum", String.valueOf(pageNum));
        return request("biz.product.sku.query", jsonObject, "biz_product_sku_query_response", String.class);
    }

    /**
     * 根据区商品池编号，获取池内商品编号
     *
     * @param sku
     * @return
     */
    public JdApiResponse<JSONObject> productDetailQuery(long sku) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", String.valueOf(sku));
        return request("biz.product.detail.query", jsonObject, "biz_product_detail_query_response", JSONObject.class);
    }

    /**
     * 根据商品编号，获取商品上下架状态(支持批量，以【，】分割，最高100个)
     *
     * @param skus
     * @return
     */
    public JdApiResponse<JSONArray> productStateQuery(Collection<Long> skus) {
        String join = StringUtils.join(skus, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", join);
        return request("biz.product.state.query", jsonObject, "biz_product_state_query_response", JSONArray.class);
    }

    /**
     * 根据商品编号，获取图片信息(支持批量，以【，】分割，最高100个)
     *
     * @param skus
     * @return
     */
    public JdApiResponse<JSONObject> productSkuImageQuery(List<Long> skus) {
        String join = StringUtils.join(skus, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", join);
        return request("biz.product.skuImage.query", jsonObject, "biz_product_skuImage_query_response", JSONObject.class);
    }

    /**
     * 根据商品编号，获取商品评价数据(支持批量，以【，】分割，最高50个)
     *
     * @param skus
     * @return
     */
    public JdApiResponse<JSONArray> productCommentSummarysQuery(List<Long> skus) {
        String join = StringUtils.join(skus, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", join);
        return request("biz.product.commentSummarys.query", jsonObject, "biz_product_commentSummarys_query_response", JSONArray.class);
    }

    /**
     * 查看商品在选定地点是否可以购买(支持批量，以【，】分割，最高50个)
     *
     * @param skus
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    public JdApiResponse<JSONArray> productCheckAreaLimitQuery(List<Long> skus, int province, int city, int county, int town) {
        String join = StringUtils.join(skus, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuIds", join);
        jsonObject.put("province", province);
        jsonObject.put("city", city);
        jsonObject.put("county", county);
        jsonObject.put("town", town);
        return request("biz.product.checkAreaLimit.query", jsonObject, "biz_product_checkAreaLimit_query_response", JSONArray.class);
    }

    /**
     * 根据商品在选定地点是否支持货到付款(支持批量，以【，】分割，最高50个)
     *
     * @param skus
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    public JdApiResponse<Boolean> productIsCodQuery(List<Long> skus, int province, int city, int county, int town) {
        String join = StringUtils.join(skus, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuIds", join);
        jsonObject.put("province", province);
        jsonObject.put("city", city);
        jsonObject.put("county", county);
        jsonObject.put("town", town);
        return request("biz.product.isCod.query", jsonObject, "biz_product_isCod_query_response", Boolean.class);
    }

    /**
     * 查询赠品信息接口
     *
     * @param skus
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    public JdApiResponse<JSONObject> productSkuGiftQuery(long sku, int province, int city, int county, int town) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuId", sku);
        jsonObject.put("province", province);
        jsonObject.put("city", city);
        jsonObject.put("county", county);
        jsonObject.put("town", town);
        return request("biz.product.skuGift.query", jsonObject, "biz_product_skuGift_query_response", JSONObject.class);
    }

    /**
     * 运费查询接口，用来查询下单购买的sku对应的运费
     *
     * @param skus
     * @param province
     * @param city
     * @param county
     * @param town
     * @return
     */
    public JdApiResponse<JSONObject> orderFreightGet(List<SkuNum> skus, Region region) {
        //TODO
        //未验证成功


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", JSONObject.toJSONString(skus));
        jsonObject.put("province", region.getProvinceId());
        jsonObject.put("city", region.getCityId());
        jsonObject.put("county", region.getCountyId());
        jsonObject.put("town", region.getTownId());
        jsonObject.put("paymentType", 4);
        return request("biz.order.freight.get", jsonObject, "biz_order_freight_get_response", JSONObject.class);
    }

    /**
     * 商品可售验证接口(支持批量，以【，】分割，最高100个)
     *
     * @param skuIds：商品id列表
     * @return
     */
    public JdApiResponse<JSONArray> productSkuCheck(List<Long> skuIds) {
        String join = StringUtils.join(skuIds, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuIds", join);
        return request("biz.product.sku.check", jsonObject, "biz_product_sku_check_response", JSONArray.class);
    }

    /**
     * 商品可售验证
     * @param skuNumList：商品对象列表
     * @return
     */
    public JdApiResponse<JSONArray> productSkuCheckWithSkuNum(List<SkuNum> skuNumList) {
        List<Long> skuIds = new ArrayList<>(skuNumList.size());
        for (SkuNum skuNum : skuNumList) {
            skuIds.add(skuNum.getSkuId());
        }
        return productSkuCheck(skuIds);
    }

    /**
     * 批量查询价格(支持批量，以【，】分割，最高100个)
     *
     * @param sku
     * @return
     */
    public JdApiResponse<JSONArray> priceSellPriceGet(Collection<Long> sku) {
        String join = StringUtils.join(sku, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", join);
        return request("biz.price.sellPrice.get", jsonObject, "biz_price_sellPrice_get_response", JSONArray.class);
    }
    
    /**
     * 批量查询价格(支持批量，以【，】分割，最高100个)
     *
     * @param sku
     * @return
     */
    public JdApiResponse<JSONArray> priceSellPriceGet(List<SkuNum> skuNumList) {
    	
    	List<Long> skuIds = new ArrayList<>(skuNumList.size());
        for (SkuNum skuNum : skuNumList) {
            skuIds.add(skuNum.getSkuId());
        }
        String join = StringUtils.join(skuIds, ",");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", join);
        return request("biz.price.sellPrice.get", jsonObject, "biz_price_sellPrice_get_response", JSONArray.class);
    }

    private Cache<String, JdApiResponse<JSONObject>> addressCache = CacheBuilder.newBuilder().maximumSize(10240)
            .expireAfterWrite(1, TimeUnit.HOURS).build();

	/**
	 * 查询京东一二级地址并保存数据库
	 * 
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 */
	public void queryAddress(){
		WorkCityJd wcjd0 = new WorkCityJd();
		wcjd0.setCode("0");
		wcjd0.setProvince("中华人民共和国");
		wcjd0.setParent(Long.parseLong("-1"));
		wcjd0.setLevel("0");
		workCityJdMapper.insertSelective(wcjd0);
		JdApiResponse<JSONObject> resultProvin = request("biz.address.allProvinces.query", null,
				"biz_address_allProvinces_query_response", JSONObject.class);
		if (resultProvin.isSuccess()) {
			Map<String, Object> mapProvin = resultProvin.getResult();
			for (Map.Entry<String, Object> entryProvin : mapProvin.entrySet()) {
				WorkCityJd wcjd = new WorkCityJd();
				wcjd.setCode(entryProvin.getValue().toString());
				wcjd.setProvince(entryProvin.getKey());
				wcjd.setParent(Long.parseLong("0"));
				wcjd.setLevel("1");
				workCityJdMapper.insertSelective(wcjd);
			}
			for (Map.Entry<String, Object> entryProvin : mapProvin.entrySet()) {
				JdApiResponse<JSONObject> resultCity = addressCitysByProvinceIdQuery(
						Integer.parseInt(entryProvin.getValue().toString()));
				if (resultCity.isSuccess()) {
					Map<String, Object> mapCity = resultCity.getResult();
					for (Map.Entry<String, Object> entryCity : mapCity.entrySet()) {
						WorkCityJd wcjdCity = new WorkCityJd();
						wcjdCity.setCode(entryCity.getValue().toString());
						wcjdCity.setCity(entryCity.getKey());
						wcjdCity.setParent(Long.parseLong(entryProvin.getValue().toString()));
						wcjdCity.setLevel("2");
						workCityJdMapper.insertSelective(wcjdCity);
					}
				}
			}
		}
	}
	
	/**
	 * 查询二级类目插入三级类目
	 * 
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 */
	public void queryDistrict() throws NumberFormatException, InterruptedException {
		List<String> cityIdList = workCityJdMapper.selectAllCity();
		for (int i = 0; i < cityIdList.size(); i++) {
			JdApiResponse<JSONObject> resultDistrict = addressCountysByCityIdQuery(Integer.parseInt(cityIdList.get(i)));
			if (null == resultDistrict) {
				System.out.println("cityIdListid---------->" + cityIdList.get(i));
				continue;
			}
			if (null == resultDistrict.getResult()) {
				continue;
			}
			if (resultDistrict.isSuccess()) {
				Map<String, Object> mapDistrict = resultDistrict.getResult();
				for (Map.Entry<String, Object> entryDistrict : mapDistrict.entrySet()) {
					WorkCityJd wcjdDistrict = new WorkCityJd();
					wcjdDistrict.setCode(entryDistrict.getValue().toString());
					wcjdDistrict.setDistrict(entryDistrict.getKey());
					wcjdDistrict.setParent(Long.parseLong(cityIdList.get(i)));
					wcjdDistrict.setLevel("3");
					workCityJdMapper.insertSelective(wcjdDistrict);
				}
			}
			Thread.sleep(1000);
		}
	}
	/**
	 * 查询三级类目插入四级类目
	 * @throws InterruptedException 
	 * @throws NumberFormatException 
	 */
	public void queryTowns() throws NumberFormatException, InterruptedException {
		List<String> DistrictIdList = workCityJdMapper.selectDistrict();
		for (int i = 0; i < DistrictIdList.size(); i++) {
			JdApiResponse<JSONObject> resultTowns = addressTownsByCountyIdQuery(
					Integer.parseInt(DistrictIdList.get(i)));
			if (null == resultTowns) {
				System.out.println("resultTownsId---------->" + DistrictIdList.get(i));
				continue;
			}
			if (null == resultTowns.getResult()) {
				continue;
			}
			if (resultTowns.isSuccess()) {
				Map<String, Object> mapTowns = resultTowns.getResult();
				for (Map.Entry<String, Object> entryTowns : mapTowns.entrySet()) {
					WorkCityJd wcjdTowns = new WorkCityJd();
					wcjdTowns.setCode(entryTowns.getValue().toString());
					wcjdTowns.setTowns(entryTowns.getKey());
					wcjdTowns.setParent(Long.parseLong(DistrictIdList.get(i)));
					wcjdTowns.setLevel("4");
					workCityJdMapper.insertSelective(wcjdTowns);
				}
			}
		}
	}
    /**
     * 获取京东一级地址
     *
     * @return
     * @throws InterruptedException 
     */
    public JdApiResponse<JSONObject> addressAllProvincesQuery()  {
        //  try {
        //      return addressCache.get("addressAllProvincesQuery", new Callable<JdApiResponse<JSONObject>>() {
        //        @Override
        //         public JdApiResponse<JSONObject> call() throws Exception {
        return request("biz.address.allProvinces.query", null, "biz_address_allProvinces_query_response", JSONObject.class);
        //          }
        //     });
        //   } catch (Exception e) {
        //        throw new RuntimeException();
        //    }
    }

    /**
     * 根据省份id获取城市信息
     *
     * @param id
     * @return
     * @throws InterruptedException 
     */
    public JdApiResponse<JSONObject> addressCitysByProvinceIdQuery(final int id){
        //  try {
        //    return addressCache.get("addressCitysByProvinceIdQuery" + id, new Callable<JdApiResponse<JSONObject>>() {
        //         @Override
        //        public JdApiResponse<JSONObject> call() throws Exception {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return request("biz.address.citysByProvinceId.query", jsonObject, "biz_address_citysByProvinceId_query_response", JSONObject.class);
        //      }
        //      });
        //   } catch (Exception e) {
        //       throw new RuntimeException();
        //     }

    }

    /**
     * 根据区县获取乡镇信息
     *
     * @param id
     * @return
     * @throws InterruptedException 
     */
    public JdApiResponse<JSONObject> addressTownsByCountyIdQuery(final int id){
//        try {
//            return addressCache.get("addressTownsByCountyIdQuery" + id, new Callable<JdApiResponse<JSONObject>>() {
//                @Override
//                public JdApiResponse<JSONObject> call() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return request("biz.address.townsByCountyId.query", jsonObject, "biz_address_townsByCountyId_query_response", JSONObject.class);
//                }
//            });
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
    }

    /**
     * 根据城市获取区县信息
     *
     * @param id
     * @return
     * @throws InterruptedException 
     */
    public JdApiResponse<JSONObject> addressCountysByCityIdQuery(final int id) {
//        try {
//            return addressCache.get("addressCountysByCityIdQuery" + id, new Callable<JdApiResponse<JSONObject>>() {
//                @Override
//                public JdApiResponse<JSONObject> call() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return request("biz.address.countysByCityId.query", jsonObject, "biz_address_countysByCityId_query_response", JSONObject.class);
//                }
//            });
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }

    }

    private Cache<String, List<Stock>> stockCache = CacheBuilder.newBuilder().maximumSize(1024)
            .expireAfterWrite(30, TimeUnit.SECONDS).build();

    /**
     * 批量获取库存接口（建议订单详情页、下单使用）
     *
     * @param skuNums
     * @param region
     * @return
     */
    public List<Stock> getStock(final List<SkuNum> skuNums, final Region region) {
        JdApiResponse<JSONArray> response = stockFororderBatget(skuNums, region);
        List<Stock> result = new ArrayList<>();
        if (response.isSuccess()) {
            for (Object o : response.getResult()) {
                result.add(JSONObject.toJavaObject((JSONObject) o, Stock.class));
            }
            return result;

        } else {
            LOGGER.warn("getstockerror: {}", response.toString());
        }
        return new ArrayList<>();
    }

    /**
     * 批量获取库存接口（建议订单详情页、下单使用）
     *
     * @param skuNums
     * @param region
     * @return
     */
    public JdApiResponse<JSONArray> stockFororderBatget(List<SkuNum> skuNums, final Region region) {
        if (skuNums.size() == 0) {
            throw new IllegalArgumentException("skuNumList is empty");
        }
        String area = region.getProvinceId() + "_" + region.getCityId() + "_" + region.getCountyId() + "_" + region.getTownId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuNums", skuNums);
        jsonObject.put("area", area);
        return request("biz.stock.fororder.batget", jsonObject, "biz_stock_fororder_batget_response", JSONArray.class);
    }
    /**
     * 获取商品库存接口（建议商品列表页使用）
     *biz.stock.forList.batget
     * @param skuNums
     * @param region
     * @return
     */
    public JdApiResponse<JSONArray> stockForListBatget(String sku, final Region region) {
        if (StringUtils.isEmpty(sku)) {
            throw new IllegalArgumentException("skuNums is empty");
        }
        String area = region.getProvinceId() + "_" + region.getCityId() + "_" + region.getCountyId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sku", sku);
        jsonObject.put("area", area);
        return request("biz.stock.forList.batget", jsonObject, "biz_stock_forList_batget_response", JSONArray.class);
    }
    /**
     * 消息推送，一次返回100条信息
     *
     * @param types
     * @return
     */
    public JdApiResponse<JSONArray> messageGet(int... types) {
        String join = StringUtils.join(types, ',');
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", join);
        return request("biz.message.get", jsonObject, "biz_message_get_response", JSONArray.class);
    }

    /**
     * 根据消息id，删除消息
     *
     * @param id
     * @return
     */
    public JdApiResponse<Boolean> messageDel(long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return request("biz.message.del", jsonObject, "biz_message_del_response", Boolean.class);
    }

    /**
     * @param searchCondition
     * @return
     */
    public JdApiResponse<JSONArray> search(SearchCondition searchCondition) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageIndex", searchCondition.getPageIndex());
        jsonObject.put("catId", searchCondition.getCatId());
        if (searchCondition.getKeyword() != null) {
            jsonObject.put("keyword", UrlUtils.encode(searchCondition.getKeyword()));
        }
        jsonObject.put("pageSize", searchCondition.getPageSize());
        jsonObject.put("min", searchCondition.getMin());
        jsonObject.put("max", searchCondition.getMax());
        if (searchCondition.getBrands() != null) {
            jsonObject.put("brands", UrlUtils.encode(searchCondition.getBrands()));
        }
        return request("jd.biz.search.search", jsonObject, "jd_biz_search_search_response", JSONArray.class);
    }

    /**
     * 查询分类列表信息接口
     * @param cid
     * @return
     */
    public JdApiResponse<JSONObject> getcategory(Long cid ) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cid", cid );
        return request("jd.biz.product.getcategory", jsonObject, "jd_biz_product_getcategory_response", JSONObject.class);
    }
    /**
     * 查询规格
     * @param skuId
     * @return
     */
    public JdApiResponse<JSONArray> getSimilarSku(Long skuId ) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skuId", skuId );
        return request("jd.biz.product.getSimilarSku", jsonObject, "jd_biz_product_getSimilarSku_response", JSONArray.class);
    }

}
