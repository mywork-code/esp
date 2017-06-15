package com.apass.esp.service.refund;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.dto.aftersale.TxnInfoDto;
import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.CashRefundVoStatus;
import com.apass.esp.domain.vo.CashRefundVo;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.utils.BeanUtils;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CashRefundService {

    private static final Logger logger = LoggerFactory.getLogger(CashRefundService.class);

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private TxnInfoMapper txnInfoMapper;

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
     * 根据退款记录的状态，返回页面所需的数据
     * @param orderId
     * @return
     */
    public String getCashRundStatus(String orderId){
    	
    	CashRefundDto dto = getCashRefundByOrderId(orderId);
    	//如果记录为空，则返回空
    	if(dto == null){
    		return "";
    	}
    	//根据状态返回值
    	if(dto.getStatus() == 1){
    		return CashRefundVoStatus.CASHREFUND_STATUS1.getCode();
    	}else if(dto.getStatus() == 2 ||dto.getStatus() == 5  ){
    		return CashRefundVoStatus.CASHREFUND_STATUS2.getCode();
    	}else if(dto.getStatus() == 4){
    		return CashRefundVoStatus.CASHREFUND_STATUS3.getCode();
    	}else{
    		return CashRefundVoStatus.CASHREFUND_STATUS4.getCode();
    	}
    }
}
