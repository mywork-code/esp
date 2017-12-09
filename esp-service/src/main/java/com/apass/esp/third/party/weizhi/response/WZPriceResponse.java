package com.apass.esp.third.party.weizhi.response;

import java.io.Serializable;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年11月17日 下午3:42:51 
 * @description
 */
public class WZPriceResponse implements Serializable {
		
	private static final long serialVersionUID = 1L;

	private String skuId;
	
	private String WzPrice;
	
	private String JDPrice;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getWzPrice() {
		return WzPrice;
	}

	public void setWzPrice(String wzPrice) {
		WzPrice = wzPrice;
	}

	public String getJDPrice() {
		return JDPrice;
	}

	public void setJDPrice(String jDPrice) {
		JDPrice = jDPrice;
	}
}
