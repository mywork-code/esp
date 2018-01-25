package com.apass.esp.domain.entity;

import java.util.Date;

public class DataAppuserRetention {
    private Long id;

    private Date createdTime;

    private Date updatedTime;

    private String isDelete;

    private String txnId;

    private Byte platformids;

    private String day1retention;

    private String day3retention;

    private String day7retention;

    private String day14retention;

    private String day30retention;

    private String dauday1retention;

    private String dauday3retention;

    private String dauday7retention;

    private String dauday14retention;

    private String dauday30retention;

    private String day7churnuser;

    private String day14churnuser;

    private String day7backuser;

    private String day14backuser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public Byte getPlatformids() {
        return platformids;
    }

    public void setPlatformids(Byte platformids) {
        this.platformids = platformids;
    }

    public String getDay1retention() {
        return day1retention;
    }

    public void setDay1retention(String day1retention) {
        this.day1retention = day1retention;
    }

    public String getDay3retention() {
        return day3retention;
    }

    public void setDay3retention(String day3retention) {
        this.day3retention = day3retention;
    }

    public String getDay7retention() {
        return day7retention;
    }

    public void setDay7retention(String day7retention) {
        this.day7retention = day7retention;
    }

    public String getDay14retention() {
        return day14retention;
    }

    public void setDay14retention(String day14retention) {
        this.day14retention = day14retention;
    }

    public String getDay30retention() {
        return day30retention;
    }

    public void setDay30retention(String day30retention) {
        this.day30retention = day30retention;
    }

    public String getDauday1retention() {
        return dauday1retention;
    }

    public void setDauday1retention(String dauday1retention) {
        this.dauday1retention = dauday1retention;
    }

    public String getDauday3retention() {
        return dauday3retention;
    }

    public void setDauday3retention(String dauday3retention) {
        this.dauday3retention = dauday3retention;
    }

    public String getDauday7retention() {
        return dauday7retention;
    }

    public void setDauday7retention(String dauday7retention) {
        this.dauday7retention = dauday7retention;
    }

    public String getDauday14retention() {
        return dauday14retention;
    }

    public void setDauday14retention(String dauday14retention) {
        this.dauday14retention = dauday14retention;
    }

    public String getDauday30retention() {
        return dauday30retention;
    }

    public void setDauday30retention(String dauday30retention) {
        this.dauday30retention = dauday30retention;
    }

    public String getDay7churnuser() {
        return day7churnuser;
    }

    public void setDay7churnuser(String day7churnuser) {
        this.day7churnuser = day7churnuser;
    }

    public String getDay14churnuser() {
        return day14churnuser;
    }

    public void setDay14churnuser(String day14churnuser) {
        this.day14churnuser = day14churnuser;
    }

    public String getDay7backuser() {
        return day7backuser;
    }

    public void setDay7backuser(String day7backuser) {
        this.day7backuser = day7backuser;
    }

    public String getDay14backuser() {
        return day14backuser;
    }

    public void setDay14backuser(String day14backuser) {
        this.day14backuser = day14backuser;
    }
}