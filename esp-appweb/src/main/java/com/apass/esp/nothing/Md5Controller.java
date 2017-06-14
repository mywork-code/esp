package com.apass.esp.nothing;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.kvattr.ShipmentTimeConfigAttr;
import com.apass.esp.service.common.KvattrService;
import com.apass.esp.web.activity.ActivityWithDrawController;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/jsUtils")
public class Md5Controller {

    @Autowired
    private KvattrService kvattrService;

    @RequestMapping(value = "/getMd5ByFile", method = RequestMethod.POST)
    @ResponseBody
    public Response getMd5ByFile(@RequestBody Map<String, Object> paramMap) {
        InputStream in  =  Md5Controller.class.getClassLoader().getResourceAsStream("static/WebContent/js/reset.js");
        //java.net.URL url  =  ActivityWithDrawController.class.getClassLoader().getResource("/static/WebContent/js/reset.js");

        String s = MD5Utils.getMd5ByFile(in);
        return Response.successResponse(s);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public Response test(@RequestBody Map<String, Object> paramMap) {
        kvattrService.add(new ShipmentTimeConfigAttr());
        return Response.successResponse();
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public Response test2(@RequestBody Map<String, Object> paramMap) {
        ShipmentTimeConfigAttr t = new ShipmentTimeConfigAttr();
        try {
             t =  kvattrService.get(new ShipmentTimeConfigAttr() );
        }catch (Exception e ){

        }

        return Response.successResponse(t);
    }
}
