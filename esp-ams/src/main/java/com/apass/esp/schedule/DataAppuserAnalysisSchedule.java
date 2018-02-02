package com.apass.esp.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.apass.esp.domain.vo.DataAppuserAnalysisDto;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class DataAppuserAnalysisSchedule {

	private static final Logger logger = LoggerFactory.getLogger(DataAppuserAnalysisSchedule.class);
	
    /*** 数据维度，即数据分组方式*/
    public static String hourly = "hourly";
    public static String daily = "daily";
    @Autowired
    private TalkDataService talkData;
    
    @Autowired
    private DataAppuserAnalysisService  dataAnalysisService;
	
    /**
     * 每小时跑一次
     * @return
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void everyHoursSchedule(){
		ArrayList<String> metrics = getHourlyMetrics();
		Date date = new Date();
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String newusers = talkData.getTalkingDataByDataAnalysis(date,date,metrics, hourly,termainal.getMessage());
			logger.info("message--->{}",newusers);
			List<DataAppuserAnalysisDto> userIos = JSONObject.parseArray(JSONObject.parseObject(newusers).getString("result"), DataAppuserAnalysisDto.class);
			/*** 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据*/
	    	String daily =  DateFormatUtil.dateToString(date, "yyyyMMdd");
	    	/*** 插入数据之前，1、是否应该判断，当天的数据是否存在，2、如果不存在，全部插入，如果存在，值更新当天时间节点的数据*/
	    	for (DataAppuserAnalysisDto user : userIos) {
	    		user.setType(Byte.valueOf("1"));
	    		user.setPlatformids(Byte.valueOf(termainal.getCode()));
	    		user.setDaily(daily);
				/*** 此处的数字，标志着分组策略为hourly*/
				dataAnalysisService.insertAnalysis(user);
			}
		}
    }
	
    /**
	 * 每天跑一次
	 * 本方法用于向t_data_appuser_analysis表中插入数据
	 * @return
	 */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void everyDayScheduleData(){
		ArrayList<String> metrics = getDailyMetrics();
		Date time = new Date(); //DateFormatUtil.addDays(new Date(), -1);
    	for (TermainalTyps termainal : TermainalTyps.values()) {
    		String newusers =  talkData.getTalkingDataByDataAnalysis(time,time,metrics, daily,termainal.getMessage());
    		JSONObject newuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(newusers).getString("result")).get(0);
    		DataAppuserAnalysisDto retention = JSONObject.toJavaObject(newuserObj, DataAppuserAnalysisDto.class);
    		String nowDate = retention.getDaily().replace("-", "");
    		if(null != retention){
				retention.setPlatformids(Byte.valueOf(termainal.getCode()));
				retention.setType(Byte.valueOf("2"));
				retention.setDaily(nowDate);
			}
			dataAnalysisService.insertAnalysisData(retention);
    	}
	}
    
    /**
     * 当天更新昨天的数据（每天1点20分更新）
     */
    @Scheduled(cron = "0 20 1 * * ?")
    public void updateAnalysisRegisterUser(){
    	
    	String txnId = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -1),"yyyyMMdd") ;
    	
    	Map<String,Object> map = Maps.newHashMap();
    	map.put("isDelete", "00");
    	map.put("txnId",txnId);
    	map.put("type","2");
    	/*** 更新昨天的注册用户数*/
    	dataAnalysisService.updateAnalysisRegisterUser(map);
    }
    
	/**
	 * 每小时需要查询的粒度
	 * @return
	 */
	public static ArrayList<String> getHourlyMetrics(){
		ArrayList<String> metrics = new ArrayList<String>();
		metrics.add("newuser");//新增用户数
		metrics.add("session");//启动次数
		return metrics;
	}
	
	/**
	 * 每天需要查询的粒度
	 * @return
	 */
	public static ArrayList<String> getDailyMetrics(){
		ArrayList<String> metrics = new ArrayList<String>();
		metrics.add("newuser");//新增用户数
		metrics.add("session");//启动次数
		metrics.add("activeuser");//查询活跃用户数
		metrics.add("wau");//某日的近7日活跃用户数
		metrics.add("mau");//某日的近30日活跃用户数
		metrics.add("totaluser");//查询截至某日的累计用户数
		metrics.add("bounceuser");//一次性用户数
		metrics.add("sessionlength");//汇总的使用时长
		metrics.add("avgsessionlength");//平均每次启动使用时长
		return metrics;
	}
	
}
