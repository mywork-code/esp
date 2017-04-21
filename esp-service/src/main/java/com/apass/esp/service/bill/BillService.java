
package com.apass.esp.service.bill;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.entity.bill.StatementEntity;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.repository.bill.BillRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private CustomerServiceClient customerServiceClient;
    
    @Autowired
    private TransactionService transactionService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

    /**
     * 账单分页查询
     * @param map
     * @param page
     * @return
     */
    public Pagination<StatementEntity> queryBillInforPage(Map<String, Object> map, Page page) {
        return billRepository.page(map, page, "queryBillInforPage");
    }
    
    /**
     * 
     * @param userId用户Id
     * @return true:有分期
     * @throws BusinessException
     */
    public boolean queryStatement(Long userId,String orderId) throws BusinessException{
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        // 1、查询客户信息
        CustomerInfo customerInfo = customerServiceClient.getCustomerInfo(userId);
        LOGGER.info("客户信息：[{}]", GsonUtils.toJson(customerInfo));
        
        // 获取账单日
        String billDay = customerInfo.getBillDate();
        // String billDay = "18";
        if (StringUtils.isBlank(billDay)) { // 没有获取到账单日,返货无账单数据 00
            return false;//如果没有授信，则无额度消费
        }
        
        Date outStmtBillDate = null; // 当前已出账单日 时间格式
        
        TxnInfoEntity txnInfoEntity = transactionService.queryTxnByOrderId(orderId);
        if(txnInfoEntity != null){
            Date txnDate = txnInfoEntity.getTxnDate();
            String txnDay = DateFormatUtil.dateToString(txnDate, "dd");
            if(Integer.parseInt(billDay)>Integer.parseInt(txnDay)){
                outStmtBillDate = DateFormatUtil.mergeDate(txnDate, Integer.parseInt(billDay));
            }else{
                outStmtBillDate = DateFormatUtil.mergeDate(DateFormatUtil.addMonth(txnDate, 1), Integer.parseInt(billDay));
            }
        }else{
            LOGGER.info("交易流水数据未生成,查询参数orderId：[{}]", orderId);
            return false;
            //throw new RuntimeException("交易流水数据有误");
        }
        
        paramMap.put("stmtDate",DateFormatUtil.dateToString(outStmtBillDate));
        
        List<StatementEntity> statements = billRepository.billRepository(paramMap);
        if(statements == null || statements.size() == 0){
            return false;//无帐单
        }else if(statements.size() == 1 && "S01".equals(statements.get(0).getStmtStatus())){
            return false;
        }else if(statements.size() == 2){
            return true;
        }else{
            throw new RuntimeException("帐单数据有误");
        }
    }
}

