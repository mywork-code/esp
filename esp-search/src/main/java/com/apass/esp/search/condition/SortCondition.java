package com.apass.esp.search.condition;

import java.util.Date;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public class SortCondition {

    //排序字段
    private String sortFiled;
    // 排序方式 (asc / desc)
    private boolean desc = true;

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
