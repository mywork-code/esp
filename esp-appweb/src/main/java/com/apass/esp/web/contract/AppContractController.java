package com.apass.esp.web.contract;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.contract.BuySellContractDTO;
import com.apass.esp.service.contract.ContractService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;

import org.apache.commons.lang3.StringUtils;
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
			return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
		}catch(Exception e){
			LOGGER.error("查询合同信息失败",e);
			return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
		}

	}
	/**
	 * 查询赊销合同数据
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/queryCreditContract2")
	public Response queryCreditContract2(Map<String, Object> paramMap){
		//信贷请求数据（查询实物分期合同数据）
		// 用户id
		String userId = CommonUtils.getValue(paramMap, "userId");
		// 本金
		String capital = CommonUtils.getValue(paramMap, "capital");
		// 分期期数
		String paymentType = CommonUtils.getValue(paramMap, "paymentType");

		if (StringUtils.isBlank(userId)) {
			return Response.fail("用户id不能为空");
		}
		if (StringUtils.isBlank(capital)) {
			return Response.fail("分期金额不能为空");
		}
		if (StringUtils.isBlank(paymentType)) {
			return Response.fail("分期期限不能为空");
		}
		//电商请求数据
		//订单ID字符串
		String orderIdStr = CommonUtils.getValue(paramMap, "orderIdStr");
		String[] orderIdArray=orderIdStr.split(",");
		if(orderIdArray==null||orderIdArray.length<=0){
			LOGGER.error("订单ID信息不能为空");
			return Response.fail(BusinessErrorCode.PARAM_IS_EMPTY);
		}
		Map<String, Object> resultMap = null;
		try{
			Response result=contractService.querycashstagescontract(userId, capital, paymentType);
			if(null !=result && result.statusResult()){
				resultMap = (Map<String, Object>) result.getData();
				LOGGER.info("信贷请求数据 result.data="+(Map<String, Object>) result.getData());
				if(null !=resultMap){
					BuySellContractDTO bsc=contractService.getContractParamModelByOrderIdList(orderIdArray);
					if(null !=bsc){
						resultMap.put("realName",bsc.getRealName());
						resultMap.put("identityNo ",bsc.getIdentityNo());
						resultMap.put("mobile",bsc.getMobile());
						resultMap.put("payBankCardNo",bsc.getPayBankCardNo());
						resultMap.put("contractNo",bsc.getContractNo());
						resultMap.put("payBankName",bsc.getPayBankName());
						resultMap.put("productList",bsc.getProductList());
						resultMap.put("repaymentDate",bsc.getRepaymentDate());
						resultMap.put("feeAmount", bsc.getFeeAmount());
						resultMap.put("payStartYear",bsc.getPayStartYear());
						resultMap.put("payStartMonth",bsc.getPayStartMonth());
						resultMap.put("payStartDay",bsc.getPayStartDay());
						resultMap.put("payEndYear",bsc.getPayEndYear());
						resultMap.put("payEndMonth",bsc.getPayEndMonth());
						resultMap.put("payEndDay",bsc.getPayEndDay());
					}
				}
			}
			return Response.successResponse(resultMap);
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorDesc(),e);
			return Response.fail(e.getErrorDesc(),e.getBusinessErrorCode());
		}catch(Exception e){
			LOGGER.error("查询合同信息失败",e);
			return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
		}

	}
}

