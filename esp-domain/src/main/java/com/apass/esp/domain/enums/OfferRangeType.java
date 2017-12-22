package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券类型
 */
public enum OfferRangeType {
    RANGE_ZDPP("1","品牌"),
    range_ZDPL("2","品类"),
    range_ZDSP("3","指定商品");

    private String code;
    private String message;
    OfferRangeType(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static String getMessage(String code){
    	OfferRangeType[] types = OfferRangeType.values();
    	for (OfferRangeType type : types) {
			if(StringUtils.equals(code, type.getCode())){
				return type.getMessage();
			}
		}
    	return "";
    }
}
