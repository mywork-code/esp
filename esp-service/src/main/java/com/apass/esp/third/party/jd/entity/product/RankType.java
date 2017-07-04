package com.apass.esp.third.party.jd.entity.product;

import java.io.Serializable;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
public enum RankType implements Serializable{
    /**
     * 综合排序
     */
    COMPREHENSIVE_RANK,
    /**
     * 销量排序
     */
    SALES_RANK,
    /**
     * 价格排序
     */
    PRICE_RANK,
    /**
     * 价格排序(降序)
     */
    PRICE_RANK_DOWN
}
