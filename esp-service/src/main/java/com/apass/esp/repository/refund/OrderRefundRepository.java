package com.apass.esp.repository.refund;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.dto.refund.RefundedOrderInfoDto;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

/**
 * 退货详细dao
 * 
 * @description
 *
 * @author chenbo
 * @version $Id: OrderRefundRepository.java, v 0.1 2016年12月23日 下午1:39:15 chenbo
 *          Exp $
 */
@MyBatisRepository
public class OrderRefundRepository extends BaseMybatisRepository<RefundInfoEntity, Long> {
	/**
	 * 查询退货详细信息
	 * 
	 * @throws BusinessException
	 */
	public List<RefundInfoEntity> queryRefundInfoByParam(Map<String, Object> map) throws BusinessException {
		try {
			List<RefundInfoEntity> refundList = getSqlSession().selectList(getSQL("queryRefundInfo"), map);
			return refundList;
		} catch (Exception e) {
			throw new BusinessException("查询", e);
		}
	}

	/**
	 * 保存物流厂商、单号信息
	 * 
	 * @param refundInfo
	 */
	public int submitLogisticsInfo(RefundInfoEntity refundInfo) {
		return getSqlSession().update(getSQL("updateLogisticsInfo"), refundInfo);
	}

	/**
	 * 更新售后状态
	 * 
	 * @param map
	 */
	public int updateRefundStatusByOrderId(Map<String, String> map) {
		return getSqlSession().update(getSQL("updateRefundStatusByOrderId"), map);
	}
	
	/**
	 * 售后完成，更新售后状态和售后完成时间
	 * 
	 * @param map
	 */
	public int updateRefundStatusAndCtimeByOrderId(Map<String, String> map) {
		return getSqlSession().update(getSQL("updateRefundStatusAndCtimeByOrderId"), map);
	}
	
	/**
	 * 同意售后请求
	 * 
	 * @param map
	 */
	public int agreeRefundByOrderId(Map<String, String> map) {
		return getSqlSession().update(getSQL("agreeRequestByOrderId"), map);
	}

	/**
	 * 驳回售后请求
	 * 
	 * @param map
	 */
	public int rejectRequestByOrderId(Map<String, String> map) {
		return getSqlSession().update(getSQL("rejectRequestByOrderId"), map);
	}

	/**
	 * 重新发货
	 * 
	 * @param map
	 */
	public int sendGoodsAgain(Map<String, String> map) {
		return getSqlSession().update(getSQL("sendGoodsAgain"), map);
	}

	/**
	 * 查询售后完成1天以上2天以内的售后订单数据
	 */
    public List<RefundedOrderInfoDto> queryReturningOrderInfo(Map<String, Object> map) {
        return getSqlSession().selectList(getSQL("queryReturningOrderInfo"), map);
    }

    /**
     * 查询 商家重新发货物流显示已签收的数据
     * 
     * @param map
     * @return
     */
    public List<RefundInfoEntity> queryReceiptedInfo(Map<String, Object> map) {
        return getSqlSession().selectList(getSQL("queryReceiptedInfo"), map);
    }

    /**
     * 更新换货商户发货物流唯一标识
     * 
     * @param map
     */
    public int updateRlogisticsIdbyRefundId(Map<String, Object> map) {
        return getSqlSession().update(getSQL("updateRlogisticsIdbyId"), map);
    }
    
    /**
     * 查询售后失败,订单状态处于售后处理中的数据
     */
    public List<RefundedOrderInfoDto> queryReturningOrderRefundInfo(Map<String, Object> map) {
        return getSqlSession().selectList(getSQL("queryReturningOrderRefundInfo"), map);
    }

    /**
     * 根据订单ID,退换货类型查询退换货信息
     * @param map
     * @return
     */
    public RefundInfoEntity queryRefundInfoByOrderIdAndRefundType(Map<String, Object> map) {
        return getSqlSession().selectOne(getSQL("queryRefundInfoByOrderIdAndRefundType"), map);
    }
}
