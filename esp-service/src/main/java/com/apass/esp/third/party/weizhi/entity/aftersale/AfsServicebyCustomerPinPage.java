package com.apass.esp.third.party.weizhi.entity.aftersale;

import java.util.List;

/**
 * Created by xiaohai on 2017/11/24.
 */
public class AfsServicebyCustomerPinPage {
    private List<AfsServicebyCustomerPin> serviceInfoList;
    private Integer totalNum;
    private Integer pageSize;
    private Integer pageNum;
    private Integer pageIndex;

    public List<AfsServicebyCustomerPin> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<AfsServicebyCustomerPin> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
