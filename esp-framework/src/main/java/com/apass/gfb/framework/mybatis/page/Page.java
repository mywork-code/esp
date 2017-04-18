package com.apass.gfb.framework.mybatis.page;

/**
 * 
 * @description 分页信息
 * 
 * @author Listening
 * @version $Id: Page.java, v 0.1 2014年7月20日 下午6:36:23 Listening Exp $
 */
public class Page {
	/**
	 * 页码
	 */
	private Integer page;

	/**
	 * 页面大小
	 */
	private Integer limit;

	public Integer getPageNo() {
		return page;
	}

	public Integer getPageSize() {
		return limit;
	}

	public Page(Integer pageNo, Integer pageSize) {
		this.page = pageNo;
		this.limit = pageSize;
	}

	public Page() {

	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public boolean hasNoParam() {
		return page == null || limit == null;
	}
}
