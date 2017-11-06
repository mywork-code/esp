package com.apass.esp.service.bill;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description 客户信息获取
 *
 * @author chenbo
 * @version $Id: CustomerService.java, v 0.1 2017年3月3日 下午3:16:06 chenbo Exp $
 */
@Component
public class CustomerServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceClient.class);

	/**
	 * 供房包服务地址
	 */
	@Value("${gfb.service.url}")
	private String gfbServiceUrl;

	private static final String SUBCREDITREQURL="/interfaceForOrder/reduce/limit";
	
	private static final String GETCUSTOMERREQURL="/interfaceForOrder/getCustomerInfo";
	
    private static final String CREDITPAYAUTH="/pay/creditpay/available";
    
    private static final String SAVEAGREEMENT="/interfaceForOrder/saveContract";
	/**
	 * 获取客户信息
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
    public CustomerInfo getCustomerInfo(Long userId) throws BusinessException {
        try {
            Map<String, Object> request = new HashMap<String, Object>();
            request.put("userId", userId);
            String requestUrl = gfbServiceUrl + GETCUSTOMERREQURL;
            LOGGER.info("获取客户信息::RequestUrl::[{}]", requestUrl);
            String requestJson = GsonUtils.toJson(request);
            LOGGER.info("获取客户信息::Request::[{}]", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            LOGGER.info("获取客户信息::Response::[{}]", responseJson);
            Response response = GsonUtils.convertObj(responseJson, Response.class);
            return resolveResult(response, CustomerInfo.class);
        } catch (Exception e) {
            LOGGER.error("调用gfb客户信息查询服务异常:[{}]", e);
            throw new BusinessException("调用gfb客户信息查询服务异常", e);
        }
    }

    public <T> T resolveResult(Response response, Class<T> cls) throws BusinessException {
        if (response == null) {
            throw new BusinessException("接口请求异常稍后再试");
        }
        if ("1".equals(response.getStatus())) {
            String respJson = response.getData().toString();
            return GsonUtils.convertObj(respJson, cls);
        } else {
            throw new BusinessException(response.getMsg());
        }
    }
	/**
	 * 修改客户可用额度  
	 * 
	 * @param paramMap
	 * @param logFlag  日志标记
	 * @return
	 * @throws BusinessException
	 */
	public Response subtractCredit(Map<String,Object> paramMap, String logFlag) throws BusinessException{
        try {
            String requestUrl = gfbServiceUrl + SUBCREDITREQURL;
            LOG.info(logFlag, "额度操作请求地址", requestUrl);
            String requestJson = GsonUtils.toJson(paramMap);
            LOG.logstashRequest(logFlag, "额度操作", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
            // LOGGER.info("获取客户信息::Response::[{}]", responseJson);
            LOG.logstashResponse(logFlag, "额度操作", responseJson);
            return GsonUtils.convertObj(responseJson, Response.class);
        } catch (Exception e) {
            LOGGER.error("调用gfb客户信息查询服务异常:[{}]", e.getMessage());
            throw new BusinessException("调用gfb客户信息查询服务异常", e);
        }
    }
	

	/**
	 * 调用GFB 额度支付权限查询
	 * 
	 * @param paramMap
	 * @return 1:可以信用支付    0:不能信用支付
	 * @throws BusinessException
	 */
    public Integer creditPaymentAuth(Map<String, Object> paramMap) throws BusinessException {
        try {
            String address = gfbServiceUrl + CREDITPAYAUTH;
            String requestJson = GsonUtils.toJson(paramMap);

            LOGGER.info("payment_creditPaymentAuth_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_creditPaymentAuth_repJson:{}", responseJson);
            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            HashMap<String,String> result = resolveResult(resp, HashMap.class);
            return Integer.valueOf(result.get("available"));
        } catch (Exception e) {
            throw new BusinessException("额度支付权限查询异常", e);
        }
    }
    
    /**
     * 保存协议合同
     * 
     * @param paramMap
     * @return
     * @throws BusinessException
     */
    public void saveAgreementInfo(Map<String, Object> paramMap) throws BusinessException {
        try {
            String address = gfbServiceUrl + SAVEAGREEMENT;
            String requestJson = GsonUtils.toJson(paramMap);

            LOGGER.info("payment_saveAgreementInfo_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_saveAgreementInfo_repJson:{}", responseJson);
        } catch (Exception e) {
            throw new BusinessException("保存协议合同异常", e);
        }
    }
}
