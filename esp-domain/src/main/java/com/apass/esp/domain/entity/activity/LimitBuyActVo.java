package com.apass.esp.domain.entity.activity;
import java.util.Date;
import com.apass.esp.domain.entity.LimitBuyAct;
public class LimitBuyActVo extends LimitBuyAct{
    private String startDay;
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
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
}