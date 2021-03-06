package com.apass.esp.domain.entity;

import com.apass.esp.common.model.QueryParams;

import java.util.Date;

public class MessageListener extends QueryParams {
  private Long id;

  private String type;

  private String skuid;

  private String orderid;

  private String status;

  private String errorMassage;

  private String result;

  private Date createdTime;

  private String createDateString;

  private Date updatedTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSkuid() {
    return skuid;
  }

  public void setSkuid(String skuid) {
    this.skuid = skuid;
  }

  public String getOrderid() {
    return orderid;
  }

  public void setOrderid(String orderid) {
    this.orderid = orderid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getErrorMassage() {
    return errorMassage;
  }

  public void setErrorMassage(String errorMassage) {
    this.errorMassage = errorMassage;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
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

  public String getCreateDateString() {
    return createDateString;
  }

  public void setCreateDateString(String createDateString) {
    this.createDateString = createDateString;
  }

}
