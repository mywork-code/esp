package com.apass.esp.repository.cashRefund;


import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.payment.PayRequestDto;
import com.apass.esp.domain.dto.payment.PayResponseDto;
import com.apass.esp.domain.entity.bill.SapData;
import com.apass.esp.domain.entity.bill.TxnOrderInfo;
import com.apass.esp.domain.enums.YesNo;
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
     * 消费分期相关的电商订单orderId集合
     *
     * @param payReq
     * @return
     * @throws BusinessException
     */
    public SapData querySapData(TxnOrderInfo request) throws BusinessException {

        try {
            String address = bbsReqUrl + VBS_BSS_URL;
            String requestJson = GsonUtils.toJson(request);

            LOGGER.info("sap_getData_reqJson:{}", requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String responseJson = HttpClientUtils.getMethodPostResponse(address, entity);
            LOGGER.info("sap_getData_repJson:{}", responseJson);

            Response resp = GsonUtils.convertObj(responseJson, Response.class);
            return Response.resolveResult(resp,SapData.class);
        } catch (BusinessException e) {
            LOGGER.error(e.getErrorDesc(), e);
            throw new BusinessException("bss退款接口调用异常", e);
        } catch (Exception e) {
            LOGGER.error("querySapData_error接口调用异常:{}", e);
            throw new BusinessException("bss退款接口调用异常", e);
        }
    }



}
