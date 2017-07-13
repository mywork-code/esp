package com.apass.esp.service;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.TxnInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jie.xu on 17/7/13.
 */
@Service
public class TxnInfoService {

  @Autowired
  private TxnInfoMapper txnInfoMapper;

  /**
   * 判断用户某笔订单是否进行了未出账主动还款
   * true:是
   */
  public boolean isActiveRepayForConsumableCredit(Long userId,String mainOrderId){
    TxnInfoEntity txn = txnInfoMapper.selectLatestTxnByUserId(userId, TxnTypeCode.REPAY_CODE.getCode());
    if(txn == null){
      return false;
    } else {
      TxnInfoEntity creditTxn = txnInfoMapper.queryOrigTxnIdByOrderidAndstatus(mainOrderId,TxnTypeCode.XYZF_CODE.getCode());
      if(creditTxn == null){
        return false;
      }
      //还款时间大于交易时间 则表示主动还款了
      if(txn.getTxnDate() != null && creditTxn.getTxnDate() != null && txn.getTxnDate().getTime() >= creditTxn.getTxnDate().getTime()){
        return true;
      }else{
        return false;
      }
    }
  }

}
