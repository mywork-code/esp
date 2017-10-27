package com.apass.esp.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.apass.esp.domain.vo.ActivityDetailStatisticsVo;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

/**
 * 转介绍奖励金明细
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class AwardActivityDetailStatisticsScheduleTask {

	private static final Logger logger = LoggerFactory.getLogger(AwardActivityDetailStatisticsScheduleTask.class);

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

	@Scheduled(cron = "0 0 7 * * ?")
	public void awardActivityStatistics() {
		List<ActivityDetailStatisticsVo> chanelStatistisList = activityDetailStatisList();
		String fileName = "转介绍奖励金额明细";
		String[] rowHeadArr = { "推荐人", "拉新人数", "总奖励金额", "已返现金额", "可返现金额" };
		String[] headKeyArr = { "mobile", "newNums", "awardAmount", "backAwardAmount", "haveAwardAmount" };
		try {
			model.generateFile(chanelStatistisList, rowHeadArr, headKeyArr, fileName);
		} catch (Exception e) {
			logger.error("export order channel with exception information ");
			e.printStackTrace();
		}
		model.sendMail(sendToAddress, copyToAddress, fileName);
	}

	public List<ActivityDetailStatisticsVo> activityDetailStatisList() {
		List<ActivityDetailStatisticsVo> list = new ArrayList<>();
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
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			//获取前一天时间
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(new Date());
	        calendar.add(Calendar.DAY_OF_MONTH, -1);
	        String BeforeDay = format.format(calendar.getTime());
	        String BeforeDay2=BeforeDay+" 00:00:00";
			ActivityBindRelStatisticQuery query = new ActivityBindRelStatisticQuery();
			query.setActivityId(aInfoVo.getId());
			query.setEndCreateDate(BeforeDay2);
			List<ActivityDetailStatisticsVo> userIdList = awardBindRelService.getUserIdListByActivityId(query);
			Iterator<ActivityDetailStatisticsVo> it = userIdList.iterator();
			while (it.hasNext()) {
				ActivityDetailStatisticsVo aVo = it.next();
				query.setUserId(aVo.getUserId());
				BigDecimal awardAmount = BigDecimal.ZERO;
				BigDecimal backAwardAmount = BigDecimal.ZERO;
				BigDecimal haveAwardAmount = BigDecimal.ZERO;
				if (null != awardDetailService.getAllAwardByActivityIdAndTime(query)) {
					awardAmount = awardDetailService.getAllAwardByActivityIdAndTime(query);
				}
				if (null != awardDetailService.getAllBackAwardByActivityIdAndTime(query)) {
					backAwardAmount = awardDetailService.getAllBackAwardByActivityIdAndTime(query);
				}
				haveAwardAmount = awardAmount.subtract(backAwardAmount);
				aVo.setAwardAmount(haveAwardAmount);
				aVo.setBackAwardAmount(backAwardAmount);
				aVo.setHaveAwardAmount(haveAwardAmount);
				// 总奖励金额为0的明细不展示
				if (awardAmount.compareTo(BigDecimal.ZERO) <= 0) {
					it.remove();
				}
			}
		} catch (BusinessException e) {
			logger.error("获取信息失败,活动已经结束！");
			return null;
		}
		return list;
	}
}
