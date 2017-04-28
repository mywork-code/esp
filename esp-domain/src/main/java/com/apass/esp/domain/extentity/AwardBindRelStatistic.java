package com.apass.esp.domain.extentity;

import java.util.Date;

/**
 * Created by jie.xu on 17/4/27.
 */
public class AwardBindRelStatistic {
  private Long id;

  private Long activityId;

  private Long userId;

  private String mobile;

  private Byte isNew;

  private Long inviteUserId;

  private String inviteMobile;

  private Date createDate;

  private Date updateDate;

  private Integer totalInviteNum;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public Byte getIsNew() {
    return isNew;
  }

  public void setIsNew(Byte isNew) {
    this.isNew = isNew;
  }

  public Long getInviteUserId() {
    return inviteUserId;
  }

  public void setInviteUserId(Long inviteUserId) {
    this.inviteUserId = inviteUserId;
  }

  public String getInviteMobile() {
    return inviteMobile;
  }

  public void setInviteMobile(String inviteMobile) {
    this.inviteMobile = inviteMobile;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public Integer getTotalInviteNum() {
    return totalInviteNum;
  }

  public void setTotalInviteNum(Integer totalInviteNum) {
    this.totalInviteNum = totalInviteNum;
  }
}
