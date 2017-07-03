package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public class ListCondition implements Serializable {
	private int firstCategoryId;
	private int secondCategoryId;
	private int thirdCategoryId;

	private int categoryId = -1;

	private int pageIndex = 0;
	private int pageCount = 20;
	private String mallId;

	// 默认综合排序
	private RankType rankType = RankType.COMPREHENSIVE_RANK;

	public int getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(int firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public int getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(int secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public int getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(int thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public RankType getRankType() {
		return rankType;
	}

	public void setRankType(RankType rankType) {
		this.rankType = rankType;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

}
