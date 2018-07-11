package com.apass.esp.domain.enums;

/**
 * Created by xiaohai on 2017/10/27.
 * 优惠券推广方式
 */
public enum  GrantNode {
    NODE_SFZRZTG("SFZRZTG","身份证认证通过"),
    NODE_YHKRZTG("YHKRZTG","银行卡认证通过"),
    NODE_FKCG("FKCG","放款成功");

    private String code;
    private String message;
    GrantNode(String code,String message){
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
    	return GrantNode.valueOf("NODE_"+code).getMessage();
    }
}
