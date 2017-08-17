package com.apass.esp.search.enums;

import java.io.Serializable;

/**
 * type: enum
 * 排序规则枚举类
 *
 * @author xianzhi.wang
 * @date 2017/8/15
 * @see
 * @since JDK 1.8
 */
public enum SortMode implements Serializable {
    ID_ASC("id", false),
    ID_DESC("id", true),
    SALEVALUE_ASC("saleNum", false),//销量
    SALEVALUE_DESC("saleNum", true),
    ORDERVALUE_ASC("listTime", false),//上架时间
    ORDERVALUE_DESC("listTime", true),
    TIMECREATED_ASC("createDate", false),//创建时间
    TIMECREATED_DESC("createDate", true),
    PRICE_ASC("goodsPrice", false),//创建时间
    PRICE_DESC("goodsPrice", true);

    private final String sortField;
    private final boolean desc;

    private SortMode(String sortField, boolean desc) {
        this.sortField = sortField;
        this.desc = desc;
    }

    public String getSortField() {
        return sortField;
    }

    public boolean isDesc() {
        return desc;
    }

    public static SortMode toSortMode(String value) {
        for (SortMode sortMode : SortMode.values()) {
            if (sortMode.name().equals(value)) {
                return sortMode;
            }
        }
        return null;
    }
}
