package com.apass.esp.domain.dto.banner;

import org.springframework.web.multipart.MultipartFile;

public class AddBannerInfoEntity {
  private Long bannerId;
  private String bannerName;

  private MultipartFile bannerFile;

  private String bannerType;

  private String bannerOrder;

  private String activityUrl;

  private String activityName;

  public Long getBannerId() {
    return bannerId;
  }

  public void setBannerId(Long bannerId) {
    this.bannerId = bannerId;
  }

  public String getBannerName() {
    return bannerName;
  }

  public void setBannerName(String bannerName) {
    this.bannerName = bannerName;
  }

  public MultipartFile getBannerFile() {
    return bannerFile;
  }

  public void setBannerFile(MultipartFile bannerFile) {
    this.bannerFile = bannerFile;
  }

  public String getBannerType() {
    return bannerType;
  }

  public void setBannerType(String bannerType) {
    this.bannerType = bannerType;
  }

  public String getBannerOrder() {
    return bannerOrder;
  }

  public void setBannerOrder(String bannerOrder) {
    this.bannerOrder = bannerOrder;
  }

  public String getActivityUrl() {
    return activityUrl;
  }

  public void setActivityUrl(String activityUrl) {
    this.activityUrl = activityUrl;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  @Override
  public String toString() {
    return "AddBannerInfoEntity [bannerName=" + bannerName + ", bannerFile="
        + bannerFile.getName() + ", bannerType=" + bannerType + ", bannerOrder="
        + bannerOrder + ", activityUrl=" + activityUrl
        + ", activityName=" + activityName + "]";
  }
}
