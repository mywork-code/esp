package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.domain.vo.CommissionWalletVo;
import com.apass.esp.mq.listener.JDTaskAmqpAccess;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.MD5Utils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
public class StaticFileController {

    @Autowired
    private KvattrService kvattrService;

    @Value("${esp.image.uri}")
    private String appWebDomain;

    @Autowired
    private  OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JDTaskAmqpAccess jdTaskAmqpAccess;

    @RequestMapping(value = "v1/app_weex")
    @ResponseBody
    public Response getMd5ByFile() {
        InputStream in  =  StaticFileController.class.getClassLoader().getResourceAsStream("static/WebContent/js/commission/commission.weex_v17.js");
        String md5 = MD5Utils.getMd5ByFile(in);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",true);
        map.put("id","app_weex");
        map.put("url",appWebDomain + "/appweb/WebContent/js/commission/commission.weex_v17.js");
        map.put("ver","17");
        map.put("md5",md5);

        return Response.successResponse(map);
    }

    @RequestMapping(value = "v2/app_weex")
    @ResponseBody
    public Response getMd5ByFile2() {
        ClassLoader classLoader = StaticFileController.class.getClassLoader();
        List<CommissionWalletVo> commissionWalletVos = Lists.newArrayList();

        InputStream in  =  classLoader.getResourceAsStream("static/WebContent/js/commission/commission.weex_v16.js");
        String md5 = MD5Utils.getMd5ByFile(in);
        CommissionWalletVo commissionWalletVo = new CommissionWalletVo();
        commissionWalletVo.setVer("16");
        commissionWalletVo.setFlag(true);
        commissionWalletVo.setId("commission");
        commissionWalletVo.setUrl(appWebDomain+"/appweb/WebContent/js/commission/commission.weex_v16.js");
        commissionWalletVo.setMd5(md5);
        commissionWalletVos.add(commissionWalletVo);

        InputStream in2  =  classLoader.getResourceAsStream("static/WebContent/js/wallet/wallet.weex_v1.js");
        String md52 = MD5Utils.getMd5ByFile(in);
        CommissionWalletVo commissionWalletVo2 = new CommissionWalletVo();
        commissionWalletVo2.setVer("1");
        commissionWalletVo2.setFlag(true);
        commissionWalletVo2.setId("wallet");
        commissionWalletVo2.setUrl(appWebDomain+"/appweb/WebContent/js/wallet/wallet.weex_v1.js");
        commissionWalletVo2.setMd5(md52);
        commissionWalletVos.add(commissionWalletVo2);

        try {
            in2.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.successResponse(commissionWalletVos);
    }

    @RequestMapping(value = "jsUtils/test1", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap) {
        kvattrService.add(new ShipmentTimeConfigAttr());
        return Response.successResponse();
    }

    @RequestMapping(value = "jsUtils/test2", method = RequestMethod.POST)
    @ResponseBody
    public Response test2(@RequestBody Map<String, Object> paramMap) {
        ShipmentTimeConfigAttr t = new ShipmentTimeConfigAttr();
        try {
             t =  kvattrService.get(new ShipmentTimeConfigAttr() );
        }catch (Exception e ){

        }

        return Response.successResponse(t);
    }

    @RequestMapping(value = "jsUtils/initGoodsSaleVolume", method = RequestMethod.POST)
    @ResponseBody
    public Response initGoodsSaleVolume(@RequestBody Map<String, Object> paramMap){
        List<String> orderIdList = orderInfoRepository.initGoodsSaleVolume();
        orderService.updateJdGoodsSaleVolume(orderIdList);
        return Response.successResponse();
    }

    @RequestMapping("/test/mq/sendmsg")
    @ResponseBody
    public String testSendMsgToMq(){
        jdTaskAmqpAccess.directSend("test");
        return "success";
    }


}
