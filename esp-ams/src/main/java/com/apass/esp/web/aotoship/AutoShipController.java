package com.apass.esp.web.aotoship;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.aftersale.CashRefundDtoVo;
import com.apass.esp.domain.entity.banner.BannerInfoEntity;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.esp.web.banner.BannerController;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
@RequestMapping(value = "/application/autoship/management")
public class AutoShipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoShipController.class);

    @Autowired
    private KvattrService kvattrService;

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


    @RequestMapping("/update")
    @ResponseBody
    public Response update() {
        kvattrService.add(new ShipmentTimeConfigAttr());
        return Response.successResponse();
    }
}
