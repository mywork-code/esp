package com.apass.esp.service.logistics;

import org.springframework.beans.factory.annotation.Autowired;

import com.apass.gfb.framework.exception.BusinessException;


//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BootApplication.class)
//@WebAppConfiguration
public class LogisticsHttpClientTest {

    @Autowired
    private LogisticsService client;
    
//    @Test
    public void loadLogisticInfo() throws Exception{
//        String orderCode="783457674642";
//        String shipperCode="SF";
//        client.loadLogisticInfo(orderCode, shipperCode);
    }

//    @Test
    public void subscribeSignleTracking() throws BusinessException {
        //顺丰
//        String trackingNumber = "788844952988";
////        String trackingNumber = "3320083389646";
//        String carrierCode = "sf-express";
//        String orderId="12341486973855250";
        //中通
        
//        try {
//            String trackingNumber = "412640215280";
//            String carrierCode = "zto";
//            String orderId = "6831487052354309";
//            client.subscribeSignleTracking(trackingNumber, carrierCode,orderId);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
    
//    @Test
    public void getSignleTrackings() throws Exception {
        
//        String trackingNumber = "788844952988";
//        String orderId="6831487052354309";
//        String carrierCode = "sfb2c";
        //中通
//        try {
//            String trackingNumber = "412640215280";
//            String carrierCode = "zto";
//            String orderId="6831487052354309";
//            Map<String, Object> trackings = client.getSignleTrackings(carrierCode,trackingNumber,orderId);
//            String json = GsonUtils.toJson(trackings);
//            System.out.println(json);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
