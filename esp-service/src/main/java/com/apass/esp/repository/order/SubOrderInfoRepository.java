package com.apass.esp.repository.order;


import java.util.List;

import com.apass.esp.domain.dto.aftersale.IdNum;
import com.apass.esp.domain.entity.order.SubOrderInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class SubOrderInfoRepository extends BaseMybatisRepository<SubOrderInfoEntity, Long> {

    public List<SubOrderInfoEntity> selectByOrderId(String orderId) {
        return this.getSqlSession().selectList("selectByOrderId", orderId);
    }
    /**
     * 根据orderId更新状态
     * 
     * @param orderId
     * @param status
     */
    public void updateStatusByOrderId(String orderId, String status) {
        SubOrderInfoEntity param = new SubOrderInfoEntity();
        param.setOrderId(orderId);
        param.setStatus(status);
        this.getSqlSession().update("updateSubOrderStatus", param);
    }
    /**
     * 根据suborderId更新状态
     * 
     * @param subOrderId
     * @param status
     */
    public Integer updateStatusBySubOrderId(String subOrderId, String status) {
        SubOrderInfoEntity param = new SubOrderInfoEntity();
        param.setOrderId(subOrderId);
        param.setStatus(status);
       return  this.getSqlSession().update("updateStatusBySubOrderId", param);
    }

    /**
     * 根据子订单查询子订单
     * 
     * @param subOrderId
     * @return
     */
    public SubOrderInfoEntity loadSubOrderBySubId(String subOrderId) {
        return getSqlSession().selectOne(getSQL("loadSubOrderBySubId"),subOrderId);
    }

    /**
     * 获取用户 待付款、待发货、待收货 子订单数量
     * 
     * @param userId
     * @return
     */
    public List<IdNum> getSubOrderNum(Long userId) {
        return this.getSqlSession().selectList("getSubOrderNum", userId);
    }

    /**
     * 根据子订单编号查询子订单状态
     * 
     * @param subOrderId
     * @return
     */
    public String getSubOrderStatus(String subOrderId) {
        return getSqlSession().selectOne(getSQL("getSubOrderStatus"),subOrderId);
    }
}
