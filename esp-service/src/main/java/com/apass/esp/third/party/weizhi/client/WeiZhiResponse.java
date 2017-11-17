package com.apass.esp.third.party.weizhi.client;

/**
 * 微知返回值
 * Created by jie.xu on 2017/11/14.
 */
public class WeiZhiResponse<T> {

    private Integer result;//错误码
    private String detail;//错误描述
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean successResp(){
        return result == 0;
    }
}
