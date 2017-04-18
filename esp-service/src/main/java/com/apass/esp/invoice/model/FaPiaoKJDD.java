package com.apass.esp.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by jie.xu on 17/3/29.
 */
@XStreamAlias("FPKJXX_DDXX")
public class FaPiaoKJDD {
  @XStreamAlias("DDH")
  private String ddh;
  @XStreamAlias("THDH")
  private String thdh;
  @XStreamAlias("DDDATE")
  private String dddate;

  public String getDdh() {
    return ddh;
  }

  public void setDdh(String ddh) {
    this.ddh = ddh;
  }

  public String getThdh() {
    return thdh;
  }

  public void setThdh(String thdh) {
    this.thdh = thdh;
  }

  public String getDddate() {
    return dddate;
  }

  public void setDddate(String dddate) {
    this.dddate = dddate;
  }
}
