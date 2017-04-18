package com.apass.esp.domain.dto.logistics;

import com.google.gson.annotations.SerializedName;

/**
 * 快递鸟  即时查询接口  RequestDto
 * @description 
 *
 * @author liuming
 * @version $Id: LogisticsRequestDto.java, v 0.1 2016年12月27日 下午1:58:32 liuming Exp $
 */
public class LogisticsRequestDto {
   /**
    * 订单编号
    */
    @SerializedName("OrderCode")
    private String orderCode;
    /**
     * 快递公司编码
     */
    @SerializedName("ShipperCode")
    private String shipperCode;
    /**
     * 物流单号
     */
    @SerializedName("LogisticCode")
    private String logisticCode;
    
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getShipperCode() {
        return shipperCode;
    }
    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }
    public String getLogisticCode() {
        return logisticCode;
    }
    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }
    
    
}
