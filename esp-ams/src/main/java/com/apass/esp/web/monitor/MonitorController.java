package com.apass.esp.web.monitor;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.service.monitor.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
@Controller
@RequestMapping(value = "/noauth/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;


    @RequestMapping(value = "/addMonitorLog", method = RequestMethod.POST)
    public Response addMonitorLog(@RequestBody MonitorDto monitorDto) {

        int record = monitorService.insertMonitor(monitorDto);
        return Response.success("添加成功", record);
    }

}
