package com.apass.esp.web.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.bill.StatementEntity;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.service.bill.BillService;
import com.apass.esp.service.bill.TransactionService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.utils.HttpWebUtils;

@Controller
@RequestMapping(value = "/application/transaction/txninfo")
public class TransactionController extends BaseController {
    private static final String CREDIT_TRANSACTION_URL = "bill/transList";

    @Autowired
    private TransactionService transactionService;

    /**
     * 商户信息初始化
     */
    @RequestMapping("/page")
    public String merchantInforPage(Map<String, Object> paramMap) {
        return CREDIT_TRANSACTION_URL;
    }

    /**
     * 商户信息查询(分页)
     */
    @ResponseBody
    @RequestMapping("/query")
    public ResponsePageBody<TxnInfoEntity> queryTransactionInforPage(HttpServletRequest request) {
        ResponsePageBody<TxnInfoEntity> respBody = new ResponsePageBody<TxnInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = Integer.valueOf(pageNo);
            Integer pageSizeNum = Integer.valueOf(pageSize);
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);

            String userId = HttpWebUtils.getValue(request, "userId");
            String txnDateBoot = HttpWebUtils.getValue(request, "txnDateBoot");
            String txnDateTop = HttpWebUtils.getValue(request, "txnDateTop");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userId);
            map.put("txnDateBoot", txnDateBoot);
            map.put("txnDateTop", txnDateTop);

            Pagination<TxnInfoEntity> resultPage = transactionService.queryTransactionInforPage(map, page);
            if (resultPage == null) {
                respBody.setTotal(0);
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }
            
            respBody.setTotal(resultPage.getTotalCount());
            respBody.setRows(resultPage.getDataList());
            respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
        } catch (Exception e) {
            logger.error("帐单信息查询失败", e);
            respBody.setStatus("0");
            respBody.setMsg("帐单信息查询失败");
        }
        return respBody;
    }

}
