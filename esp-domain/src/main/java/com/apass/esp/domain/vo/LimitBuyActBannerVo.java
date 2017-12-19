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
     *当前时间场次:字符串
     */
    private Date startDate;

    /**
     * 结束时间
     * @return
     */
    private Date endDate;;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
