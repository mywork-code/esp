package com.apass.esp.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.domain.enums.TermainalTyps;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.esp.domain.vo.DataAppuserRetentionDto;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.utils.DateFormatUtil;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class DataAppuserRetentionSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(DataAppuserRetentionSchedule.class);
			
	/*** 数据维度，即数据分组方式*/
    public static String daily = "daily";
    
    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserRetentionService retentionService;
    
    /**
	 * 每天跑一次
	 * 此方法往t_data_appuser_retention表中插入数据
	 * @return
	 */
    @Scheduled(cron = "0 0 2 * * ?")
    public void retentionEveryDayScheduleData(){
		ArrayList<String> metrics = getMetrics();
		Date time = DateFormatUtil.addDays(new Date(), -1);//今天获取的数据，应该是昨天的 ，此处待确认TODO
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String day1retentions = talkData.getTalkingDataByDataAnalysis(time,time,metrics, daily,termainal.getMessage());
			logger.info("result--->"+day1retentions);
			JSONObject newuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(day1retentions).getString("result")).get(0);
			DataAppuserRetentionDto retention = JSONObject.toJavaObject(newuserObj, DataAppuserRetentionDto.class);
			if(null != retention){
				retention.setPlatformids(Byte.valueOf(termainal.getCode()));
			}
			
			/*** 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据*/
	    	String nowDate = DateFormatUtil.dateToString(time, "yyyyMMdd");
    		/*** 插入数据之前，1、是否应该判断，当天的数据是否存在，2、如果不存在，全部插入，如果存在，值更新当天时间节点的数据*/
			DataAppuserRetention analysis = retentionService.getDataAnalysisByTxnId(new DataAnalysisVo(nowDate, termainal.getCode(),"2","00"));
			if(null != analysis){
				retention.setId(analysis.getId());
			}
			retentionService.insertRetention(retention);
			try {
	            TimeUnit.SECONDS.sleep(15);
	        } catch (InterruptedException e) {
	        	logger.error("-----DataAppuserRetentionSchedule Exception---->",e);
	        }
		}
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
