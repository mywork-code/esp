package com.apass.esp.domain.entity;
import java.util.Date;
import com.apass.esp.common.model.CreatedUser;
public class LimitBuyAct extends CreatedUser{
    private Long id;
    private Date startDate;
    private Date endDate;
    private String status;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    private Byte startTime;
    public Byte getStartTime() {
        return startTime;
    }
    public void setStartTime(Byte startTime) {
        this.startTime = startTime;
    }
}