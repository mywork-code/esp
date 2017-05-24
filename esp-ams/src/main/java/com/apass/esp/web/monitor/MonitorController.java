package com.apass.esp.web.monitor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.monitor.MonitorDto;
import com.apass.esp.domain.query.MonitorQuery;
import com.apass.esp.domain.vo.MonitorVo;
import com.apass.esp.service.monitor.MonitorService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.utils.DateFormatUtil;

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
    	
    	if(StringUtils.isBlank(monitorDto.getMonitorTime())){
    		return Response.fail("时间不能为空!");
    	}
    	
    	if(!NumberUtils.isNumber(monitorDto.getMonitorTime())){
    		return Response.fail("时间必须为数字!");
    	}
    	
    	if(StringUtils.isBlank(monitorDto.getMonitorTimes())){
    		return Response.fail("次数不能为空!");
    	}
    	
    	if(!NumberUtils.isNumber(monitorDto.getMonitorTimes())){
    		return Response.fail("次数必须为数字!");
    	}
    	
    	cacheManager.set("monitor_time", monitorDto.getMonitorTime());
    	cacheManager.set("monitor_times", monitorDto.getMonitorTimes());
        HashMap map = new HashMap();
    	map.put("monitor_times",cacheManager.get("monitor_times"));
    	map.put("monitor_time",cacheManager.get("monitor_time"));
    	return Response.success("配置成功!",JsonUtil.toJsonString(map));
    }

  /**
   * 接口监控页面
   */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(){
    return "monitor/index";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public ResponsePageBody<MonitorVo> listConfig(MonitorQuery query) {
	  
	  //如果days为空，就查询当天的数据
	 if(query.getDays() == null || query.getDays() == 0){
		 query.setStartCreateDate(DateFormatUtil.dateToString(new Date())+" 00:00:00");
	 }else{
		 Calendar cal = Calendar.getInstance();
		 cal.add(cal.DATE, query.getDays());
		 query.setStartCreateDate(DateFormatUtil.dateToString(cal.getTime())+" 00:00:00"); 
	 }
	 query.setEndCreateDate(DateFormatUtil.dateToString(new Date())+" 23:59:59");
	
    return  monitorService.pageListMonitorLog(query);
  }
}
