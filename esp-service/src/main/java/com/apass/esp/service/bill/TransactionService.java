package com.apass.esp.service.bill;

import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.repository.bill.TransactionRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    /**
     * 交易查询
     * @param map
     * @param page
     * @return
     */
    public Pagination<TxnInfoEntity> queryTransactionInforPage(Map<String, Object> map, Page page) {
        return transactionRepository.page(map, page, "queryTransactionInforPage");
    }


    /**
     * 根据 orderId查询交易 流水
     * @param orderId
     * @return
     */
    public TxnInfoEntity queryCreditTxnByOrderId(String orderId) {
        return transactionRepository.queryTxnByOrderId(orderId);
    }

}
