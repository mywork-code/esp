package com.apass.esp.domain.enums;

/**
 * 
 * @description 商品状态枚举
 * 
 * @author chenbo
 * @version $Id: GoodStatus.java, v 0.1 2016年12月19日 下午3:15:10 chenbo Exp $
 */
public enum AddressDefaultStatus {
    
    NOTDEFAULT("0","非默认地址"),
    
    DEFAULT("1","默认地址");
    
    private String code;
    
    private String message;
    
    private AddressDefaultStatus(String code ,String message){
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
}
