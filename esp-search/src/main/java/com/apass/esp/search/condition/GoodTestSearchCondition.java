package com.apass.esp.search.condition;

import java.util.Date;

/**
 * Created by xianzhi.wang on 2017/5/23.
 */
public class GoodTestSearchCondition {
    private String name;//商品名称模糊

    private String cateGateName;//分类名称模糊

    private SortCondition sortCondition;//排序字段
    
    //开始时间
    private Date timeStart;
    //结束时间
    private Date timeEnd;

    public String getCateGateName() {
        return cateGateName;
    }

    public void setCateGateName(String cateGateName) {
        this.cateGateName = cateGateName;
    }

    public SortCondition getSortCondition() {
        return sortCondition;
    }

    public void setSortCondition(SortCondition sortCondition) {
        this.sortCondition = sortCondition;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
