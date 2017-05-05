package com.apass.esp.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.entity.refund.RefundInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.refund.OrderRefundService;
import com.apass.gfb.framework.utils.DateFormatUtil;
/**
 * 邀请人获得返点结算 Task
 * @author xianzhi.wang
 *
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class RebateActivityScheduleTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(RebateActivityScheduleTask.class);

	@Autowired
	public AwardDetailService awardDetailService;

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	@Autowired
	public OrderService orderService;

	@Autowired
	public OrderRefundService orderRefundService;

	/**
	 * 每天凌晨一点执行,邀请人获得返点结算
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void validateActivityEndtime() {
		try {
			LOGGER.info("邀请人获得返点结算定时任务开始");
			// 转介绍活动存在
			AwardActivityInfoVo awardActivityInfoVo = awardActivityInfoService
					.getActivityByName(AwardActivity.ActivityName.INTRO);
			if (awardActivityInfoVo != null) {
				Date startDate = DateFormatUtil.string2date(awardActivityInfoVo.getaStartDate(), "yyyy-MM-dd HH:mm:ss");
				Date endDate = DateFormatUtil.string2date(awardActivityInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				LOGGER.info("activity id {},startDate {},endDate {},curDate {}", awardActivityInfoVo.getId(), startDate,
						endDate, date);
				// 转介绍活动在活动有效期内
				if (!date.before(endDate) || !date.after(startDate)) {
					return;
				}
				List<AwardDetailDto> list = awardDetailService.queryAwardDetailByStatusAndType((byte) 0, (byte) 2);
				if (CollectionUtils.isEmpty(list)) {
					return;
				}
				for (AwardDetailDto awardDetailDto : list) {
					AwardActivityInfo awardActivityInfo = awardActivityInfoService
							.getAwardActivityInfoById(awardDetailDto.getActivityId());
					String activityName = awardActivityInfo.getActivityName();
					// 该活动为返现活动
					if (StringUtils.isNoneBlank(activityName)
							&& AwardActivity.ActivityName.INTRO.getValue().equals(activityName)) {
						String orderId = awardDetailDto.getMainOrderId();
						OrderInfoEntity orderInfoEntity = orderService.selectByOrderId(orderId);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderId", orderId);
						map.put("refundType", "0");
						RefundInfoEntity refundInfoEntity = orderRefundService
								.queryRefundInfoByOrderIdAndRefundType(map);
						// 订单状态已完成且订单已完成37天 ，没有发生过退货行为 才能获得返点
						Date newDate = DateFormatUtil.addDays(orderInfoEntity.getUpdateDate(), 37);
						if ("D04".equals(orderInfoEntity.getStatus()) && newDate.before(date)
								&& refundInfoEntity == null) {
							// 更新状态
							awardDetailDto.setUpdateDate(new Date());
							awardDetailDto.setStatus((byte) 0);
							try {
								awardDetailService.updateAwardDetail(awardDetailDto);
							} catch (Exception e) {
								LOGGER.error(
										"activity id {},startDate {},endDate {},curDate {} orderId {} 更新 返现获得 失败=====",
										awardActivityInfoVo.getId(), startDate, endDate, date,
										awardDetailDto.getMainOrderId(),e);
							}
							LOGGER.info(
									"activity id {},活动开始时间 startDate {},活动结束时间 endDate {},获得返现时间  {},订单ID {} 获得返现成功,金额 ,{}",
									awardActivityInfoVo.getId(), startDate, endDate, new Date(),
									awardDetailDto.getMainOrderId(), awardDetailDto.getAmount());
						}

					}

				}

			}
			LOGGER.info("邀请人获得返点结算定时任务结束");
		} catch (Exception e) {
			LOGGER.error("邀请人获得返点结算", e);
		}
	}
}
