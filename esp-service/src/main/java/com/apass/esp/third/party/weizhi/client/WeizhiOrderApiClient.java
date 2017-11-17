package com.apass.esp.third.party.weizhi.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.service.wz.WeiZhiTokenService;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.weizhi.entity.AddressInfo;
import com.apass.esp.third.party.weizhi.entity.OrderReq;
import com.apass.esp.third.party.weizhi.entity.SkuNum;
import com.google.common.collect.Maps;
@Service
public class WeizhiOrderApiClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeizhiOrderApiClient.class);
	
	@Autowired
	private WeiZhiTokenService weiZhiTokenService;
	
	/**
     * 统一下单接口
     *
     * @param orderReq
     * @return
	 * @throws Exception 
     */
    public Map<String,Object> orderUniteSubmit(OrderReq orderReq) throws Exception {
    	
        Objects.requireNonNull(orderReq.getOrderPriceSnap());
        if (orderReq.getSkuNumList().size() > 50) {
            throw new RuntimeException("最大数量为50，当前:" + orderReq.getSkuNumList().size());
        }
        JSONObject jsonObject = new JSONObject(true);
        
        jsonObject.put("token", weiZhiTokenService.getTokenFromRedis());
        jsonObject.put("thirdOrder", orderReq.getOrderNo());
        List<Map<String, Object>> skuNums = new ArrayList<>();
        for (SkuNum skuNum : orderReq.getSkuNumList()) {
            Map<String, Object> skuNumMap = new HashMap<>(2);
            skuNumMap.put("skuId", skuNum.getSkuId());
            skuNumMap.put("num", skuNum.getNum());
            skuNumMap.put("bNeedAnnex", true);//表示是否需要附件，默认每个订单都给附件，如果为false,不会给客户发附件 TODO
            skuNumMap.put("bNeedGift", false);//表示是否需要赠品 TODO
            skuNums.add(skuNumMap);
        }
        jsonObject.put("sku", skuNums);
        AddressInfo addressInfo = orderReq.getAddressInfo();
        jsonObject.put("name", addressInfo.getReceiver());
        jsonObject.put("province", addressInfo.getProvinceId());
        jsonObject.put("city", addressInfo.getCityId());
        jsonObject.put("county", addressInfo.getCountyId());
        jsonObject.put("town", addressInfo.getTownId());
        jsonObject.put("address", addressInfo.getAddress());
        jsonObject.put("zip", "");
        jsonObject.put("phone", addressInfo.getPhone());
        jsonObject.put("mobile", addressInfo.getMobile());
        jsonObject.put("email", addressInfo.getEmail());
        jsonObject.put("remark", orderReq.getRemark());
        /*
		        下单价格模式
			0:客户端订单价格快照不做验证对比，还是以京东端价格正常下单;
			1:必需验证客户端订单价格快照，如果快照与京东端价格不一致返回下单失败，需要更新商品价格后，重新下单;
         */
        jsonObject.put("orderPriceSnap", JSON.toJSONString(orderReq.getOrderPriceSnap()));

        jsonObject.put("extContent", "");
        //JdApiResponse<JSONObject> response = request("biz.order.unite.submit", jsonObject, "biz_order_unite_submit_response", JSONObject.class);
        
        
        Map<String,Object> returns = Maps.newHashMap();
        
        return returns;
    }
    
    
    /**
     * 根据京东订单号，确认预占库存
     *
     * @param jdOrderId
     * @return
     */
    public JdApiResponse<Boolean> orderOccupyStockConfirm(long jdOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        return null;//request("biz.order.occupyStock.confirm", jsonObject, "biz_order_occupyStock_confirm_response", Boolean.class);
    }
    
    /**
     * 根据京东订单号，查询订单明细
     *
     * @param jdOrderId
     * @return
     */
    public JdApiResponse<JSONObject> orderJdOrderQuery(long jdOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        return null;//request("biz.order.jdOrder.query", jsonObject, "biz_order_jdOrder_query_response", JSONObject.class);
    }
    
    /**
     * 根据第三方订单号，获取京东订单号
     *
     * @param thridOrderId
     * @return
     */
    public JdApiResponse<JSONObject> orderJdOrderIDByThridOrderIDQuery(String thridOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("thirdOrder", thridOrderId);
        return null;//request("biz.order.jdOrderIDByThridOrderID.query", jsonObject, "biz_order_jdOrderIDByThridOrderID_query_response", JSONObject.class);
    }
    
    /**
     * 根据京东订单号，查询配送信息
     *
     * @param jdOrderId
     * @return
     */
    public JdApiResponse<JSONObject> orderOrderTrackQuery(long jdOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        return null;//request("biz.order.orderTrack.query", jsonObject, "biz_order_orderTrack_query_response", JSONObject.class);
    }
}
