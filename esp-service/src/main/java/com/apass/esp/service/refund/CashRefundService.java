package com.apass.esp.service.refund;

import java.util.List;

import com.apass.esp.domain.dto.aftersale.CashRefundDto;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.vo.CashRefundVo;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.jwt.common.ListeningRegExpUtils;

@Service
@Transactional(rollbackFor = { Exception.class })
public class CashRefundService {
	
	private static final Logger logger = LoggerFactory.getLogger(CashRefundService.class);
	
	@Autowired
	private CashRefundMapper cashRefundMapper;
	
	public List<CashRefundVo> cashRefundByOrderId(String orderId) throws BusinessException{
		
		if(StringUtils.isBlank(orderId)){
			throw new BusinessException("订单Id不能为空！");
		}
		
		if(!ListeningRegExpUtils.isNumberic(orderId)){
			throw new BusinessException("订单Id有误！");
		}
		
		List<CashRefund> list = cashRefundMapper.cashRefundByOrderId(Long.parseLong(orderId));
		
		return null;
	}


	public CashRefundDto getCashRefundByOrderId(String orderId){
		CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);

		CashRefundDto cashRefundDto = new CashRefundDto();
		BeanUtils.copyProperties(cashRefundDto, cashRefund);
		return cashRefundDto;
	}
}
