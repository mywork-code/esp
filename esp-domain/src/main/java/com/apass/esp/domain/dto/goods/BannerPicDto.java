package com.apass.esp.domain.dto.goods;

import org.springframework.web.multipart.MultipartFile;

/**
 * banner上传Dto
 * 
 * @author zhanwendong
 *
 */
public class BannerPicDto {

	private String bannerGoodsId;// 商品ID
	private String bannerPicName;//名称
	private String bannerPicOrder;//order
	private MultipartFile bannerPicFile;//文件
	
	public String getBannerGoodsId() {
		return bannerGoodsId;
	}
	public void setBannerGoodsId(String bannerGoodsId) {
		this.bannerGoodsId = bannerGoodsId;
	}
	public String getBannerPicName() {
		return bannerPicName;
	}
	public void setBannerPicName(String bannerPicName) {
		this.bannerPicName = bannerPicName;
	}
	public String getBannerPicOrder() {
		return bannerPicOrder;
	}
	public void setBannerPicOrder(String bannerPicOrder) {
		this.bannerPicOrder = bannerPicOrder;
	}
	public MultipartFile getBannerPicFile() {
		return bannerPicFile;
	}
	public void setBannerPicFile(MultipartFile bannerPicFile) {
		this.bannerPicFile = bannerPicFile;
	}
	@Override
	public String toString() {
		return "BannerPicDto [bannerGoodsId=" + bannerGoodsId
				+ ", bannerPicName=" + bannerPicName + ", bannerPicOrder="
				+ bannerPicOrder + ", bannerPicFile=" + bannerPicFile.getName() + "]";
	}

}
