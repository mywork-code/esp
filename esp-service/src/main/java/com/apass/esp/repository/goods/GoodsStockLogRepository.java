package com.apass.esp.repository.goods;

import com.apass.esp.domain.entity.goods.GoodsStockLogEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class GoodsStockLogRepository extends BaseMybatisRepository<GoodsStockLogEntity, Long> {

    /**
     * 根据订单号查询
     * 
     * @param goodsId
     * @return
     */
    public GoodsStockLogEntity loadByOrderId(String orderId) {
        return this.getSqlSession().selectOne("loadByOrderId", orderId);
    }
    /**
     * 根据订单号删除
     * 
     * @param orderId
     * @return
     */
    public int deleteByOrderId(String orderId) {
        return this.getSqlSession().delete("deleteByOrderId", orderId);
    }

}
