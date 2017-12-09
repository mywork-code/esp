package com.apass.esp.third.party.weizhi.entity.aftersale;

import com.apass.esp.third.party.weizhi.client.WeiZhiResponse;

/**
 * Created by xiaohai on 2017/11/24.
 */
public class WeiZhiAfterSaleResponse{
    /**
     * 状态码:0成功，其它：导演
     */
    private Integer result;
    /**
     * 错误描述
     */
    private String detail;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
