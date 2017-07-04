package com.apass.esp.third.party.jd.entity.aftersale;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class AsReturnwareDto {
    private Integer returnwareType;//返件方式
    private Integer returnwareProvince;//返件省
    private Integer returnwareCity;//返件市
    private Integer returnwareCounty;//返件县
    private Integer returnwareVillage;//返件乡镇
    private String returnwareAddress;//返件街道地址
    public Integer getReturnwareType() {
        return returnwareType;
    }
    public void setReturnwareType(Integer returnwareType) {
        this.returnwareType = returnwareType;
    }
    public Integer getReturnwareProvince() {
        return returnwareProvince;
    }
    public void setReturnwareProvince(Integer returnwareProvince) {
        this.returnwareProvince = returnwareProvince;
    }
    public Integer getReturnwareCity() {
        return returnwareCity;
    }
    public void setReturnwareCity(Integer returnwareCity) {
        this.returnwareCity = returnwareCity;
    }
    public Integer getReturnwareCounty() {
        return returnwareCounty;
    }
    public void setReturnwareCounty(Integer returnwareCounty) {
        this.returnwareCounty = returnwareCounty;
    }
    public Integer getReturnwareVillage() {
        return returnwareVillage;
    }
    public void setReturnwareVillage(Integer returnwareVillage) {
        this.returnwareVillage = returnwareVillage;
    }
    public String getReturnwareAddress() {
        return returnwareAddress;
    }
    public void setReturnwareAddress(String returnwareAddress) {
        this.returnwareAddress = returnwareAddress;
    }
}
