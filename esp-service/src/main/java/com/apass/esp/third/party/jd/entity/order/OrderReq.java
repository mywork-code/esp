package com.apass.esp.third.party.jd.entity.order;

import com.apass.esp.third.party.jd.entity.person.AddressInfo;

import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class OrderReq {
    private String orderNo;
    private int personId;
    private List<SkuNum> skuNumList;
    private AddressInfo addressInfo;
    private String remark;
    private List<PriceSnap> orderPriceSnap;
    public List<SkuNum> getSkuNumList() {
        return skuNumList;
    }

    public void setSkuNumList(List<SkuNum> skuNumList) {
        this.skuNumList = skuNumList;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PriceSnap> getOrderPriceSnap() {
        return orderPriceSnap;
    }

    public void setOrderPriceSnap(List<PriceSnap> orderPriceSnap) {
        this.orderPriceSnap = orderPriceSnap;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
