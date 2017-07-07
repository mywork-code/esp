package com.apass.esp.third.party.jd.entity.order;

import com.apass.esp.third.party.jd.entity.person.AddressInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Order implements Serializable {


    private long id;
    private int personId;
    private String userName;
    private String orderNo;
    private long jdOrderId;
    private List<OrderSku> orderSkus;
    private String remark;
    private BigDecimal freight = BigDecimal.ZERO;
    private BigDecimal jdPrice;
    private BigDecimal orderPrice;
    private BigDecimal showPrice;//页面显示价格（jd价格加邮费）
    /**
     * 地址镜像 start
     * <-----------
     */
    private String receiver;//收货人
    private String mobile;
    private String phone;
    private String email;

    //京东4级地址
    private int provinceId;
    private int cityId;
    private int countyId;
    private int townId;
    private String address;//详细地址
    private Date timeCreated;
    private Date pay_time;
    private Date cancel_time;
    private int auto_cancel;
    private String mallId;
    private int loginEntry;

    /**
     * 地址镜像 end
     * ----------->
     */

    public void setWithAddressInfo(AddressInfo addressInfo) {
        this.setReceiver(addressInfo.getReceiver());
        this.setMobile(addressInfo.getMobile());
        this.setPhone(addressInfo.getPhone());
        this.setEmail(addressInfo.getEmail());
        this.setProvinceId(addressInfo.getProvinceId());
        this.setCityId(addressInfo.getCityId());
        this.setCountyId(addressInfo.getCountyId());
        this.setTownId(addressInfo.getTownId());
        this.setAddress(addressInfo.getAddress());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(long jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public List<OrderSku> getOrderSkus() {
        return orderSkus;
    }

    public void setOrderSkus(List<OrderSku> orderSkus) {
        this.orderSkus = orderSkus;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Date getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Date cancel_time) {
        this.cancel_time = cancel_time;
    }

    public int getAuto_cancel() {
        return auto_cancel;
    }

    public void setAuto_cancel(int auto_cancel) {
        this.auto_cancel = auto_cancel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public int getLoginEntry() {
        return loginEntry;
    }

    public void setLoginEntry(int loginEntry) {
        this.loginEntry = loginEntry;
    }
}
