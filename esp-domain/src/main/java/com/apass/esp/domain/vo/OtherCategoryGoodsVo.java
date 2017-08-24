package com.apass.esp.domain.vo;

import java.util.List;

import com.apass.esp.domain.dto.goods.GoodsCategoryDto;

public class OtherCategoryGoodsVo {
    /**
     * 每个一级类目下的轮播图--固定
     */
    private String banner;

    /**
     * 二级类目Id
     */
    private Long categoryIdSecond;
    /**
     * 类目名称
     */
    private String categoryNameSecond;
    /**
     * 级别
     */
    private Long level;
    /**
     * 图片路径
     */
    private String pictureUrl;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 修改时间
     */
    private String updateDate;
    /**
     * 当前类目下的下属分类
     */
    private List<GoodsCategoryDto> goodsCategoryDtos;
    
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}

	
	public Long getCategoryIdSecond() {
		return categoryIdSecond;
	}
	public void setCategoryIdSecond(Long categoryIdSecond) {
		this.categoryIdSecond = categoryIdSecond;
	}
	public String getCategoryNameSecond() {
		return categoryNameSecond;
	}
	public void setCategoryNameSecond(String categoryNameSecond) {
		this.categoryNameSecond = categoryNameSecond;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public List<GoodsCategoryDto> getGoodsCategoryDtos() {
		return goodsCategoryDtos;
	}
	public void setGoodsCategoryDtos(List<GoodsCategoryDto> goodsCategoryDtos) {
		this.goodsCategoryDtos = goodsCategoryDtos;
	}

}
