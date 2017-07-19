package com.apass.esp.third.party.jd.entity.base;

import java.math.BigDecimal;
import java.util.Date;

public class JdGoods {
    private Integer id;

    private Long skuId;

    private BigDecimal price;

    private BigDecimal jdPrice;

    private String brandName;

    private String name;

    private String imagePath;

    private BigDecimal weight;

    private String upc;

    private String productArea;

    private String saleUnit;

    private String wareQd;

    private Boolean state;

    private Integer firstCategory;

    private Integer secondCategory;

    private Integer thirdCategory;

    private String similarSkus;

    private Date createDate;

    private Date updateDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getWareQd() {
        return wareQd;
    }

    public void setWareQd(String wareQd) {
        this.wareQd = wareQd;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(Integer firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Integer getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(Integer secondCategory) {
        this.secondCategory = secondCategory;
    }

    public Integer getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(Integer thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

    public String getSimilarSkus() {
        return similarSkus;
    }

    public void setSimilarSkus(String similarSkus) {
        this.similarSkus = similarSkus;
    }
}