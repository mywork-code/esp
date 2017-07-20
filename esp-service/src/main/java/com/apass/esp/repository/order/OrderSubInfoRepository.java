package com.apass.esp.repository.order;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.order.OrderSubInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 订单子表dao
 * 
 * @description
 *
 * @author chenbo
 * @version $Id: OrderSubInfoRepository.java, v 0.1 2016年12月22日 下午1:39:15 chenbo
 *          Exp $
 */
@MyBatisRepository
public class OrderSubInfoRepository extends BaseMybatisRepository<OrderSubInfoEntity, Long> {
    /**
     * 查询订单详情通过参数
     * 
     * @throws BusinessException
     */
    public Pagination<OrderSubInfoEntity> querySubOrderDetailInfoByParam(Map<String, String> map,
                                                                         Page page) throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderList = page(map, page, getSQL("querySubOrderDetailInfoByParam"));
            return orderList;
        } catch (Exception e) {
            throw new BusinessException("通过商户号查询订单列表失败", e);
        }
    }
    
    /**
     * 查询订单详情通过参数
     * 
     * @throws BusinessException
     */
    public Pagination<OrderSubInfoEntity> queryOrderCashRefundException(Map<String, String> map,
                                                                         Page page) throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderList = page(map, page, getSQL("queryOrderCashRefundException"));
            return orderList;
        } catch (Exception e) {
            throw new BusinessException("通过商户号查询订单列表失败", e);
        }
    }
    
    public Pagination<OrderSubInfoEntity> queryOrderRefundException(Map<String, String> map,
            Page page) throws BusinessException {
		try {
			Pagination<OrderSubInfoEntity> orderList = page(map, page, getSQL("queryOrderRefundException"));
			return orderList;
		} catch (Exception e) {
			throw new BusinessException("通过商户号查询订单列表失败", e);
		}
	}
    
    
    /**
     * 查询被二次拒绝的订单
     */
    public Pagination<OrderSubInfoEntity> queryOrderInfoRejectAgain(Page page) throws BusinessException {
		try {
			  Pagination<OrderSubInfoEntity> orderList = page(null,page, getSQL("queryOrderInfoRejectAgain"));
			  return orderList;
		} catch (Exception e) {
			  throw new BusinessException("查询被二次拒绝的订单", e);
		}
    }
    
    /**
     * 更新物流信息通过订单号
     * 
     * @throws BusinessException
     */
    public void updateLogisticsInfoByOrderId(Map<String, String> map) throws BusinessException {
        updateBymap(map, getSQL("updateLogisticsInfoByOrderId"));
    }

    /**
     * 更新订单状态为售后完成
     * 
     * @throws BusinessException
     */
    public void updateOrderStatusByOrderId(Map<String, String> map) throws BusinessException {
        updateBymap(map, getSQL("updateOrderStatusByOrderId"));
    }

    /**
     * 更新订单状态为售后完成、更新最后收货时间默认为15天
     * 
     * @throws BusinessException
     */
    public void updateOrderStatusAndLastRtimeByOrderId(Map<String, String> map) throws BusinessException {
        updateBymap(map, getSQL("updateOrderStatusAndLastRtimeByOrderId"));
    }

    /**
     * 订单查询导出查询
     * @param map
     * @param page
     * @return
     * @throws BusinessException
     */
    public Pagination<OrderSubInfoEntity> queryOrderSubDetailInfoByParamForExport(Map map,
                                                                                  Page page) throws BusinessException {
        try {
            Pagination<OrderSubInfoEntity> orderList = page(map, page,
                getSQL("querySubOrderDetailInfoByParamForExport"));
            return orderList;
        } catch (Exception e) {
            throw new BusinessException("通过商户号查询订单列表失败", e);
        }
    }
    
    /**
     * 邮件发送 专用
     * @return
     */
    public List<OrderSubInfoEntity> queryOrderSubInfoByTime(Map<String,String> param) {
        return getSqlSession().selectList(getSQL("queryOrderSubInfoByTime"), param);
    }
}
