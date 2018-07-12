package com.apass.esp.domain.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 节点发放
 */
public enum  GrantNode {
    NODE_SFZRZTG("SFZRZTG","身份证认证通过","IDENTITY"),
    NODE_YHKRZTG("YHKRZTG","银行卡认证通过","CARD_AUTH"),
    NODE_FKCG("FKCG","放款成功","LOAN_PROVIDE");

    private String code;
    private String message;
    private String mapCode;
    GrantNode(String code,String message,String mapCode){
        this.code = code;
        this.message = message;
        this.mapCode = mapCode;
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
    
    public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	public static String getMessage(String code){
    	return GrantNode.valueOf("NODE_"+code).getMessage();
    }
	
	public static String getCodeByMapCode(String mapCode){
		GrantNode[] values = GrantNode.values();
		for (GrantNode node : values) {
			if(StringUtils.equals(node.getMapCode(), mapCode)){
				return node.getCode();
			}
		}
		return "";
    }
}
