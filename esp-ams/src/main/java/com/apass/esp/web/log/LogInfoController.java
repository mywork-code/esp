package com.apass.esp.web.log;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.log.LogInfoEntity;
import com.apass.esp.domain.enums.LogAttrEnums;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.service.log.LogAttrService;
import com.apass.esp.service.log.LogService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.utils.HttpWebUtils;
/**
 * 
 * @author 日志操作controller
 *
 */
@Controller
@RequestMapping(value = "/application/loginfo/log")
public class LogInfoController extends BaseController  {
    /**
     * 日志
     */
    private static final Logger  logger  = LoggerFactory.getLogger(LogInfoController.class);
    private static final String  loginfo_url = "log/logInforList";
    
    @Autowired
    private LogService logService;
    
    @Autowired
    private LogAttrService logAttrService;
    
    /**
     * 商户信息查询(分页)
     */
    @ResponseBody
    @RequestMapping("/query")
    public ResponsePageBody<LogInfoEntity> queryMerchantInforPage(HttpServletRequest request) {
        ResponsePageBody<LogInfoEntity> respBody = new ResponsePageBody<LogInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = null;
            Integer pageSizeNum = null;
            try {
            	pageNoNum = Integer.valueOf(pageNo);
            	pageSizeNum = Integer.valueOf(pageSize);
			} catch (Exception e) {
				pageNoNum = 1 ;
				pageSizeNum = 1;
			}
            
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("operationType", LogAttrEnums.LOG.getCode());

            Pagination<LogInfoEntity> resultPage = logService.getLogForPageByCondition(map, page);
            if (resultPage == null) {
                respBody.setTotal(0);
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }
            
            for (LogInfoEntity log : resultPage.getDataList()) {
				log.setContent(logAttrService.getContent(log.getId()+""));
			}
            respBody.setTotal(resultPage.getTotalCount());
            respBody.setRows(resultPage.getDataList());
            respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
        } catch (Exception e) {
            logger.error("日志信息查询失败", e);
            respBody.setStatus("0");
            respBody.setMsg("日志信息查询失败");
        }
        return respBody;
    }
    
}
