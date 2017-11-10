package com.apass.esp.repository.cashRefund;


import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.TxnOrderInfoForBss;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.dto.payment.PayResponseDto;
import com.apass.esp.domain.entity.bill.SapData;
import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.enums.YesNo;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CashRefundHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CashRefundHttpClient.class);

    @Value("${bbs.request.address}")
    public String bbsReqUrl;
    //退款地址
    private static final String APASS_CASHREFUND_COLLER = "/apassRefund/refundCash";

    //sapData接口
    private static final String VBS_BSS_URL = "/queryForEsp/querySapData";


    /**
     * 调用BSS退款接口
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public Response refundCash(RefundCashRequest request) {

        try {
            String address = bbsReqUrl + APASS_CASHREFUND_COLLER;
            String requestJson = GsonUtils.toJson(request);

            LOGGER.info("payment_defary_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("payment_defary_repJson:{}", responseJson);

            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            return Response.successResponse(resp);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            return Response.fail("bss退款接口调用异常");
        } catch (Exception e) {
            LOGGER.error("cashRefund_error接口调用异常:{}", e);
            return Response.fail("bss退款接口调用异常");
        }
    }

    /**
     * 消费分期相关的电商订单orderId集合:form表单形式
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public SapData querySapData(TxnOrderInfoForBss request) throws BusinessException {

        try {
            String address = bbsReqUrl + VBS_BSS_URL;

            ArrayList<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("txnId", request.getTxnId().toString()));
            list.add(new BasicNameValuePair("userId", request.getUserId().toString()));
            list.add(new BasicNameValuePair("vbsId", request.getVbsId().toString()));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, formEntity);

            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            return Response.resolveResult(resp,SapData.class);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            throw new BusinessException("消费分期相关的电商订单orderId集合异常", e);
        } catch (Exception e) {
            LOGGER.error("querySapData_error接口调用异常:{}", e);
            throw new BusinessException("消费分期相关的电商订单orderId集合异常", e);
        }
    }


}
