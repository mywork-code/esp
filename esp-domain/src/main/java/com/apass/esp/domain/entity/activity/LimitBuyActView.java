package com.apass.esp.domain.entity.activity;
import java.util.List;
import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.LimitGoodsSku;
public class LimitBuyActView extends LimitBuyAct{
    private String startDay;
    private String startTime;
    public String getStartDay() {
        return startDay;
    }
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    private List<LimitGoodsSku> list;
    public List<LimitGoodsSku> getList() {
        return list;
    }
    public void setList(List<LimitGoodsSku> list) {
        this.list = list;
    }
}