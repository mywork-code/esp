package com.apass.esp.domain.enums;

/**
 * @description  商品类型枚举，index表示首页，sift表示精选
 *
 * @author dell
 * @version $Id: BannerType.java, v 0.1 2017年1月22日 上午10:34:41 dell Exp $
 */
public enum BannerType {

                        BANNER_INDEX("index", "首页"),

                        BANNER_SIFT("sift", "精选");

    private String identify;

    private String message;

    private BannerType(String identify, String message) {
        this.identify = identify;
        this.message = message;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
