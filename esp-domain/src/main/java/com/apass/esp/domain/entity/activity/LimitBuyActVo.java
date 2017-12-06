package com.apass.esp.domain.entity.activity;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
public class LimitBuyActVo extends LimitBuyAct{
    private String startDay;
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    private List<LimitGoodsSku> list;
    public List<LimitGoodsSku> getList() {
        return list;
    }
    public void setList(List<LimitGoodsSku> list) {
        this.list = list;
    }
    private Date startDayBefore;
    private Date startDayAfter;
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
    private MultipartFile upLoadGoodsFile;
    public MultipartFile getUpLoadGoodsFile() {
        return upLoadGoodsFile;
    }
    public void setUpLoadGoodsFile(MultipartFile upLoadGoodsFile) {
        this.upLoadGoodsFile = upLoadGoodsFile;
    }
}