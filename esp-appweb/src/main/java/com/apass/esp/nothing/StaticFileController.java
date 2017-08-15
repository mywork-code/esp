package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.mq.listener.JDTaskAmqpAccess;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        InputStream in  =  StaticFileController.class.getClassLoader().getResourceAsStream("static/WebContent/js/app.weex_v9.js");
        String md5 = MD5Utils.getMd5ByFile(in);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",true);
        map.put("id","app_weex");
        map.put("url",appWebDomain + "/appweb/WebContent/js/app.weex_v9.js");
        map.put("ver","9");
        map.put("md5",md5);
        return Response.successResponse(map);
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
