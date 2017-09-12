package com.apass.esp.domain.dto.goods;

import org.springframework.web.multipart.MultipartFile;


/**
 * 类目图片上传dto
 * @author xiaohai
 *
 */
public class CategoryPicDto {
	private String categoryLevel;//类目级别
	private MultipartFile file;//文件
	
	
	public String getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "CategoryPicDto [categoryLevel=" + categoryLevel + ", file="
				+ file.getName() + "]";
	}
}
