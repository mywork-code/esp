package com.apass.esp.common.model;

/**
 * Created by jie.xu on 17/4/21.
 */
public class QueryParams {
  private Integer startRecordIndex;
  private Integer pageSize;
  private Integer page;

  private String order = "id desc";

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Integer getStartRecordIndex() {
    return startRecordIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Integer getPage() {
    return page;
  }

  public void setPageParams(Integer pageSize, Integer page) {
    this.pageSize = pageSize;
    this.page = page;
    if (pageSize != null) {
      this.startRecordIndex = (page - 1) * pageSize;
    }
  }

}
