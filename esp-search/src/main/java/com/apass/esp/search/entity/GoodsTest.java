package com.apass.esp.search.entity;

/**
 * Created by Administrator on 2017/5/23.
 */
public class GoodsTest implements IdAble {
    private String goodsName;
    private String goodsFixName;
    private Integer id;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsFixName() {
        return goodsFixName;
    }

    public void setGoodsFixName(String goodsFixName) {
        this.goodsFixName = goodsFixName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
