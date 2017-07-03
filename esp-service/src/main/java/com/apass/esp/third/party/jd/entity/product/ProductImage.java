package com.apass.esp.third.party.jd.entity.product;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.sql.Date;
/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ProductImage implements Serializable {
    private long id;
    private long skuId;
    private long imageId;

    private String imagePath;
    private int isPrimary;
    private int orderSort;
    private int type;
    private String features;

    @JSONField(serialize = false, deserialize = false)
    private int position;
    private int isInUse;
    private Date timeCreated;
    private Date timeModified;

    public static ProductImage fromOriginalJson(JSONObject jsonObject) {
        ProductImage productImage = new ProductImage();
        productImage.setSkuId(jsonObject.getLongValue("skuId"));
        productImage.setImageId(jsonObject.getLongValue("id"));
        productImage.setImagePath(jsonObject.getString("path"));
        productImage.setIsPrimary(jsonObject.getIntValue("isPrimary"));
        productImage.setOrderSort(jsonObject.getIntValue("orderSort"));
        productImage.setType(jsonObject.getIntValue("type"));
        productImage.setFeatures(jsonObject.getString("features"));
        productImage.setPosition(jsonObject.getIntValue("position"));
        productImage.setIsInUse(jsonObject.getIntValue("yn"));
        return productImage;
    }


    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(int orderSort) {
        this.orderSort = orderSort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIsInUse() {
        return isInUse;
    }

    public void setIsInUse(int isInUse) {
        this.isInUse = isInUse;
    }


}
