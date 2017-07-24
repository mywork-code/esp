package com.apass.esp.domain.dto.refund;

import java.util.Date;

/**
 * 售后进度查询dto
 * @description 
 *
 * @author liuchao01
 * @version $Id: ServiceProcessDto.java, v 0.1 2017年1月3日 下午2:59:15 liuchao01 Exp ${0xD}
 */
public class ServiceProcessDto {

    /** 退货信息表主键id */
    private Long refundId;
    
    /** 退换货类型  0退货、1换货  */
    private String refundType;
    
    /** 是否允许输入退换货物流地址  默认否 */
    private String isAllowed = "0";

    /** 售后处理进度(状态) */
    private String status;

    /** 客户发货物流厂商 */
    private String slogisticsName;

    /** 客户发货物流单号 */
    private String slogisticsNo;
    
    /** 换货商户发货物流厂商 */
    private String rlogisticsName;
    
    /** 换货商户发货物流单号 */
    private String rlogisticsNo;
    
    /** 商户的退货地址*/
    private String merchantInfoReturnAddress;
    
    /** 商户的收货人名称*/
    private  String merchantReturnName;
    
    /** 商户的收货人手机号码*/
    private String merchantReturnPhone;

    /** 处理进度-处理时间 */
    /** RS01(申请退/换货)-处理时间 */
    private Date rs01Time;

    /** RS02(提交退货物流单号)-处理时间 */
    private Date rs02Time;

    /** RS03(商家确认收货)-处理时间 */
    private Date rs03Time;

    /** RS04(等待退款/商家重新发货)-处理时间 */
    private Date rs04Time;

    /** RS05(售后完成)-处理时间 */
    private Date rs05Time;
    
    /** RS06(售后失败)-处理时间 */
    private Date rs06Time;

    private String memo;

    private String source ;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(String isAllowed) {
        this.isAllowed = isAllowed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlogisticsName() {
        return slogisticsName;
    }

    public void setSlogisticsName(String slogisticsName) {
        this.slogisticsName = slogisticsName;
    }

    public String getSlogisticsNo() {
        return slogisticsNo;
    }

    public void setSlogisticsNo(String slogisticsNo) {
        this.slogisticsNo = slogisticsNo;
    }

    public String getRlogisticsName() {
        return rlogisticsName;
    }

    public void setRlogisticsName(String rlogisticsName) {
        this.rlogisticsName = rlogisticsName;
    }

    public String getRlogisticsNo() {
        return rlogisticsNo;
    }

    public void setRlogisticsNo(String rlogisticsNo) {
        this.rlogisticsNo = rlogisticsNo;
    }
    
    public String getMerchantInfoReturnAddress() {
        return merchantInfoReturnAddress;
    }

    public void setMerchantInfoReturnAddress(String merchantInfoReturnAddress) {
        this.merchantInfoReturnAddress = merchantInfoReturnAddress;
    }

    public String getMerchantReturnName() {
        return merchantReturnName;
    }

    public void setMerchantReturnName(String merchantReturnName) {
        this.merchantReturnName = merchantReturnName;
    }

    public String getMerchantReturnPhone() {
        return merchantReturnPhone;
    }

    public void setMerchantReturnPhone(String merchantReturnPhone) {
        this.merchantReturnPhone = merchantReturnPhone;
    }

    public Date getRs01Time() {
        return rs01Time;
    }

    public void setRs01Time(Date rs01Time) {
        this.rs01Time = rs01Time;
    }

    public Date getRs02Time() {
        return rs02Time;
    }

    public void setRs02Time(Date rs02Time) {
        this.rs02Time = rs02Time;
    }

    public Date getRs03Time() {
        return rs03Time;
    }

    public void setRs03Time(Date rs03Time) {
        this.rs03Time = rs03Time;
    }

    public Date getRs04Time() {
        return rs04Time;
    }

    public void setRs04Time(Date rs04Time) {
        this.rs04Time = rs04Time;
    }

    public Date getRs05Time() {
        return rs05Time;
    }

    public void setRs05Time(Date rs05Time) {
        this.rs05Time = rs05Time;
    }

    public Date getRs06Time() {
        return rs06Time;
    }

    public void setRs06Time(Date rs06Time) {
        this.rs06Time = rs06Time;
    }

}
