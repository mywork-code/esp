package com.apass.esp.invoice.model;

/**
 * Created by jie.xu on 17/3/29.
 */
public class Data {
  private DataDescription dataDescription;
  private Object content;

  public DataDescription getDataDescription() {
    return dataDescription;
  }

  public void setDataDescription(DataDescription dataDescription) {
    this.dataDescription = dataDescription;
  }

  public Object getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = content;
  }
}
