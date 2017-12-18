package com.apass.esp.domain.entity.activity;
import com.apass.esp.domain.entity.LimitGoodsSku;
/**
 * 限时购活动商品  前台展示专用  冗余商品基本信息表相关字段    附属于LimitBuyActTimeLine活动时间条
 * @author Administrator
 *
 */
public class LimitGoodsSkuInfo extends LimitGoodsSku{
    //冗余商品基本信息表字段
    private String goodsTitle;
    private String goodsName;
    private String goodsUrl;
    //冗余按钮状态 按钮名称
    private String buttonStatus;//按钮状态  1《可用》 0《置灰》
    private String buttonDesc;//按钮名称
    public String getGoodsTitle() {
        return goodsTitle;
    }
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsUrl() {
        return goodsUrl;
    }
    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }
    public String getButtonStatus() {
        return buttonStatus;
    }
    public void setButtonStatus(String buttonStatus) {
        this.buttonStatus = buttonStatus;
    }
    public String getButtonDesc() {
        return buttonDesc;
    }
    public void setButtonDesc(String buttonDesc) {
        this.buttonDesc = buttonDesc;
    }
}