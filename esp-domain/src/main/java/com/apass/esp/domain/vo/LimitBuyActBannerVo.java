package com.apass.esp.domain.vo;

import java.util.Date;

/**
 * Created by xiaohai on 2017/12/7.
 */
public class LimitBuyActBannerVo {
    /**
     * 限时购图片url
     */
    private String imgurl;

    /**
     *当前时间场次:hh:ss
     */
    private String startDate;

    /**
     * 结束时间
     * @return
     */
    private String endDate;;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
