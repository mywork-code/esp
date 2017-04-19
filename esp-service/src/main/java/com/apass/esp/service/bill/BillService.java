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
    public boolean queryStatement(Long userId) throws BusinessException{
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        // 1、查询客户信息
        CustomerInfo customerInfo = customerServiceClient.getCustomerInfo(userId);
        LOGGER.info("客户信息：[{}]", GsonUtils.toJson(customerInfo));
        
        // 获取账单日
        String billDay = customerInfo.getBillDate();
        // String billDay = "18";
        if (StringUtils.isBlank(billDay)) { // 没有获取到账单日,返货无账单数据 00
            return false;
        }
        
        String currDay = DateFormatUtil.getNowDay(); // 当日
        Date outStmtBillDate = null; // 当前已出账单日 时间格式
        Date currDate = new Date();
        // 比较当日是否本月已出账单
        if (Integer.parseInt(billDay) <= Integer.parseInt(currDay)) { // 当月账单已出
            outStmtBillDate = DateFormatUtil.mergeDate(currDate, Integer.parseInt(billDay));
        } else { // 已出账单为上月账单
            outStmtBillDate = DateFormatUtil.mergeDate(DateFormatUtil.addMonth(currDate, -1),
                    Integer.parseInt(billDay));
        }
        paramMap.put("stmtDate",DateFormatUtil.dateToString(outStmtBillDate));
        
        List<StatementEntity> statements = billRepository.billRepository(paramMap);
        if(statements == null || statements.size() == 0){
            return false;//无分期
            //throw new RuntimeException("当前无帐单.");
        }else if(statements.size() == 1 && "S01".equals(statements.get(0).getStmtStatus())){
            return false;//无分期
        }else if(statements.size() == 2){
            return true;//有分期
        }else{
            throw  new RuntimeException("帐单数据有误");
        }
        
    }

}
