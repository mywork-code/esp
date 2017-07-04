package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class Region implements Serializable {
    private int id;
    private int provinceId;
    private String province;
    private int cityId;
    private String city;
    private int countyId;
    private String county;
    private int townId;
    private String town;

    public Region() {
    }

    public Region(int provinceId, int cityId, int countyId, int townId) {
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.townId = townId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;

        Region region = (Region) o;

        if (provinceId != region.provinceId) return false;
        if (cityId != region.cityId) return false;
        if (countyId != region.countyId) return false;
        return townId == region.townId;

    }

    @Override
    public int hashCode() {
        int result = provinceId;
        result = 31 * result + cityId;
        result = 31 * result + countyId;
        result = 31 * result + townId;
        return result;
    }

}
