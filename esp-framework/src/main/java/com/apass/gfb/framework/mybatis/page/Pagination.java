package com.apass.gfb.framework.mybatis.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author lixining
 * @version $Id: Pagination.java, v 0.1 2014年7月21日 下午6:56:06 lixining Exp $
 */
public class Pagination<T> {

	/**
	 * 记录总数
	 */
	private Integer totalCount = 0;

	public static final int DEFAULT_PAGE_NUM = 1;
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 列表记录
	 */
	private List<T> dataList = new ArrayList<T>();

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		if (dataList != null) {
			this.dataList = dataList;
		}
	}

}
