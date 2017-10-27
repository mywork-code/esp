package com.apass.esp.repository.order;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.bill.SalesOrderInfo;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;

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
     * 根据订单列表查询订单详情
     *
     * @param orderList
     * @return
     * @throws BusinessException
     */
    public List<OrderDetailInfoEntity> queryOrderDetailListByOrderList(List<String> orderList) throws BusinessException {
        try {
            List<OrderDetailInfoEntity> jmList = getSqlSession().selectList(getSQL("queryOrderDetailListByOrderList"), orderList);
            return jmList;
        } catch (Exception e) {
            throw new BusinessException("查询用户的消息列表失败", e);
        }
    }

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
	 * 根据skuId查询商品是否被下单过
	 * @param skuIds
	 * @return
	 */
	public List<OrderDetailInfoEntity> queryOrderDetailBySkuIds(
			List<Long> skuIds) {
		return getSqlSession().selectList(getSQL("queryOrderDetailBySkuIds"), skuIds);
	}

    public List<SalesOrderInfo> selectByOrderStatusList(List<String> statusArray,String dateBegin,String dateEnd){
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("statusArray",statusArray);
        paramMap.put("dateBegin",dateBegin);
        paramMap.put("dateEnd",dateEnd);
        return getSqlSession().selectList(getSQL("salesorderinfoList"),paramMap);
    }
}
