package com.apass.esp.mapper;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by jie.xu on 17/4/28.
 */
public interface TxnInfoMapper {

  /**
   * 根据订单id查询流水
   */
  List<TxnInfoEntity> selectByOrderId(String orderId);

  void updateTime(@Param("orderId") String orderId,@Param("date") Date date);

}
