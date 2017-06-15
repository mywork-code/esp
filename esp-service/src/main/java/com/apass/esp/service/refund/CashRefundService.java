package com.apass.esp.service.refund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.TxnInfoDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.utils.BeanUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CashRefundService {

    private static final Logger logger = LoggerFactory.getLogger(CashRefundService.class);

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private TxnInfoMapper txnInfoMapper;
    @Autowired
    private OrderInfoRepository  orderInfoRepository;

    /**
     * @param orderId
     * @return
     */
    public CashRefundDto getCashRefundByOrderId(String orderId) {
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);

        CashRefundDto cashRefundDto = new CashRefundDto();
        BeanUtils.copyProperties(cashRefundDto, cashRefund);
        return cashRefundDto;
    }

    public void update(CashRefundDto cashRefundDto) {
        CashRefund cashRefund = new CashRefund();
        BeanUtils.copyProperties(cashRefund, cashRefundDto);
        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
    }

    /**
     * @param orderId
     * @return
     */
    public List<TxnInfoDto> getTxnInfoByOrderId(String orderId) {
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(orderId);
        List<TxnInfoDto> txnInfoDtoList = new ArrayList<>();
        for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
            TxnInfoDto txnInfoDto = new TxnInfoDto();
            BeanUtils.copyProperties(txnInfoDto, txnInfoEntity);
            txnInfoDtoList.add(txnInfoDto);
        }
        return txnInfoDtoList;
    }
    
    /**
     * 退款申请
     * @param orderId
     * @param reason
     * @param memo
     * @return
     */
    public void requestRefund(String requestId,String orderId,String userId, String reason,String memo) 
    		throws BusinessException{
    	OrderInfoEntity orderInfo=orderInfoRepository.selectByOrderIdAndUserId(orderId,Long.parseLong(userId));
    	CashRefund cashRefund=cashRefundMapper.getCashRefundByOrderId(orderId);
    	CashRefund  cr=new CashRefund();
    	if(null !=cashRefund){
    		cr.setOrderId(orderId);
    		cr.setReason(reason);
    		cr.setMemo(memo);
    		cr.setUpdateDate(new Date());
    		cashRefundMapper.updateByOrderIdSelective(cr);
    	}else{
    	  	if(null !=orderInfo && OrderStatus.ORDER_PAYED.getCode().equals(orderInfo.getStatus())){
        		cr.setCreateDate(new Date());
        		cr.setUpdateDate(new Date());
        		cr.setAmt(orderInfo.getOrderAmt());
        		cr.setOrderId(orderId);
        		cr.setStatus(Integer.parseInt(CashRefundStatus.CASHREFUND_STATUS1.getCode()));
        		cr.setStatusD(new Date());
        		cr.setUserId(Long.parseLong(userId));
        		cr.setMainOrderId(orderInfo.getMainOrderId());
        		cr.setReason(reason);
        		cr.setMemo(memo);
        		int result=cashRefundMapper.insert(cr);
        		if(result !=1){
    				LOG.info(requestId, "插入退款申请信息到数据失败!", "");
    				throw new BusinessException("退款申请失败！",BusinessErrorCode.ORDER_REQUEST_REFUND);
        		}
        		orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_REFUNDPROCESSING.getCode());
        	}
    	}
  
    }
    
    /**
     * 获取退款申请信息
     * @return
     */
    public CashRefund getRequestRefundInfo(String requestId,String orderId,String userId){
    	return cashRefundMapper.getCashRefundByOrderId(orderId);
    }
    
    
    
    
    
    
    
    
    
}
