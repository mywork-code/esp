package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.entity.person.AddressInfo;
import com.apass.esp.third.party.jd.entity.order.OrderReq;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdOrderApiClient extends JdApiClient {
    /**
     * 统一下单接口
     *
     * @param orderReq
     * @return
     */
    public JdApiResponse<JSONObject> orderUniteSubmit(OrderReq orderReq) {
        Objects.requireNonNull(orderReq.getOrderPriceSnap());
        if (orderReq.getSkuNumList().size() > 50) {
            throw new RuntimeException("最大数量为50，当前:" + orderReq.getSkuNumList().size());
        }
        if (StringUtils.isEmpty(orderReq.getOrderNo())) {
            //orderReq.setOrderNo(NoGenerator.ORDER.make());
            orderReq.setOrderNo("1111");
        }
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("thirdOrder", orderReq.getOrderNo());
        List<Map<String, Object>> skuNums = new ArrayList<>();
        for (SkuNum skuNum : orderReq.getSkuNumList()) {
            Map<String, Object> skuNumMap = new HashMap<>(2);
            skuNumMap.put("skuId", skuNum.getSkuId());
            skuNumMap.put("num", skuNum.getNum());
            skuNumMap.put("bNeedAnnex", true);
            skuNumMap.put("bNeedGift", false);
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
        jsonObject.put("invoiceState", 2);//开票方式(1为随货开票，0为订单预借，2为集中开票 )
        jsonObject.put("invoiceType", 2);//1普通发票2增值税发票
        jsonObject.put("selectedInvoiceTitle", 5);//4个人，5单位
        jsonObject.put("companyName", "上海奥派数据科技有限公司");
        jsonObject.put("invoiceContent", 1);//1:明细，3：电脑配件，19:耗材，22：办公用品  备注:若增值发票则只能选1 明细
        jsonObject.put("paymentType", 4);//1：货到付款，2：邮局付款，4：在线支付（余额支付），5：公司转账，6：银行转账，7：网银钱包， 101：金采支付
        jsonObject.put("isUseBalance", 1);//预存款【即在线支付（余额支付）】下单固定1 使用余额 非预存款下单固定0 不使用余额

        jsonObject.put("submitState", 0);//是否预占库存，0是预占库存（需要调用确认订单接口），1是不预占库存
        jsonObject.put("invoiceName", "");
        jsonObject.put("invoicePhone", "");
        jsonObject.put("invoiceProvice", "");
        jsonObject.put("invoiceCity", "");
        jsonObject.put("invoiceCounty", "");
        jsonObject.put("invoiceAddress", "");
        /*
        下单价格模式
0: 客户端订单价格快照不做验证对比，还是以京东端价格正常下单;
1:必需验证客户端订单价格快照，如果快照与京东端价格不一致返回下单失败，需要更新商品价格后，重新下单;
         */
        jsonObject.put("doOrderPriceMode", 1);
        jsonObject.put("orderPriceSnap", JSON.toJSONString(orderReq.getOrderPriceSnap()));

//        jsonObject.put("doOrderPriceMode", 0);
//        jsonObject.put("orderPriceSnap", "");
        jsonObject.put("extContent", "");
        JdApiResponse<JSONObject> response = request("biz.order.unite.submit", jsonObject, "biz_order_unite_submit_response", JSONObject.class);
        return response;
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
        return request("biz.order.occupyStock.confirm", jsonObject, "biz_order_occupyStock_confirm_response", Boolean.class);
    }

    /**
     * 根据京东订单号，发起支付请求
     *
     * @param jdOrderId
     * @return
     */
    public JdApiResponse<Boolean> orderDoPay(long jdOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        return request("biz.order.doPay", jsonObject, "biz_order_doPay_response", Boolean.class);
    }

    /**
     * 根据京东订单号，取消未确认订单
     *
     * @param jdOrderId
     * @return
     */
    public JdApiResponse<Boolean> orderCancelorder(long jdOrderId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jdOrderId", jdOrderId);
        return request("biz.order.cancelorder", jsonObject, "biz_order_cancelorder_response", Boolean.class);
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
        return request("biz.order.jdOrder.query", jsonObject, "biz_order_jdOrder_query_response", JSONObject.class);
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
        return request("biz.order.jdOrderIDByThridOrderID.query", jsonObject, "biz_order_jdOrderIDByThridOrderID_query_response", JSONObject.class);
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
        return request("biz.order.orderTrack.query", jsonObject, "biz_order_orderTrack_query_response", JSONObject.class);
    }

    //对账api

    /**
     * 根据日期和页码，查询新建订单信息
     *
     * @param date 日期（格式：yyyy-MM-dd） 说明：目前仅支持查前一天，不能查当天
     * @param page 首次查询，填1
     * @return
     */
    public JdApiResponse<JSONObject> orderCheckNewOrderQuery(String date, Integer page) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        jsonObject.put("page", page);
        return request("biz.order.checkNewOrder.query", jsonObject, "biz_order_checkNewOrder_query_response", JSONObject.class);
    }

    /**
     * 根据日期和页码，查询妥投订单信息
     *
     * @param date
     * @param page
     * @return
     */
    public JdApiResponse<JSONObject> orderCheckDlokOrderQuery(String date, Integer page) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        jsonObject.put("page", page);
        return request("biz.order.checkDlokOrder.query", jsonObject, "biz_order_checkDlokOrder_query_response", JSONObject.class);
    }

    /**
     * 根据日期和页码，查询拒收订单信息
     *
     * @param date
     * @param page
     * @return
     */
    public JdApiResponse<JSONObject> orderCheckRefuseOrderQuery(String date, Integer page) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        jsonObject.put("page", page);
        return request("biz.order.checkRefuseOrder.query", jsonObject, "biz_order_checkRefuseOrder_query_response", JSONObject.class);
    }

    /**
     * 统一余额查询接口
     *
     * @param payType
     * @return
     */
    public JdApiResponse<String> priceBalanceGet(int payType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payType", payType);
        return request("biz.price.balance.get", jsonObject, "biz_price_balance_get_response", String.class);
    }

}
