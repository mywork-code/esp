package com.apass.esp.web.monitor;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.service.monitor.MonitorService;
import com.apass.gfb.framework.cache.CacheManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
@Controller
@RequestMapping(value = "/noauth/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;
    
    @Autowired
    private CacheManager cacheManager; 


    @RequestMapping(value = "/addMonitorLog", method = RequestMethod.POST)
    public Response addMonitorLog(@RequestBody MonitorDto monitorDto) {

        int record = monitorService.insertMonitor(monitorDto);
        return Response.success("添加成功", record);
    }
    
    @RequestMapping(value = "/editMonitorSetUp", method = RequestMethod.POST)
    @ResponseBody
    public Response editSetUp(@RequestBody MonitorDto monitorDto){
    	
    	if(!StringUtils.isBlank(monitorDto.getMonitorTime())){
    		cacheManager.set("monitor_time", monitorDto.getMonitorTime());
    	}
    	
    	if(!StringUtils.isBlank(monitorDto.getMonitorTimes())){
    		cacheManager.set("monitor_times", monitorDto.getMonitorTimes());
    	}
    	
    	return Response.success("修改设置成功!");
    }

  /**
   * 接口监控页面
   */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(){
    return "monitor/index";
  }

}
