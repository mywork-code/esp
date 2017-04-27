package com.apass.esp.repository.bill;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.gfb.framework.annotation.MyBatisRepository;
import com.apass.gfb.framework.mybatis.support.BaseMybatisRepository;

@MyBatisRepository
public class TransactionRepository extends BaseMybatisRepository<TxnInfoEntity, Long> {

    /**
     * 根据订单号查询首付金额
     * @param orderId - 订单号
     * @return TxnInfoEntity
     */
    public TxnInfoEntity selectDownpaymentByOrderId(String orderId){
        return this.getSqlSession().selectOne(getSQL("selectDownpaymentByOrderId"), orderId);
    }

    /**
     * 根据 orderId查询交易 流水
     * @param orderId
     * @return
     */
    public TxnInfoEntity queryTxnByOrderId(String orderId) {
        return this.getSqlSession().selectOne(getSQL("queryTxnByOrderId"), orderId);
    }
}
