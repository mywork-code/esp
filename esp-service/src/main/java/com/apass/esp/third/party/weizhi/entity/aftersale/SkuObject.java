package com.apass.esp.third.party.weizhi.entity.aftersale;

/**
 * Created by xiaohai on 2017/11/24.
 */
public class SkuObject {
    private String skuId;
    private ResultObject result;

    public ResultObject getResult() {
        return result;
    }

    public void setResult(ResultObject result) {
        this.result = result;
    }

    public String getSkuId() {

        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
