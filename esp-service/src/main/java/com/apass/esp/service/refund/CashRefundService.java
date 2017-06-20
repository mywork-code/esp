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
public class CashRefundService {

    private static final Logger logger = LoggerFactory.getLogger(CashRefundService.class);

    @Autowired
    private CashRefundMapper cashRefundMapper;

    @Autowired
    private TxnInfoMapper txnInfoMapper;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private CommonHttpClient commonHttpClient;

    @Autowired
    private CashRefundTxnMapper cashRefundTxnMapper;

    @Autowired
    private OrderService orderService;

    /**
     * @param orderId
     * @return
     */
    public CashRefundDto getCashRefundByOrderId(String orderId) {
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);

        CashRefundDto cashRefundDto = null;
        if(cashRefund != null){
        	cashRefundDto = new CashRefundDto();
        	BeanUtils.copyProperties(cashRefundDto, cashRefund);
        }
        return cashRefundDto;
    }

    public void update(CashRefundDto cashRefundDto) {
        CashRefund cashRefund = new CashRefund();
        BeanUtils.copyProperties(cashRefund, cashRefundDto);
        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
    }

    public void updateCashRefundDto(CashRefundDto cashRefundDto) {
        update(cashRefundDto);
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setOrderId(cashRefundDto.getOrderId());
        orderInfoEntity.setStatus(OrderStatus.ORDER_PAYED.getCode());
        orderService.updateOrderStatus(orderInfoEntity);
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
     *
     * @param orderId
     * @return
     */
    public String getCashRundStatus(String orderId){
    	CashRefundDto dto = getCashRefundByOrderId(orderId);
    	//如果记录为空，则返回空
    	if(dto == null){
    		return CashRefundVoStatus.CASHREFUND_STATUS0.getCode();
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

    /*
     * 退款申请
     * @param orderId
     * @param reason
     * @param memo
     * @return
     */
    public void requestRefund(String requestId, String orderId, String userId, String reason, String memo)
            throws BusinessException {
        OrderInfoEntity orderInfo = orderInfoRepository.selectByOrderIdAndUserId(orderId, Long.parseLong(userId));
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);
        CashRefund cr = new CashRefund();
        if (null != cashRefund) {
            cr.setOrderId(orderId);
            cr.setReason(reason);
            cr.setMemo(memo);
            cr.setUpdateDate(new Date());
            cashRefundMapper.updateByOrderIdSelective(cr);
        } else {
            if (null != orderInfo && OrderStatus.ORDER_PAYED.getCode().equals(orderInfo.getStatus())) {
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
                int result = cashRefundMapper.insert(cr);
                if (result != 1) {
                    LOG.info(requestId, "插入退款申请信息到数据失败!", "");
                    throw new BusinessException("退款申请失败！", BusinessErrorCode.ORDER_REQUEST_REFUND);
                }
                orderInfoRepository.updateStatusByOrderId(orderId, OrderStatus.ORDER_REFUNDPROCESSING.getCode());
            }
        }

    }

    /**
     * 获取退款申请信息
     *
     * @return
     */
    public CashRefund getRequestRefundInfo(String requestId, String orderId, String userId) {
        return cashRefundMapper.getCashRefundByOrderId(orderId);
    }

    /**
     * 同意退款
     *
     * @param orderId
     * @return
     */
    public Response agreeRefund(String userId, String orderId) {
        CashRefund cashRefund = cashRefundMapper.getCashRefundByOrderId(orderId);

        //1:退款提交 才能进行同意
        if (cashRefund == null || cashRefund.getStatus() != 1) {
            return Response.fail(BusinessErrorCode.NO);
        }
        List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(cashRefund.getMainOrderId());
        if (CollectionUtils.isEmpty(txnInfoEntityList)) {
            return Response.fail(BusinessErrorCode.NO);
        }
        BigDecimal txnAmt = new BigDecimal(0);
        if (txnInfoEntityList.size() == 1) {
            if (!TxnTypeCode.KQEZF_CODE.getCode().equalsIgnoreCase(txnInfoEntityList.get(0).getTxnType())) {
                return Response.fail(BusinessErrorCode.NO);
            } else {//银行卡支付  处理中
                txnAmt = txnInfoEntityList.get(0).getTxnAmt();
                CashRefundTxn cashRefundTxn = new CashRefundTxn();
                cashRefundTxn.setAmt(txnAmt);
                cashRefundTxn.setTypeCode(TxnTypeCode.KQEZF_CODE.getCode());
                cashRefundTxn.setOriTxnCode(String.valueOf(txnInfoEntityList.get(0).getOrigTxnCode()));
                cashRefundTxn.setStatus("1");
                cashRefundTxn.setCashRefundId(cashRefund.getId());
                cashRefundTxn.setCreateDate(new Date());
                cashRefundTxn.setUpdateDate(new Date());
                cashRefundTxnMapper.insert(cashRefundTxn);
                cashRefund.setUpdateDate(new Date());
                cashRefund.setStatus(2);
                cashRefund.setStatusD(new Date());
                cashRefundMapper.updateByPrimaryKeySelective(cashRefund);

                try {
                    orderService.addGoodsStock("",orderId);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
                return Response.successResponse();
            }
        } else {
            for (TxnInfoEntity txnInfoEntity : txnInfoEntityList) {
                CashRefundTxn cashRefundTxn = new CashRefundTxn();
                cashRefundTxn.setAmt(txnInfoEntity.getTxnAmt());
                cashRefundTxn.setTypeCode(txnInfoEntity.getTxnType());
                cashRefundTxn.setOriTxnCode(String.valueOf(txnInfoEntity.getOrigTxnCode()));
                cashRefundTxn.setStatus("1");
                cashRefundTxn.setCashRefundId(cashRefund.getId());
                cashRefundTxn.setCreateDate(new Date());
                cashRefundTxn.setUpdateDate(new Date());
                cashRefundTxnMapper.insert(cashRefundTxn);

                if (TxnTypeCode.XYZF_CODE.getCode().equalsIgnoreCase(txnInfoEntity.getTxnType())) {
                    Response res = commonHttpClient.updateAvailableAmount("", Long.valueOf(userId), String.valueOf(txnInfoEntity.getTxnAmt()));
                    if (!res.statusResult()) {
                        cashRefund.setUpdateDate(new Date());
                       // cashRefund.setStatus(5);
                        cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                        cashRefundTxn.setStatus("3");
                        cashRefundTxn.setUpdateDate(new Date());
                        cashRefundTxnMapper.updateByPrimaryKeySelective(cashRefundTxn);
                        return res;
                    }
                    cashRefundTxn.setStatus("2");
                    cashRefundTxn.setUpdateDate(new Date());
                    cashRefundTxnMapper.updateByPrimaryKeySelective(cashRefundTxn);
                    cashRefund.setUpdateDate(new Date());
                    cashRefund.setStatusD(new Date());
                    //cashRefund.setStatus(4);
                    cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
                    try {
                        orderService.addGoodsStock("",orderId);
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    }
                    return res;
                }
            }
            return Response.successResponse();
        }
    }

	/**
	 * 根据订单id修改退款状态
	 * @param cashRefund
	 */
	public Integer updateRefundCashStatusByOrderid(CashRefund cashRefund) {
		return cashRefundMapper.updateByPrimaryKeySelective(cashRefund);
	}

	/**
	 * 查询所有退款中 的订单
	 * @param code
	 * @return
	 */
	public List<CashRefund> getCashRefundByStatus(String status) {
		return cashRefundMapper.queryCashRefundByStatus(Integer.valueOf(status));
	}
}
