package com.apass.esp.domain.dto.logistics;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @description 快递鸟  即时查询接口  ResponseDto
 *
 * @author liuming
 * @version $Id: LogisticsResponseDto.java, v 0.1 2016年12月27日 下午1:56:48 liuming Exp $
 */
public class LogisticsResponseDto {
    /**
     * 用户ID
     */
    @SerializedName("EBusinessID")
    private String      eBusinessID;
    /**
     * 轨迹列表
     */
    @SerializedName("Traces")
    private List<Trace> traces;
    /**
     * 订单编号
     */
    @SerializedName("OrderCode")
    private String      orderCode;
    /**
     * 快递公司编码
     */
    @SerializedName("ShipperCode")
    private String      shipperCode;
    /**
     * 物流运单号
     */
    @SerializedName("LogisticCode")
    private String      logisticCode;
    /**
     * 成功与否
     */
    @SerializedName("Success")
    private boolean     success;
    /**
     * 失败原因
     */
    @SerializedName("Reason")
    private String      reason;
    /**
     * 物流状态：pending:新增包裹正在查询中....详见TrackingmoreStatus类
     */
    @SerializedName("State")
    private String      state;

    public String geteBusinessID() {
        return eBusinessID;
    }

    public void seteBusinessID(String eBusinessID) {
        this.eBusinessID = eBusinessID;
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
