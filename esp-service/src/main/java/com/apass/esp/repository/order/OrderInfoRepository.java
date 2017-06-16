package com.apass.esp.repository.order;

import com.apass.esp.domain.dto.aftersale.IdNum;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MyBatisRepository
public class OrderInfoRepository extends BaseMybatisRepository<OrderInfoEntity, Long> {
    /**
     * 根据订单列表查询订单概要信息列表
     * 
     * @throws BusinessException
     */
    public List<OrderInfoEntity> selectByOrderIdList(Map<String, Object> paramMap) throws BusinessException {
        try {
            List<OrderInfoEntity> oiList = getSqlSession().selectList(getSQL("selectByOrderIdList"), paramMap);
            return oiList;
        } catch (Exception e) {
            throw new BusinessException("根据订单列表查询订单概要信息列表失败", e);
        }
    }
    
    /**
     * 查询订单概要信息列表
     * 
     * @throws BusinessException
     */
    public List<OrderInfoEntity> queryOrderInfoList(Map<String, Object> paramMap) throws BusinessException {
        try {
            List<OrderInfoEntity> oiList = getSqlSession().selectList(getSQL("queryOrderInfoList"), paramMap);
            return oiList;
        } catch (Exception e) {
            throw new BusinessException("查询用户的消息列表失败", e);
        }
    }

    /**
     * 根据mainOrderId查询订单
     * 
     * @param mainOrderId
     * @return
     * @throws BusinessException
     */
    public List<OrderInfoEntity> selectByMainOrderId(String mainOrderId) throws BusinessException {
        return getSqlSession().selectList(getSQL("selectByMainOrderId"), mainOrderId);
    }
    
    public OrderInfoEntity selectByOrderIdAndUserId(String orderId, Long userId) {
        HashMap<Object, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        param.put("userId", userId);
        return getSqlSession().selectOne("selectByOrderIdAndUserId", param);
    }
    
    public OrderInfoEntity selectByOrderId(String orderId) {
        HashMap<Object, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        return getSqlSession().selectOne("selectByOrderIdAndUserId", param);
    }

    /**
     * 更新订单状态
     * 
     * @param orderId
     */
    public void updateStatusByOrderId(String orderId, String status) {
        OrderInfoEntity param = new OrderInfoEntity();
        param.setOrderId(orderId);
        param.setStatus(status);
        this.getSqlSession().update("updateStatusByOrderId", param);
    }
    /**
     * 根据mainOrderId更新订单信息
     * 
     * @param param
     */
    public void updateByMainOrderId(OrderInfoEntity param) {
        this.getSqlSession().update("updateByMainOrderId", param);
    }
    
    /**
    * 获取用户 待付款、待发货、待收货 子订单数量
    * 
    * @param userId
    * @return
    */
    public List<IdNum> getOrderNum(Long userId) {
        return this.getSqlSession().selectList("getOrderNum", userId);
    }

    /**
     * 查询未支付订单
     * 
     * @return
     */
    public List<OrderInfoEntity> loadNoPayList() {
        return this.getSqlSession().selectList("loadNoPayList");
    }

    /**
     * 查询所有待签收订单
     * 
     * @return
     */
    public List<OrderInfoEntity> loadNoSignOrders() {
        return this.getSqlSession().selectList("loadNoSignOrders");
    }

    /**
     * 根据物流Id标识查询
     * 
     * @param logisticsId
     * @return
     */
    public OrderInfoEntity loadBylogisticsId(String logisticsId) {
        return getSqlSession().selectOne("loadBylogisticsId", logisticsId);
    }

    /**
     * 查询售后完成且超过3天的订单
     * @return
     */
    public List<OrderInfoEntity> loadCompleteOrderGtThreeDay() {
        return this.getSqlSession().selectList("loadCompleteOrderGtThreeDay");
    }

    /**
     * 最新赊购记录
     *
     * @param userId
     * @return
     */
    public OrderInfoEntity queryLatestSuccessOrderInfo(Long userId) {
        return getSqlSession().selectOne(getSQL("queryLatestSuccessOrderInfo"), userId);
    }
    
    /**
     * 查询待发货订单的信息，切订单的预发货状态为''
     */
    public List<OrderInfoEntity> toBeDeliver() {
    	return this.getSqlSession().selectList("toBeDeliver");
    }
    
    public void updateOrderStatusAndPreDelivery(OrderInfoEntity entity){
    	this.getSqlSession().update("updateOrderStatusAndPreDelivery",entity);
    }

    public void updateOrderStatus(OrderInfoEntity entity){
        this.getSqlSession().update("updateOrderStatusAndPreDelivery",entity);
    }

}
