package com.apass.esp.domain.enums;

import org.apache.commons.codec.binary.StringUtils;

/**
 * 
 * @description 商品状态枚举
 * 
 * @author chenbo
 * @version $Id: GoodStatus.java, v 0.1 2016年12月19日 下午3:15:10 chenbo Exp $
 */
public enum GoodStatus {
    
    GOOD_NEW("G00","待上架"),
    
    GOOD_NOCHECK("G01","待审核"),
  
    GOOD_UP("G02","已上架"),
    
    GOOD_DOWN("G03","已下架"),

    GOOD_BBEN("G04","待审核");//保本率审核中
    
    private String code;
    
    private String message;
    
    private GoodStatus(String code ,String message){
        this.code=code;
        this.message=message;
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
    
    public static String getMessageByCode(String code){
    	GoodStatus[] status = GoodStatus.values();
    	for (GoodStatus goods : status) {
			if(StringUtils.equals(goods.getCode(), code)){
				return goods.getMessage();
			}
		}
    	return "";
    }
}
