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
  List<TxnInfoEntity> selectByOrderId(Long orderId);


}
