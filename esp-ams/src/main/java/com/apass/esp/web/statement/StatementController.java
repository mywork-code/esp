package com.apass.esp.web.statement;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.dto.statement.StatementDto;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.service.statement.StatementService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/application/merchant/statement")
public class StatementController extends BaseController {
    private static final String CREDIT_STATEMENT_URL = "statement/statementList";

    @Autowired
    private StatementService    statementService;

    /**
     * 报表信息初始化
     */
    @RequestMapping("/page")
    public ModelAndView statementPage() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String merchantCode = listeningCustomSecurityUserDetails.getMerchantCode();
            map.put("userMerchantCode", merchantCode);
        } catch (Exception e) {
            logger.error("报表信息初始化异常", e);
        }
        logger.info("报表信息初始化成功...");
        return new ModelAndView(CREDIT_STATEMENT_URL, map);
    }

    /**
     * 报表信息查询(分页)
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<StatementDto> queryStatementPage(HttpServletRequest request) {
        ResponsePageBody<StatementDto> respBody = new ResponsePageBody<StatementDto>();
        try{
            Page page = getPageParam(request);
            Map<String, Object> mapParam = encapMethod(request);

            Pagination<StatementDto> resultPage = statementService.queryStatementShowPage(mapParam, page);
            if (resultPage == null) {
                respBody.setTotal(0);
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }

            List<StatementDto> dataList = resultPage.getDataList();
            for (StatementDto statementDto : dataList) {
                String signTime = statementDto.getSignTime();
                Date signTimeDate = DateFormatUtil.string2date(signTime, DateFormatUtil.YYYY_MM_DD);
                if(signTimeDate != null){
                    Date signTimeDateAddDays = DateFormatUtil.addDays(signTimeDate, FINALLY_SETTLETIME);
                    statementDto.setSettlementTime(signTimeDateAddDays);
                }
            }

            respBody.setTotal(resultPage.getTotalCount());
            respBody.setRows(resultPage.getDataList());
            respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
        }catch(Exception e){
            logger.error("用户报表查询失败", e);
            respBody.setMsg("用户报表查询失败"); 
        }

        return respBody;
    }

    //获取分页数据
    private Page getPageParam(HttpServletRequest request) {
        // 获取分页数据
        String pageNo = HttpWebUtils.getValue(request, "page");
        String pageSize = HttpWebUtils.getValue(request, "rows");
        Integer pageNoNum = Integer.parseInt(pageNo);
        Integer pageSizeNum = Integer.parseInt(pageSize);
        Page page = new Page();
        if (!StringUtils.isAnyBlank(pageNo, pageSize)) {
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);
        }
        return page;
    }

    /**
     * 封装参数到Map
     * 
     * @throws ParseException
     */
    private Map<String, Object> encapMethod(HttpServletRequest request) {
        String createDate1 = HttpWebUtils.getValue(request, "createDate1");
        String createDate2 = HttpWebUtils.getValue(request, "createDate2");
        String customerName = HttpWebUtils.getValue(request, "customerName");
        String goodsNames = HttpWebUtils.getValue(request, "goodsNames");
        String orderId = HttpWebUtils.getValue(request, "orderId");
        String date1 = HttpWebUtils.getValue(request, "date1");
        String date2 = HttpWebUtils.getValue(request, "date2");
        String payType = HttpWebUtils.getValue(request, "payType");
        String refundType = HttpWebUtils.getValue(request, "refundType");
        String merchantCode = HttpWebUtils.getValue(request, "merchantCode");

        Map<String, Object> mapParam = new HashMap<String, Object>();
        // 封装参数到Map中
        if (StringUtils.isNotBlank(createDate1)) {
            mapParam.put("createDate1", createDate1);
        }
        if (StringUtils.isNotBlank(createDate2)) {
            mapParam.put("createDate2", createDate2);
        }
        if (StringUtils.isNotBlank(customerName)) {
            mapParam.put("customerName", customerName);
        }
        if (StringUtils.isNotBlank(goodsNames)) {
            mapParam.put("goodsNames", goodsNames);
        }
        if (StringUtils.isNotBlank(orderId)) {
            mapParam.put("orderId", orderId);
        }
        if (StringUtils.isNotBlank(date1)) {
            mapParam.put("date1", date1);
        }
        if (StringUtils.isNotBlank(date2)) {
            mapParam.put("date2", date2);
        }
        if (StringUtils.isNotBlank(payType)) {
            mapParam.put("payType", payType);
        }
        if (StringUtils.isNotBlank(refundType)) {
            mapParam.put("refundType", refundType);
        }
        if (StringUtils.isNotBlank(merchantCode)) {
            mapParam.put("merchantCode", merchantCode);
        }

        return mapParam;
    }

}
