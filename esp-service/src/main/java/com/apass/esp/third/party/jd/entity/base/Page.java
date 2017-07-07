package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;
/**
 * @author xianzhi.wang
 * @time
 */
public class Page implements Serializable {


	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 1000;
	// 总条数
	private int allRows = 0;
	// 总页码
	private int allPages = 0;
	// 当前页码
	private int currentPage = 1;
	// 每页数量
	private int pageSize = DEFAULT_PAGE_SIZE;
	// 查询开始行数
	private int rowOffset = 0;

	public int getAllRows() {
		return allRows;
	}

	private void setPageInfo(int allRows) {
		allPages = allRows % pageSize == 0 ? allRows / pageSize : allRows / pageSize + 1;
		if (currentPage > allPages)
			currentPage = allPages;
	}

	public void setAllRows(int allRows) {
		setPageInfo(allRows);
		this.allRows = allRows;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > MAX_PAGE_SIZE)
			pageSize = MAX_PAGE_SIZE;
		this.pageSize = pageSize;
	}

	public int getRowOffset() {
		this.rowOffset = (currentPage - 1) * pageSize;
		return rowOffset < 0 ? 0 : rowOffset;
	}

}
