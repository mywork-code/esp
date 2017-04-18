package com.apass.esp.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 分页工具类
 * 
 * @author lixining
 * @version $Id: Pagination.java, v 0.1 2014年7月21日 下午6:56:06 lixining Exp $
 */
public class PaginationManage<T> {
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 记录总数
     */
    private Integer totalCount = 0;
    /**
     * 列表记录
     */
    private List<T> dataList   = new ArrayList<T>();

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

    public void addAllData(Collection<T> collection) {
        if (collection == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<T>();
        }
        dataList.addAll(collection);
    }

    public void addData(T data) {
        if (dataList == null) {
            dataList = new ArrayList<T>();
        }
        dataList.add(data);
    }

    /**
     * Getter method for property <tt>pageNo</tt>.
     * 
     * @return property value of pageNo
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * Setter method for property <tt>pageNo</tt>.
     * 
     * @param pageNo value to be assigned to property pageNo
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     * 
     * @return property value of pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     * 
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMaxPageNo() {
        return totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1);
    }

    public Integer getCurrentPageNo() {
        Integer maxPageNo = getMaxPageNo();
        Integer currentPage = pageNo <= maxPageNo ? pageNo : maxPageNo;
        return currentPage <= 0 ? 1 : currentPage;
    }

    public Integer getStartRow() {
        Integer currentPage = getCurrentPageNo();
        return (currentPage - 1) * pageSize + 1;
    }

    public Integer getEndRow() {
        Integer currentPage = getCurrentPageNo();
        return currentPage * pageSize;
    }

    public void setPageInfo(Integer pageNo, Integer pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public Integer getPageShowStart() {
        Integer segmentSize = 5;
        Integer currentPage = getCurrentPageNo();
        Integer segmentIndex = (currentPage % segmentSize == 0) ? (currentPage / segmentSize)
            : (currentPage / segmentSize + 1);
        return (segmentIndex - 1) * segmentSize + 1;
    }

    public Integer getPageShowEnd() {
        Integer segmentSize = 5;
        Integer currentPage = getCurrentPageNo();
        Integer segmentIndex = (currentPage % segmentSize == 0) ? (currentPage / segmentSize)
            : (currentPage / segmentSize + 1);
        return segmentIndex * segmentSize;
    }

    public List<Integer> getPageShowList() {
        List<Integer> pageShowList = Lists.newArrayList();
        Integer segmentSize = 5;
        Integer currentPage = getCurrentPageNo();
        Integer segmentIndex = (currentPage % segmentSize == 0) ? (currentPage / segmentSize)
            : (currentPage / segmentSize + 1);
        Integer start = (segmentIndex - 1) * segmentSize + 1;
        Integer end = segmentIndex * segmentSize;
        for (int i = start; i <= end; i++) {
            pageShowList.add(i);
        }
        return pageShowList;
    }
}