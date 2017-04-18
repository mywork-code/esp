package com.apass.esp.domain.dto.aftersale;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RefundImageDto {

    @SerializedName("userId")
    private String userId;
    
    @SerializedName("orderId")
    private String orderId;
    
    @SerializedName("returnImage")
    private List<String> returnImage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getReturnImage() {
        return returnImage;
    }

    public void setReturnImage(List<String> returnImage) {
        this.returnImage = returnImage;
    }

}
