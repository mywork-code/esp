package com.apass.esp.domain.dto.goods;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;


/**
 * 类目图片上传dto
 * @author xiaohai
 *
 */
public class HomeConfigDto {
	private Long id;

    private String homeName;

    private String startTime;

    private String endTime;

    private String homeStatus;

    private String logoUrl;

    private String activeLink;

	private MultipartFile addConfigFilePic;//文件

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHomeName() {
		return homeName;
	}

	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHomeStatus() {
		return homeStatus;
	}

	public void setHomeStatus(String homeStatus) {
		this.homeStatus = homeStatus;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getActiveLink() {
		return activeLink;
	}

	public void setActiveLink(String activeLink) {
		this.activeLink = activeLink;
	}

	public MultipartFile getAddConfigFilePic() {
		return addConfigFilePic;
	}

	public void setAddConfigFilePic(MultipartFile addConfigFilePic) {
		this.addConfigFilePic = addConfigFilePic;
	}
}
