package com.apass.esp.web.thirdparty.jd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.third.party.jd.client.JdApiResponse;
import com.apass.esp.third.party.jd.client.JdOrderApiClient;
import com.apass.esp.third.party.jd.client.JdProductApiClient;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.esp.third.party.jd.entity.order.OrderReq;
import com.apass.esp.third.party.jd.entity.order.PriceSnap;
import com.apass.esp.third.party.jd.entity.order.SkuNum;
import com.apass.esp.third.party.jd.entity.person.AddressInfo;
import com.apass.esp.third.party.jd.entity.product.Stock;
import com.apass.gfb.framework.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */

@Controller
@RequestMapping("jd")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @Autowired
    private JdProductApiClient jdProductApiClient;

    @Autowired
    private JdOrderApiClient jdOrderApiClient;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap){
       // JSONObject jsonObject = jdTokenClient.getToken();
        //cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject.toJSONString());
        return Response.success("1","");
    }

    @RequestMapping(value = "/productPageNumQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productPageNumQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONArray> jdApiResponse =  jdProductApiClient.productPageNumQuery();
        return Response.success("1",jdApiResponse);
    }


    @RequestMapping(value = "/productSkuQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productSkuQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<String> jdApiResponse =  jdProductApiClient.productSkuQuery(106);
        return Response.success("1",jdApiResponse);
    }

    @RequestMapping(value = "/productDetailQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response productDetailQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse =  jdProductApiClient.productDetailQuery(2403211l);
        return Response.success("1",jdApiResponse);
    }


    @RequestMapping(value = "/priceBalanceGet", method = RequestMethod.POST)
    @ResponseBody
    public Response priceBalanceGet(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<String> jdApiResponse =  jdOrderApiClient.priceBalanceGet(4);
        return Response.success("1",jdApiResponse);
    }

    @RequestMapping(value = "/pctt", method = RequestMethod.POST)
    @ResponseBody
    public Response pctt(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressAllProvincesQuery();
        return Response.success("1",jdApiResponse);
    }

    @RequestMapping(value = "/addressCitysByProvinceIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressCitysByProvinceIdQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressCitysByProvinceIdQuery(2);
        return Response.success("1",jdApiResponse);
    }


    @RequestMapping(value = "/addressCountysByCityIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressCountysByCityIdQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressCountysByCityIdQuery(2813);
        return Response.success("1",jdApiResponse);
    }

    @RequestMapping(value = "/addressTownsByCountyIdQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response addressTownsByCountyIdQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse = jdProductApiClient.addressTownsByCountyIdQuery(51931);
        return Response.success("1",jdApiResponse);
    }



    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Response createOrder(@RequestBody Map<String, Object> paramMap){
        List<SkuNum> skuNumList = new ArrayList<>();
        List<PriceSnap> priceSnaps = new ArrayList<>();
        SkuNum skuNum = new SkuNum();
        skuNum.setNum(1);
        skuNum.setSkuId(2403211l);
        skuNumList.add(skuNum);

        AddressInfo addressInfo =new AddressInfo();
        addressInfo.setProvinceId(2);
        addressInfo.setCityId(2813);
        addressInfo.setCountyId(51976);
        addressInfo.setAddress("延安西路726号华敏翰尊大厦3层A-5");
        addressInfo.setReceiver("王贤志");
        addressInfo.setEmail("wangxianzhi1211@163.com");
        addressInfo.setMobile("17717573525");

        List<Long> skulist = new ArrayList<Long>();
        skulist.add(skuNum.getSkuId());
        JSONArray productPriceList=jdProductApiClient.priceSellPriceGet(skulist).getResult();
        BigDecimal price = null;
        BigDecimal jdPrice =null;
        if(productPriceList!=null &&productPriceList.get(0)!=null){
            Object productPrice=productPriceList.get(0);
            JSONObject jsonObject = (JSONObject) productPrice;
            price=jsonObject.getBigDecimal("price");
            jdPrice=jsonObject.getBigDecimal("jdPrice");
            priceSnaps.add(new PriceSnap(skulist.get(0), price, jdPrice));
        }
        OrderReq orderReq = new OrderReq();
        orderReq.setSkuNumList(skuNumList);
        orderReq.setAddressInfo(addressInfo);
        orderReq.setOrderPriceSnap(priceSnaps);
        orderReq.setRemark("test");

        JdApiResponse<JSONArray> skuCheckResult =  jdProductApiClient.productSkuCheckWithSkuNum(orderReq.getSkuNumList());
        if (!skuCheckResult.isSuccess()) {
            LOGGER.warn("check order status error, {}", skuCheckResult.toString());
        }
        for (Object o : skuCheckResult.getResult()) {
            JSONObject jsonObject = (JSONObject) o;
            int saleState = jsonObject.getIntValue("saleState");
            if (saleState != 1) {
                LOGGER.info("sku[{}] could not sell,detail:", jsonObject.getLongValue("skuId"), jsonObject.toJSONString());
                LOGGER.info( jsonObject.getLongValue("skuId")+"_");
            }
        }

        List<Stock> stocks = jdProductApiClient.getStock(orderReq.getSkuNumList(), orderReq.getAddressInfo().toRegion());
        for (Stock stock : stocks) {
            if (!"有货".equals(stock.getStockStateDesc())) {
                LOGGER.info("sku[{}] {}", stock.getSkuId(), stock.getStockStateDesc());
                LOGGER.info( stock.getSkuId()+"_");
            }
        }

        JdApiResponse<JSONObject> orderResponse = jdOrderApiClient.orderUniteSubmit(orderReq);
        LOGGER.info(orderResponse.toString());
        if ((!orderResponse.isSuccess() || "0008".equals(orderResponse.getResultCode()))&&!"3004".equals(orderResponse.getResultCode())) {
            LOGGER.warn("submit order error, {}", orderResponse.toString());

        }else if(!orderResponse.isSuccess() || "3004".equals(orderResponse.getResultCode())){
            LOGGER.warn("submit order error, {}", orderResponse.toString());
            //return "3004_"+orderResponse.getResultMessage();
        }
        long jdOrderId = orderResponse.getResult().getLongValue("jdOrderId");

        return Response.success("1",jdOrderId);
    }


    /**
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderOccupyStockConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Response orderOccupyStockConfirm(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<Boolean> confirmResponse = jdOrderApiClient.orderOccupyStockConfirm(58683527927l);
        LOGGER.info("confirm order error, {}", confirmResponse.toString());
        int confirmStatus = 0;
        if (confirmResponse.isSuccess() && confirmResponse.getResult()) {
          // orderSyncer.addOrder(jdOrderId);

            //orderStateSyncer.addOrder(orderNo);
            confirmStatus = 1;
//			return true;
        }
        return Response.success("1",58683527927l);
    }

    /**
     * 查询物流信息
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderOrderTrackQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response orderOrderTrackQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse =  jdOrderApiClient.orderOrderTrackQuery(58683527927l);
        return Response.success("1",jdApiResponse);
    }

    /**
     * 查询京东订单明细
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/orderJdOrderQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response orderJdOrderQuery(@RequestBody Map<String, Object> paramMap){
        JdApiResponse<JSONObject> jdApiResponse =  jdOrderApiClient.orderJdOrderQuery(58683527927l);
        return Response.success("1",jdApiResponse);
    }

}
