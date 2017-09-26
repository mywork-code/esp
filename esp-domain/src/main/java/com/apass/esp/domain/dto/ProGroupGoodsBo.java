package com.apass.esp.domain.dto;

import java.math.BigDecimal;

/**
 * Created by jie.xu on 17/9/26.
 */
public class ProGroupGoodsBo {
  private Long goodsId;
  private Long activityId;
  private BigDecimal activityPrice;
  private boolean isValidActivity;//是否是有效的活动

  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public BigDecimal getActivityPrice() {
    return activityPrice;
  }

  public void setActivityPrice(BigDecimal activityPrice) {
    this.activityPrice = activityPrice;
  }

  public boolean isValidActivity() {
    return isValidActivity;
  }

  public void setValidActivity(boolean validActivity) {
    isValidActivity = validActivity;
  }
}
