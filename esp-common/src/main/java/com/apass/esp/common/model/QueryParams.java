package com.apass.esp.common.model;

/**
 * Created by jie.xu on 17/4/21.
 */
public class QueryParams {
  private Integer startRecordIndex;
  private Integer rows;
  private Integer page;

  private String order = "id desc";

  public QueryParams(){
    if (rows != null) {
      this.startRecordIndex = (page - 1) * rows;
    }
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public void setStartRecordIndex(Integer startRecordIndex) {
    this.startRecordIndex = startRecordIndex;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getStartRecordIndex() {
    if (rows != null) {
      this.startRecordIndex = (page - 1) * rows;
    }
    return startRecordIndex;
  }

  public Integer getRows() {
    return rows;
  }

  public Integer getPage() {
    return page;
  }

  public void setPageParams(Integer rows, Integer page) {
    this.rows = rows;
    this.page = page;
    if (rows != null) {
      this.startRecordIndex = (page - 1) * rows;
    }
  }

}
