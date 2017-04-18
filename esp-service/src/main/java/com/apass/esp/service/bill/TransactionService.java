package com.apass.esp.service.bill;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.dto.statement.StatementDto;
import com.apass.esp.domain.entity.bill.StatementEntity;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.repository.bill.BillRepository;
import com.apass.esp.repository.bill.TransactionRepository;
import com.apass.esp.repository.statement.StatementRepository;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

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

}
