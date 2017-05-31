package com.apass.esp.web.contract;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.contract.BuySellContractDTO;
import com.apass.esp.service.contract.ContractService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/contract")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AppContractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppContractController.class);

	@Autowired
	public ContractService contractService;

	/**
	 * 查询赊销合同数据
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/queryCreditContract")
	public Response queryCreditContract(Map<String, Object> paramMap){
		//订单ID字符串
		String orderIdStr = CommonUtils.getValue(paramMap, "orderIdStr");
		String[] orderIdArray=orderIdStr.split(",");
		if(orderIdArray==null||orderIdArray.length<=0){
			LOGGER.error("订单ID信息不能为空");
			return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
		}

		try{
			BuySellContractDTO bsc=contractService.getContractParamModelByOrderIdList(orderIdArray);
			return Response.successResponse(bsc);
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorDesc(),e);
			return Response.fail(e.getErrorDesc());
		}catch(Exception e){
			LOGGER.error("查询合同信息失败",e);
			return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
		}

	}
}

