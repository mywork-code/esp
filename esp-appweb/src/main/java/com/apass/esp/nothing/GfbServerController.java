package com.apass.esp.nothing;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.repository.repaySchedule.RepayScheduleRepository;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;

import net.sf.json.JSONObject;
/**
 * 当账单期数为一时（7天），gfb后台要显示的VBS出账日需要调用esp中 
 * 表 t_apass_repay_schedule中的到期还款日期（loan_pmt_due_date）
 * @author zengqingshan
 */
@Path("/gfbSignLoan")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GfbServerController {
	    private static final Logger LOGGER = LoggerFactory.getLogger(GfbServerController.class);
	    @Autowired
	    private RepayScheduleRepository    repayScheduleRepository;

	    @POST
	    @Path("/query")
	    public Response webHook(String param) {
	    	try {
	    	    String gfbEspSignloan = LogStashKey.GFB_ESP_SIGNLOAN.getValue();
	    	    String gfbEspSignloanDesc = LogStashKey.GFB_ESP_SIGNLOAN.getName();
	    	      
		    	JSONObject jsonObj = JSONObject.fromObject(param);
		        String vbsBid = jsonObj.getString("vbsBid");
		        
		        String requestId = gfbEspSignloan + "_" + vbsBid; 
		        LOG.info(requestId, gfbEspSignloanDesc, GsonUtils.toJson(param));
		        
		        String loanPmtDueDate= repayScheduleRepository.selectByVbsid(Long.parseLong(vbsBid));
		        
		        LOG.info(requestId, loanPmtDueDate, GsonUtils.toJson(param));
		        
		        Map<String, Object> resultMap = new HashMap<String, Object>();
		        if(StringUtils.isNotBlank(loanPmtDueDate)){
		        	resultMap.put("loanPmtDueDate", loanPmtDueDate);
		        	return Response.successResponse(resultMap);
		        }
		        return Response.fail("通过vbsid查询VBS账单日失败!");
			} catch (Exception e) {
				LOGGER.error("通过vbsid查询VBS账单日失败", e);
				return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
			}
	    }
}
