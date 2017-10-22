package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.entity.aftersale.AfsApply;
import com.apass.esp.third.party.jd.entity.aftersale.SendSku;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PUT;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdAfterSaleApiClient  extends JdApiClient{
    /**
     * 申请服务
     * @param afsApply
     * @return
     */
    public JdApiResponse<Integer> afterSaleAfsApplyCreate(AfsApply afsApply) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", afsApply.getJdOrderId());
        jsonObject.put("customerExpect", afsApply.getCustomerExpect());
        jsonObject.put("questionDesc", afsApply.getQuestionDesc());
        jsonObject.put("isNeedDetectionReport", afsApply.getIsNeedDetectionReport());
        jsonObject.put("questionPic", afsApply.getQuestionPic());
        jsonObject.put("questionPic",afsApply.getQuestionPic());
        jsonObject.put("isHasPackage", afsApply.getIsHasPackage());
        jsonObject.put("packageDesc", afsApply.getPackageDesc());
        jsonObject.put("asCustomerDto", afsApply.getAsCustomerDtok());
        jsonObject.put("asPickwareDto", afsApply.getAsPickwareDtok());
        jsonObject.put("asReturnwareDto", afsApply.getAsReturnwareDtok());
        jsonObject.put("asDetailDto", afsApply.getAsDetailDtok());
        return requestWithParam("biz.afterSale.afsApply.create", jsonObject, "biz_afterSale_afsApply_create_response", Integer.class);
    }

    /**
     * 客户发运信息
     * @param sendSku
     * @return
     */
    public JdApiResponse<Integer> afterSaleSendSkuUpdate(SendSku sendSku) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("afsServiceId", sendSku.getAfsServiceId());
        jsonObject.put("freightMoney", sendSku.getFreightMoney());
        jsonObject.put("expressCompany", sendSku.getExpressCompany());
        jsonObject.put("deliverDate", sendSku.getDeliverDate());
        jsonObject.put("expressCode", sendSku.getExpressCode());
        return requestWithParam("biz.afterSale.sendSku.update", jsonObject, "biz_afterSale_sendSku_update_response", Integer.class);
    }

    /**
     * 查询商品是否可以提交售后
     * @param jdOrderId
     * @param skuId
     * @return
     */
    public JdApiResponse<Integer> afterSaleAvailableNumberCompQuery(Long jdOrderId,Long skuId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        jsonObject.put("skuId", skuId);
        return requestWithParam("biz.afterSale.availableNumberComp.query", jsonObject, "biz_afterSale_availableNumberComp_query_response", Integer.class);
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @param jdOrderId
     * @param skuId
     * @return
     */
    public JdApiResponse<JSONArray> afterSaleCustomerExpectCompQuery(Long jdOrderId, Long skuId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        jsonObject.put("skuId", skuId);
        return requestWithParam("biz.afterSale.customerExpectComp.query", jsonObject, "biz_afterSale_customerExpectComp_query_response", JSONArray.class);
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回京东方式
     * @param jdOrderId
     * @param skuId
     * @return
     */
    public JdApiResponse<JSONArray> afterSaleWareReturnJdCompQuery(Long jdOrderId,Long skuId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        jsonObject.put("skuId", skuId);
        return requestWithParam("biz.afterSale.wareReturnJdComp.query", jsonObject, "biz_afterSale_wareReturnJdComp_query_response", JSONArray.class);
    }

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @param jdOrderId
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public JdApiResponse<JSONObject> afterSaleServiceListPageQuery(long jdOrderId,int pageIndex,int pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        jsonObject.put("pageSize", pageSize);
        jsonObject.put("pageIndex", pageIndex);
        return requestWithParam("biz.afterSale.serviceListPage.query", jsonObject, "biz_afterSale_serviceListPage_query_response", JSONObject.class);
    }

    /**
     * 查询用户对应的商品池编号
     * @param afsServiceId
     * @param appendInfoSteps
     * @return
     */
    public JdApiResponse<JSONObject> afterSaleServiceDetailInfoQuery(Long afsServiceId,List<Integer> appendInfoSteps) {
        if(appendInfoSteps == null){
            appendInfoSteps = new ArrayList<>(0);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("afsServiceId", afsServiceId);
        jsonObject.put("appendInfoSteps", appendInfoSteps);
        return requestWithParam("biz.afterSale.serviceDetailInfo.query", jsonObject, "biz_afterSale_serviceDetailInfo_query_response", JSONObject.class);
    }

    /**
     * 取消服务单
     * @param serviceIdList
     * @param approveNotes
     * @return
     */
    public JdApiResponse<JSONObject> afterSaleAuditCancelQuery(ArrayList<Integer> serviceIdList,String approveNotes) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serviceIdList", serviceIdList);
        jsonObject.put("approveNotes", approveNotes);
        return requestWithParam("biz.afterSale.auditCancel.query", jsonObject, "biz_afterSale_auditCancel_query_response", JSONObject.class);
    }
}
