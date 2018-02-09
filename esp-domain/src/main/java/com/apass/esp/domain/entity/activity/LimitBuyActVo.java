package com.apass.esp.domain.entity.activity;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
/**
 * 限时购活动  后台查询专用  封装开始日期  和 商品列表
 * @author Administrator
 *
 */
public class LimitBuyActVo extends LimitBuyAct{
    /**
     * 冗余活动ID ==活动ID
     */
    private Long limitBuyActId;
    private String startDay;
    private List<LimitGoodsSku> list;
    private Date startDayBefore;
    private Date startDayAfter;
    private MultipartFile upLoadGoodsFile;
    public Long getLimitBuyActId() {
        return limitBuyActId;
    }
    public void setLimitBuyActId(Long limitBuyActId) {
        this.limitBuyActId = limitBuyActId;
    }
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    public List<LimitGoodsSku> getList() {
        return list;
    }
    public void setList(List<LimitGoodsSku> list) {
        this.list = list;
    }
    public Date getStartDayBefore() {
        return startDayBefore;
    }
    public void setStartDayBefore(Date startDayBefore) {
        this.startDayBefore = startDayBefore;
    }
    public Date getStartDayAfter() {
        return startDayAfter;
    }
    public void setStartDayAfter(Date startDayAfter) {
        this.startDayAfter = startDayAfter;
    }
    public MultipartFile getUpLoadGoodsFile() {
        return upLoadGoodsFile;
    }
    public void setUpLoadGoodsFile(MultipartFile upLoadGoodsFile) {
        this.upLoadGoodsFile = upLoadGoodsFile;
    }
}