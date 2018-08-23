package com.apass.esp.domain.vo.zhongyuan;

import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

/**
 * Created by DELL on 2018/8/23.
 */
public class ZYCompanyCityAwardsVo {
    private String city;
    private String award;
    private String detail;

    public ZYCompanyCityAwardsVo(String city,String award,String detail){
        this.city = city;
        this.award = award;
        this.detail = detail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
