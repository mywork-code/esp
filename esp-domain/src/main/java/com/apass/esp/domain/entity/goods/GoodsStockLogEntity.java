package com.apass.esp.domain.entity.goods;


import com.apass.gfb.framework.annotation.MyBatisEntity;

/**
 * 商品库存消耗日志
 * @description 
 *
 * @author liuming
 * @version $Id: GoodsStockLogEntity.java, v 0.1 2017年3月17日 下午5:16:14 liuming Exp $
 */
@MyBatisEntity
public class GoodsStockLogEntity {

    private Long id;

    private Long userId;

    private String  orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "GoodsStockLogEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + "]";
    }
}
