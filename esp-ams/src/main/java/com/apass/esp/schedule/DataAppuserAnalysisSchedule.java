package com.apass.esp.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.apass.esp.domain.Response;
import com.apass.gfb.framework.logstash.LOG;
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
import com.apass.esp.domain.vo.DataAppuserAnalysisDto;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.esp.service.talkingdata.TalkDataService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
@RequestMapping("/talkdata/analysis")
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
	 * 每天跑1点一次
	 * @param startDate YYYY-MM-dd
	 * @param endDate YYYY-MM-dd
     * @return
     */
	@RequestMapping("/test1")
	@ResponseBody
	public Response test1(String startDate,String endDate){
		logger.info("DataAppuserAnalysisSchedule---->test1()方法如看看开始执行,参数 开始时间:{},结束时间:{}",startDate,endDate);
		List<DataAppuserAnalysisDto> list = null;
		try{
			list = everyDayScheduleData(startDate, endDate);
		}catch (Exception e){
			logger.error("每天跑批设备用户talkingdata分析异常",e);
			return Response.fail("失败!");
		}
		return Response.success("成功，新增的内容有:{}",list);
	}

	/**
	 * 当天 1:20 更新昨天数据t_data_appuser_analysis表
	 * @param txnId YYYY-MM-dd
	 * @return
     */
	@RequestMapping("/test2")
	@ResponseBody
	public Response test2(String txnId){
		logger.info("DataAppuserAnalysisSchedule---->test2()方法如看看开始执行,参数查询时间txnId:{}",txnId);
		List<DataAppuserAnalysisDto> list = null;
		try{
			updateAnalysisRegisterUser(txnId);
		}catch (Exception e){
			logger.error("更新昨天数据talkingdate数据分析异常",e);
			return Response.fail("失败!");
		}
		return Response.success("成功，新增的内容有:{}",list);
	}

	/**
	 * 每小时更新一次
	 * @param startDate YYYY-MM-dd
	 * @param endDate YYYY-MM-dd
	 * @return
     */
	@RequestMapping("/test3")
	@ResponseBody
	public Response test3(String startDate,String endDate){
		logger.info("DataAppuserAnalysisSchedule---->test3()方法如看看开始执行,参数查询开始时间startDate:{},endDate:{}",
				startDate,endDate);
		List<DataAppuserAnalysisDto> list = null;
		try{
			everyHoursSchedule(startDate,endDate);
		}catch (Exception e){
			logger.error("每小时更新一次数据导演 异常",e);
			return Response.fail("失败!");
		}
		return Response.success("成功，新增的内容有:{}",list);
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	public void everyDayScheduleDate(){
		logger.info("DataAppuserAnalysisSchedule---->everyDayScheduleDate()job方法开始执行,执行时间:{}",
				DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
		try {
			Date begin = DateFormatUtil.addDays(new Date(),-1);
			String startDate = DateFormatUtil.dateToString(begin);
			String endDate = DateFormatUtil.dateToString(new Date());
			everyDayScheduleData(startDate,endDate);
		}catch (Exception e){
			logger.error("DataAppuserAnalysisSchedule---->everyDayScheduleDate()方法异常,---Exception---",e);
		}

	}
	@Scheduled(cron = "0 20 1 * * ?")
	public void updateAnalysisRegisterUser(){
		logger.info("DataAppuserAnalysisSchedule---->updateAnalysisRegisterUser()job方法开始执行,执行时间:{}",
				DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
		try{
			String txnId = DateFormatUtil.dateToString(DateFormatUtil.addDays(new Date(), -1),"yyyyMMdd") ;

			updateAnalysisRegisterUser(txnId);
		}catch (Exception e){
			logger.error("DataAppuserAnalysisSchedule---->updateAnalysisRegisterUser()方法异常,---Exception---",e);
		}
	}
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void everyHoursSchedule(){
		logger.info("DataAppuserAnalysisSchedule---->everyHoursSchedule()job方法开始执行,执行时间:{}",
				DateFormatUtil.dateToString(new Date(),DateFormatUtil.YYYY_MM_DD_HH_MM_SS));
		try{
			Date begin = DateFormatUtil.addDays(new Date(),-1);
			String startDate = DateFormatUtil.dateToString(begin);
			String endDate = DateFormatUtil.dateToString(new Date());
			everyHoursSchedule(startDate,endDate);
		}catch (Exception e){
			logger.error("DataAppuserAnalysisSchedule---->everyHoursSchedule()每小时更新一次数据异常",e);
		}
	}
    public void everyHoursSchedule(String startDate,String endDate){
		ArrayList<String> metrics = getHourlyMetrics();
		for (TermainalTyps termainal : TermainalTyps.values()) {
			String newusers = talkData.getTalkingDataByDataAnalysis(DateFormatUtil.string2date(startDate),
					DateFormatUtil.string2date(endDate),metrics, hourly,termainal.getMessage());
			logger.info("message--->{}",newusers);
			List<DataAppuserAnalysisDto> userIos = JSONObject.parseArray(JSONObject.parseObject(newusers).getString("result"), DataAppuserAnalysisDto.class);
			/*** 如果第一次进入就所有的数据写入数据库，否则更新当前hour的数据*/
	    	String daily =  DateFormatUtil.dateToString(DateFormatUtil.string2date(startDate), "yyyyMMdd");
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
	 * 每天跑1点一次
	 * 本方法用于向t_data_appuser_analysis表中插入数据
	 * @return
	 */
    public List<DataAppuserAnalysisDto> everyDayScheduleData(String startDate,String endDate){
		List<DataAppuserAnalysisDto> list = Lists.newArrayList();
		ArrayList<String> metrics = getDailyMetrics();
    	for (TermainalTyps termainal : TermainalTyps.values()) {
    		String newusers =  talkData.getTalkingDataByDataAnalysis(DateFormatUtil.string2date(startDate),
					DateFormatUtil.string2date(endDate),metrics, daily,termainal.getMessage());
    		JSONObject newuserObj = (JSONObject) JSONArray.parseArray(JSONObject.parseObject(newusers).getString("result")).get(0);
    		DataAppuserAnalysisDto retention = JSONObject.toJavaObject(newuserObj, DataAppuserAnalysisDto.class);
    		String nowDate = retention.getDaily().replace("-", "");
    		if(null != retention){
				retention.setPlatformids(Byte.valueOf(termainal.getCode()));
				retention.setType(Byte.valueOf("2"));
				retention.setDaily(nowDate);
			}
			dataAnalysisService.insertAnalysisData(retention);
			list.add(retention);
    	}

		return list;
	}

    
    /**
     * 当天更新昨天的数据（每天1点20分更新）
     */
    public void updateAnalysisRegisterUser(String txnId){
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
