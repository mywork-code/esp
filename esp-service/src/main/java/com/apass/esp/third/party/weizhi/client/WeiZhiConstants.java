package com.apass.esp.third.party.weizhi.client;

import org.apache.commons.codec.digest.DigestUtils;

public class WeiZhiConstants {
	//tokenUrl
    public static final String TOKEN_URL = "http://180.168.49.94:65530/jdapi/accessToken";
	//
    public static final String GRANT_TYPE = "access_token";
	//对接账号
    public static final String CLIENT_ID = "200034525";
    //用户名
    public static final String USER_NAME = "zydc";
    //密码
    public static final String PASSWORD = "132654";
    //
    public static final String CLIENT_SECRET = "kLKYak8a9xHsUW2TBgdf";
    //
    public static final String WEIZHI_TOKEN = "WEIZHI_TOKEN";
    //获取sign
    public String getSign(String timestamp){
    	String signString=CLIENT_SECRET+USER_NAME+PASSWORD+timestamp+CLIENT_ID+GRANT_TYPE+CLIENT_SECRET;
    	return DigestUtils.md5Hex(signString).toLowerCase();
    }

}
