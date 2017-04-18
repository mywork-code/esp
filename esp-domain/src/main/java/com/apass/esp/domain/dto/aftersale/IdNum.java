package com.apass.esp.domain.dto.aftersale;

import com.google.gson.annotations.SerializedName;

public class IdNum {

    /** 商品id **/
    @SerializedName("id")
    private String id;
    
    /** 商品数量 **/
    @SerializedName("num")
    private String num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    
}
