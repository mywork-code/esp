package com.apass.esp.mapper;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;

import java.util.List;

/**
 * Created by jie.xu on 17/4/28.
 */
public interface TxnInfoMapper {

  /**
   * 根据订单id查询流水
   */
  List<TxnInfoEntity> selectByOrderId(String orderId);

  
   /**
     * 根据orderId查询去交易流水表的OrigOryid(原始消费交易的queryId)
	 * @param orderId:订单id
	 * @return
	 */
  String queryOrigTxnIdByOrderid(String orderId);


}
