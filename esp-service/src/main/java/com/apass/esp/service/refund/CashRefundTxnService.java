package com.apass.esp.service.refund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.CashRefundTxn;
import com.apass.esp.domain.entity.goods.GoodsStockLogEntity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.mapper.CashRefundTxnMapper;
import com.apass.esp.repository.goods.GoodsStockLogRepository;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.service.order.OrderService;

import org.apache.commons.collections.CollectionUtils;
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
import com.apass.esp.domain.enums.CashRefundVoStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.utils.BeanUtils;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CashRefundTxnService {

    private static final Logger logger = LoggerFactory.getLogger(CashRefundTxnService.class);

    @Autowired
    private CashRefundTxnMapper cashRefundTxnMapper;


	/**
	 * 根据queryId修改退款流水表中的状态
	 * @param cashRefundTxn
	 * @return 
	 */
	public Integer updateStatusByQueryId(CashRefundTxn cashRefundTxn) {
		return cashRefundTxnMapper.updateByPrimaryKeySelective(cashRefundTxn);		
	}

   
}
