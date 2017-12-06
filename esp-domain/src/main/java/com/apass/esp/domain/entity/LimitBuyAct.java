package com.apass.esp.domain.entity;
import java.util.Date;
import com.apass.esp.common.model.QueryParams;
public class LimitBuyAct extends QueryParams{
    private Long id;
    private Date startDate;
    private Date endDate;
    private String status;
    private String createUser;
    private String updateUser;
    private Date createdTime;
    private Date updatedTime;
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
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    private Byte startTime;
    public Byte getStartTime() {
        return startTime;
    }
    public void setStartTime(Byte startTime) {
        this.startTime = startTime;
    }
}