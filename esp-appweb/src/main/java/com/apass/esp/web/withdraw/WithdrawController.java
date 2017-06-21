package com.apass.esp.web.withdraw;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.repository.httpClient.EspActivityHttpClient;
import com.apass.esp.service.coffers.CoffersBaseService;
import com.apass.esp.service.withdraw.WithdrawService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.BaseConstants.ParamsCode;
import com.google.common.collect.Maps;

/**
 * @description 我的金库页面
 *
 * @author xiaohai
 * @version $Id: CoffersBaseController.java, v 0.1 2017年6月6日 下午2:58:25 xiaohai Exp $
 */
@Controller
@RequestMapping("/withdraw/deposit")
public class WithdrawController {
    private static final Logger LOGGER=LoggerFactory.getLogger(WithdrawController.class);

    @Autowired
    private WithdrawService withdrawService;
	
	@Autowired
    private EspActivityHttpClient espActivityHttpClient;
	/**
	 * 提现页面查询接口
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Response queryWithdraw(@RequestBody Map<String, Object> paramMap) {
	    Map<String,Object> resultMap = Maps.newHashMap();
	    try{
	        String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
	        if(StringUtils.isBlank(userId)){
	        	LOGGER.error("参数有误");
	            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
	        }
	        
	        resultMap = withdrawService.queryWithdrawByUserId(userId);

	        return Response.success("提现页面查询成功", resultMap);
	    }catch(Exception e){
	        LOGGER.error(e.getMessage(),e);
	        return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
	    }
	}
	
	/**
	 * 确认提现接口
	 * @param paramMap
	 * @return
	 */
	@RequestMapping("/confirmWithdraw")
	@ResponseBody
	public Response confirmWithdraw(@RequestBody Map<String, Object> paramMap) {
	    Map<String,Object> resultMap = Maps.newHashMap();
	    String userId = CommonUtils.getValue(paramMap, ParamsCode.USER_ID);
        String amount = CommonUtils.getValue(paramMap, "amount");
        String cardBank = CommonUtils.getValue(paramMap, "cardBank");
        String cardNo = CommonUtils.getValue(paramMap, "cardNo");
            
	    try{
	        if(StringUtils.isAnyBlank(userId,amount,cardBank,cardNo)){
	        	LOGGER.error("参数有误");
	            return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
	        }
	        LOGGER.info("确认提现传参内容userId:{},amount:{},cardBank:{},cardNo:{}",userId,amount,cardBank,cardNo);
	        
	        resultMap = withdrawService.confirmWithdraw(userId,amount,cardBank,cardNo);
	        String customerId = (String)resultMap.get("customerId");
	        //调用供房帮接口：银行卡用过后不可更换
	        Map<String,Object> parMap = Maps.newHashMap();
	        parMap.put("customerId",customerId);
	        //提现成功后调用供房帮接口标记银行卡已提现
	        espActivityHttpClient.userCardFlag(parMap);	
			
	        return Response.success("提现成功！", resultMap);
	    }catch(Exception e){
	        LOGGER.error(e.getMessage(),e);
	        return Response.fail(BusinessErrorCode.EDIT_INFO_FAILED);
	    }
	}


}
