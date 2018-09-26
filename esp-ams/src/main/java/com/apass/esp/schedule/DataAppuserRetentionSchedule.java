package com.apass.esp.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.vo.DataAppuserAnalysisDto;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.enums.TermainalTyps;
import com.apass.esp.domain.vo.DataAppuserRetentionDto;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
@RequestMapping("/talkdata/retention")
public class DataAppuserRetentionSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(DataAppuserRetentionSchedule.class);
			
	/*** 数据维度，即数据分组方式*/
    public static String daily = "daily";
    
    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserRetentionService retentionService;

	@RequestMapping("/test1")
	@ResponseBody
	public Response test1(String startDate, String endDate){
		logger.info("DataAppuserAnalysisSchedule---->test1()方法开始执行,参数 开始时间:{},结束时间:{}",startDate,endDate);
		List<DataAppuserRetentionDto> list = null;
		try{
			list = retentionEveryDayScheduleData(DateFormatUtil.string2date(startDate),
					DateFormatUtil.string2date(endDate));
		}catch (Exception e){
			logger.error("每天跑批设备用户talkingdata分析异常",e);
			return Response.fail("失败!");
		}
		return Response.success("成功，新增的内容有:{}",list);
	}

	@Scheduled(cron = "0 0 4 * * ?")
	public void retentionEveryDayScheduleData(){
		logger.info("DataAppuserAnalysisSchedule---->retentionEveryDayScheduleData()job方法job方法开始执行,执行时间:{}",
				DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
		try{
			Date startDate = DateFormatUtil.addDays(new Date(), -30);
			Date endDate = DateFormatUtil.addDays(new Date(), -1);
			retentionEveryDayScheduleData(startDate,endDate);
		}catch (Exception e){
			logger.error("DataAppuserRetentionSchedule---->retentionEveryDayScheduleData()方法异常,Exception---",e);
		}
	}
    
    /**
	 * 每天跑一次
	 * 此方法往t_data_appuser_retention表中插入数据
	 * @return
	 */
    public List<DataAppuserRetentionDto> retentionEveryDayScheduleData(Date startDate,Date endDate){
		List<DataAppuserRetentionDto> list = Lists.newArrayList();
		ArrayList<String> metrics = getMetrics();
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String day1retentions = talkData.getTalkingDataByDataAnalysis(startDate,endDate,metrics, daily,termainal.getMessage());
			logger.info("result--->"+day1retentions);
			List<DataAppuserRetentionDto> dtoList = JSONArray.parseArray(JSONObject.parseObject(day1retentions).getString("result"), DataAppuserRetentionDto.class);
			for (DataAppuserRetentionDto retention : dtoList) {
				retention.setPlatformids(Byte.valueOf(termainal.getCode()));
				retentionService.insertRetention(retention);
			}
			list.addAll(dtoList);
		}

		return list;
	}
    
    public ArrayList<String> getMetrics(){
    	ArrayList<String> metrics = new ArrayList<String>();
    	metrics.add("day1retention");//新增用户次日留存率
    	metrics.add("day3retention");//新增用户3日留存率
    	metrics.add("day7retention");//新增用户7日留存率
    	metrics.add("day14retention");//新增用户14日留存率
    	metrics.add("day30retention");//新增用户30日留存率
    	
    	metrics.add("dauday1retention");//活跃用户次日留存率
    	metrics.add("dauday3retention");//活跃用户3日留存率
    	metrics.add("dauday7retention");//活跃用户7日留存率
    	metrics.add("dauday14retention");//活跃用户14日留存率
    	metrics.add("dauday30retention");//活跃用户30日留存率
    	
    	metrics.add("day7churnuser");//某日的7日不使用流失用户数
    	metrics.add("day14churnuser");//某日的14日不使用流失用户数
    	
    	metrics.add("day7backuser");//7日以上流失用户中的回流用户
    	metrics.add("day14backuser");//14日以上流失用户中的回流用户
		return metrics;
    }
	
}
