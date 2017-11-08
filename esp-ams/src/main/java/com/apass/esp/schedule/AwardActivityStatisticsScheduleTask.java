package com.apass.esp.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.enums.AwardActivity.ActivityName;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.ActivityStatisticsVo;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 转介绍奖励金统计
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class AwardActivityStatisticsScheduleTask {
	
	private static final Logger logger  = LoggerFactory.getLogger(AwardActivityStatisticsScheduleTask.class);
	
    @Value("${AwardActivityStatistics.daily.sendto}")
    public String sendToAddress;
    
    @Value("${AwardActivityStatistics.daily.copyto}")
    public String copyToAddress;
	@Autowired
	private ExportExcleCommonModel model;
	@Autowired
	private AwardActivityInfoService awardActivityInfoService;
	@Autowired
	private AwardBindRelService awardBindRelService;
	@Autowired
	private AwardDetailService awardDetailService;
	
	@Scheduled(cron = "0 0 7 * * ?")//每天早上7点
	public void awardActivityStatistics(){
		List<ActivityStatisticsVo> chanelStatistisList=awardActivityStatisticsList();
		String fileName = "转介绍奖励金额统计";
		String[] rowHeadArr = {"统计日期","推荐人总数", "拉新总数", "总奖励金额", "已返现金额", "可返现金额"};
        String[] headKeyArr = {"des","refereeNums", "newNums", "awardAmount", "backAwardAmount", "haveAwardAmount"};
		try {
			model.generateFile(chanelStatistisList,rowHeadArr,headKeyArr,fileName);
		} catch (Exception e) {
			logger.error("export order channel with exception information ");
			e.printStackTrace();
		}
		model.sendMail(sendToAddress, copyToAddress, fileName);
	}
	
	public List<ActivityStatisticsVo> awardActivityStatisticsList(){
		List<ActivityStatisticsVo> list=new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		//获取当月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = format.format(calendar1.getTime());
        String firstDay2=firstDay+" 00:00:00";
        //获取前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String BeforeDay = format.format(calendar.getTime());
        String BeforeDay2=BeforeDay+" 23:59:59";
        
        ActivityName activityName = ActivityName.INTRO;// 获取活动名称
		try {
			AwardActivityInfoVo aInfoVo = awardActivityInfoService.getActivityByName(activityName);
			if (null == aInfoVo) {
				logger.error("获取信息失败,无有效活动！");
				return null;
			}
			Date aEndDate = DateFormatUtil.string2date(aInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
			int falge = aEndDate.compareTo(new Date());
			if (falge < 0) {
				logger.error("获取信息失败,活动已经结束！");
				return null;
			}
			//当月
			ActivityStatisticsVo astvo1=getActivityStatisticsVo(aInfoVo.getId(),firstDay2,BeforeDay2);
			astvo1.setDes("活动当月");
			list.add(astvo1);
			//活动上线至今
			ActivityStatisticsVo astvo2=getActivityStatisticsVo(aInfoVo.getId(),null,BeforeDay2);
			astvo2.setDes("活动上线至今");
			list.add(astvo2);
			
		} catch (BusinessException e) {
			logger.error("获取信息失败,活动已经结束！");
			return null;
		}
		return list;
	}
	public ActivityStatisticsVo getActivityStatisticsVo(Long id,String day1,String day2){
		ActivityBindRelStatisticQuery query=new ActivityBindRelStatisticQuery();
		if(null !=id){
			query.setActivityId(id);
		}
		if(StringUtils.isNotEmpty(day1)){
			query.setStartCreateDate(day1);
		}
		if(StringUtils.isNotEmpty(day2)){
			query.setEndCreateDate(day2);
		}
		//活动推荐人总数	
		int refereeNums=awardBindRelService.refereeNums(query);
		//活动拉新人总数
		int newNums=awardBindRelService.newNums(query);
		BigDecimal awardAmount=BigDecimal.ZERO;
		BigDecimal backAwardAmount=BigDecimal.ZERO;
		BigDecimal haveAwardAmount=BigDecimal.ZERO;
		if(null !=awardDetailService.getAllAwardByActivityIdAndTime(query)){
			awardAmount=awardDetailService.getAllAwardByActivityIdAndTime(query);
		}
		if(null !=awardDetailService.getAllBackAwardByActivityIdAndTime(query)){
			backAwardAmount=awardDetailService.getAllBackAwardByActivityIdAndTime(query);
		}
		haveAwardAmount=awardAmount.subtract(backAwardAmount);
		ActivityStatisticsVo astvo=new ActivityStatisticsVo();
		astvo.setRefereeNums(refereeNums);
		astvo.setNewNums(newNums);
		astvo.setAwardAmount(awardAmount);
		astvo.setBackAwardAmount(backAwardAmount);
		astvo.setHaveAwardAmount(haveAwardAmount);
		return astvo;
	}
	
}
