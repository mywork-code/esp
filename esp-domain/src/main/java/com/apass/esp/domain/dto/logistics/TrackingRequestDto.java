package com.apass.esp.domain.dto.logistics;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trackingmore快递       创建跟踪项目entity
 * @description 
 *
 * @author liuming
 * @version $Id: Tracking.java, v 0.1 2017年2月14日 上午11:58:47 liuming Exp $
 */
public class TrackingRequestDto {

    @JsonProperty("tracking_number")
    private String trackingNum;

    @JsonProperty("carrier_code")
    private String carrierCode;
    
    @JsonProperty("order_id ")
    private String orderId;
    
    /**
     * 语言默认为简体中文
     */
    @JsonProperty("lang")
    private String lang = "cn";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
