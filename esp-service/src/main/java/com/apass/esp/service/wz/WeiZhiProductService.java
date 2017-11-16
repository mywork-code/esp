package com.apass.esp.service.wz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.jd.JdProductState;
import com.apass.esp.third.party.jd.entity.product.Product;
import com.apass.esp.third.party.weizhi.client.WeiZhiProductApiClient;

@Service
public class WeiZhiProductService {
	@Autowired
	private WeiZhiProductApiClient weiZhiProductApiClient;
	
	/**
	 * 获取微知商品详情信息
	 * @return
	 * @throws Exception
	 */
	public Product getWeiZhiProductDetail(String sku) throws Exception {
		return weiZhiProductApiClient.getWeiZhiProductDetail(sku);
	}

	/**
	 * 获取商品上下架状态接口(单个商品) true:上架；false:下架
	 * @throws Exception 
	 */
	public Boolean getWeiZhiProductSkuState(String sku) throws Exception{
		if (StringUtils.isNotEmpty(sku)) {
			List<JdProductState> wzProductState = weiZhiProductApiClient.getWeiZhiProductSkuState(sku);
			if (null != wzProductState && wzProductState.size() == 1) {
				Integer state = wzProductState.get(0).getState();
				if (state == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取商品上下架状态接口(多个商品，最多20个)
	 */
	public List<JdProductState> getWeiZhiProductSkuStateList(List<String> skus) throws Exception {
		List<JdProductState> wzProductState = new ArrayList<>();
		if (null != skus && skus.size() > 0) {
			String skuString = skus.get(0);
			int len = 0;
			if (skus.size() <= 20) {
				len = skus.size();
			} else {
				len = 20;
			}
			for (int i = 1; i < len; i++) {
				skuString = skuString + "," + skus.get(i);
			}
			wzProductState = weiZhiProductApiClient.getWeiZhiProductSkuState(skuString);
		}
		return wzProductState;
	}
	
	
}
