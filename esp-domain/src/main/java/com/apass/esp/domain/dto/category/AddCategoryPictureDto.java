package com.apass.esp.domain.dto.category;

import org.springframework.web.multipart.MultipartFile;

public class AddCategoryPictureDto {
	
    private String        categoryType;//类目名称

    private String        categoryTitle;//类目小标题
    
    private MultipartFile categoryPictureFile;//类目图片
    
    private MultipartFile categoryBannerPictureFile;//类目Banner图片

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public MultipartFile getCategoryPictureFile() {
		return categoryPictureFile;
	}

	public void setCategoryPictureFile(MultipartFile categoryPictureFile) {
		this.categoryPictureFile = categoryPictureFile;
	}

	public MultipartFile getCategoryBannerPictureFile() {
		return categoryBannerPictureFile;
	}

	public void setCategoryBannerPictureFile(MultipartFile categoryBannerPictureFile) {
		this.categoryBannerPictureFile = categoryBannerPictureFile;
	}
    
}
