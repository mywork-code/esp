package com.apass.esp.web.aotoship;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.schedule.OrderModifyStatusScheduleTask;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.utils.CronTools;
import com.apass.esp.utils.ResponsePageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "/application/autoship/management")
public class AutoShipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoShipController.class);

    @Autowired
    private KvattrService kvattrService;

    @Autowired
    private OrderModifyStatusScheduleTask orderModifyStatusScheduleTask;

    @RequestMapping("/page")
    public String page() {
        return "autoship/autoship";
    }

    @RequestMapping("/query")
    @ResponseBody
    public ResponsePageBody<ShipmentTimeConfigAttr> query() {
        ShipmentTimeConfigAttr shipmentTimeConfigAttr = kvattrService.get(new ShipmentTimeConfigAttr());
        List<ShipmentTimeConfigAttr> list = new ArrayList();
        list.add(shipmentTimeConfigAttr);
        ResponsePageBody<ShipmentTimeConfigAttr> responsePageBody = new ResponsePageBody();
        responsePageBody.setMsg("返回成功");
        responsePageBody.setStatus("1");
        responsePageBody.setRows(list);
        return responsePageBody;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response update(@RequestBody ShipmentTimeConfigAttr shipmentTimeConfigAttr) {
        ShipmentTimeConfigAttr shipmentTimeConfigAttr1 = kvattrService.get(new ShipmentTimeConfigAttr());
        if (shipmentTimeConfigAttr1 == null) {
            kvattrService.add(shipmentTimeConfigAttr);
        } else {
            List<Kvattr> list = kvattrService.getTypeName(shipmentTimeConfigAttr1);
            List<Kvattr> list2= new ArrayList<>();
            for (Kvattr kvattr:list
                 ) {
                if(kvattr.getKey().equalsIgnoreCase("time1")){
                    kvattr.setValue(shipmentTimeConfigAttr.getTime1());
                }
                if(kvattr.getKey().equalsIgnoreCase("time2")){
                    kvattr.setValue(shipmentTimeConfigAttr.getTime2());
                }
                if(kvattr.getKey().equalsIgnoreCase("time3")){
                    kvattr.setValue(shipmentTimeConfigAttr.getTime3());
                }
                list2.add(kvattr);
            }
            kvattrService.update(list2);
        }
        orderModifyStatusScheduleTask.startCron1(CronTools.getCron(shipmentTimeConfigAttr.getTime1()));
        orderModifyStatusScheduleTask.startCron2(CronTools.getCron(shipmentTimeConfigAttr.getTime2()));
        orderModifyStatusScheduleTask.startCron3(CronTools.getCron(shipmentTimeConfigAttr.getTime3()));

        return Response.successResponse();
    }
}
