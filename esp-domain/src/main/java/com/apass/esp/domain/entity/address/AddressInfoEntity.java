package com.apass.esp.domain.entity.address;

import org.apache.commons.lang3.StringUtils;

import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 地址信息
 * 
 * @description
 *
 * @author liuming
 * @version $Id: AddressInfo.java, v 0.1 2016年12月19日 下午2:12:50 liuming Exp $
 */
@MyBatisEntity
public class AddressInfoEntity {

    private Long id;

    private Long addressId;

    private Long userId;

    private String provinceCode ;

    private String province;

    private String cityCode ;

    private String city;

    private String districtCode ;

    private String district;

    private String townsCode ;

    private String towns ;

    private String address;

    private String postcode;

    private String name;

    private String telephone;

    private String isDefault;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getTowns() {
        if(StringUtils.isBlank(towns)){
            return "";
        }
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = towns;
    }

    public String getProvinceCode() {
        if(StringUtils.isBlank(provinceCode)){
            return "";            
        }
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        if(StringUtils.isBlank(cityCode)){
            return "";            
        }
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        if(StringUtils.isBlank(districtCode)){
            return "";            
        }
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getTownsCode() {
        if(StringUtils.isBlank(townsCode)){
            return "";            
        }
        return townsCode;
    }

    public void setTownsCode(String townsCode) {
        this.townsCode = townsCode;
    }

}
