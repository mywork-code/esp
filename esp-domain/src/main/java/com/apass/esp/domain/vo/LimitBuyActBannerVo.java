package com.apass.esp.domain.vo;

/**
 * Created by xiaohai on 2017/12/7.
 */
public class LimitBuyActBannerVo {
    /**
     * 限时购图片url
     */
    private String imgurl;

    /**
     *当前时间场次
     */
    private String time;

    /**
     * 距结束还有多久的时间间隔
     * @return
     */
    private long millisecond;

    public long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(long millisecond) {
        this.millisecond = millisecond;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
