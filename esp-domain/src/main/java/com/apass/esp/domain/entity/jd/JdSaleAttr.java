package com.apass.esp.domain.entity.jd;

import java.util.List;

public class JdSaleAttr {

    private String imagePath;

    private String saleValue;
    
    private String saleValueId;

    private List<String> skuIds;

    private String skuIdStr;


    public String getSkuIdStr() {
        return skuIdStr;
    }

    public void setSkuIdStr(String skuIdStr) {
        this.skuIdStr = skuIdStr;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(String saleValue) {
        this.saleValue = saleValue;
    }

    public List<String> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<String> skuIds) {
        this.skuIds = skuIds;
    }

	public String getSaleValueId() {
		return saleValueId;
	}

	public void setSaleValueId(String saleValueId) {
		this.saleValueId = saleValueId;
	}
    
}
