package com.apass.esp.repository.order;

import java.util.List;

import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class OrderDetailInfoRepository extends BaseMybatisRepository<OrderDetailInfoEntity, Long> {
    /**
     * 根据订单号查询订单详情
     * 
     * @throws BusinessException
     */
    public List<OrderDetailInfoEntity> queryOrderDetailInfo(String orderId) throws BusinessException {
        try {
        	List<OrderDetailInfoEntity> jmList = getSqlSession().selectList(getSQL("queryOrderDetailInfo"), orderId);
            return jmList;
        } catch (Exception e) {
            throw new BusinessException("查询用户的消息列表失败", e);
        }
    }
    /**
     * 
     * 
     * @param orderId
     * @param code
     */
//    public void updateStatusByOrderId(String orderId, String status) {
//        OrderDetailInfoEntity param = new OrderDetailInfoEntity();
//        param.setOrderId(orderId);
//        param.setOrderStatus(status);
//        this.getSqlSession().update("updateOrderDetailStatus", param);
//    }
    /**
     * 根据 商户订单id查询订单详情
     * 
     * @param subOrderId
     * @return
     */
    public List<OrderDetailInfoEntity> queryOrderDetailBySubOrderId(String subOrderId) {
        return getSqlSession().selectList(getSQL("queryOrderDetailBySubOrderId"), subOrderId);
    }
    /**
     * 根据子订单Id更新订单详情状态
     * 
     * @param subOrderId
     * @param code
     * @return
     */
//    public Integer updateStatusBySubOrderId(String subOrderId, String code) {
//        OrderDetailInfoEntity param = new OrderDetailInfoEntity();
//        param.setSubOrderId(subOrderId);
//        param.setOrderStatus(code);
//        return this.getSqlSession().update("updateOrderDetailStatusBySubOrderId", param);
//    }

}
