package com.apass.esp.nothing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.domain.enums.YesNo;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.service.payment.PaymentService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;

/**
 * 支付BSS回调
 * 
 * @description
 *
 * @author liuming
 * @version $Id: PayCallback.java, v 0.1 2017年3月12日 下午3:00:22 liuming Exp $
 */
@Path("/payment")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PayCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayCallback.class);

	@Autowired
	private PaymentService paymentService;

	@Autowired
	public OrderService orderService;

	@Autowired
	public AwardActivityInfoService awardActivityInfoService;

	@Autowired
	public AwardBindRelService awardBindRelService;

	@Autowired
	public AwardDetailService awardDetailService;

	/**
	 * BSS支付成功或失败回调
	 * 
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/callback")
	public Response callback(Map<String, Object> paramMap) {
		String logStashSign = LogStashKey.PAY_CALLBACK.getValue();
		String methodDesc = LogStashKey.PAY_CALLBACK.getName();
		String status = CommonUtils.getValue(paramMap, "status"); // 支付状态[成功 失败]
		String orderId = CommonUtils.getValue(paramMap, "orderId"); // 订单号

		String requestId = logStashSign + "_" + orderId;
		LOG.info(requestId, methodDesc, GsonUtils.toJson(paramMap));

		if (StringUtils.isAnyEmpty(status, orderId)) {
			return Response.fail("请选择支付方式!");
		}
		try {
			paymentService.callback(requestId, orderId, status);
		} catch (Exception e) {
			LOGGER.error("订单支付失败", e);
			return Response.fail("订单支付失败!请重新支付");
		}
		addRebateRecord(status, orderId);
		return Response.success("支付成功");
	}

	private void addRebateRecord(String status, String orderId) {
		if (YesNo.isNo(status)) {
			return;
		}
		// 付款成功
		// 该订单是否可以返现
		AwardActivityInfoVo awardActivityInfoVo = null;
		try {
			awardActivityInfoVo = awardActivityInfoService.getActivityByName(AwardActivity.ActivityName.INTRO);
		} catch (BusinessException e) {
			LOGGER.error("getActivityBy intro error orderId {}", orderId);
			return;
		}
		// 返现活动存在
		if (awardActivityInfoVo != null) {
			OrderInfoEntity orderInfoEntity = null;
			Long userId = null;
			try {
				orderInfoEntity = orderService.selectByOrderId(orderId);
				userId = orderInfoEntity.getUserId();
			} catch (BusinessException e) {
				LOGGER.error("根据订单号和用户id查询订单信息", e);
				LOGGER.error("selectByOrderId orderId{},userId{} error ", orderId, userId,e);
				return;
			}
			if (orderInfoEntity != null) {// 订单存在
				Date startDate = DateFormatUtil.string2date(awardActivityInfoVo.getaStartDate(), "yyyy-MM-dd HH:mm:ss");
				Date endDate = DateFormatUtil.string2date(awardActivityInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
				Date date = orderInfoEntity.getCreateDate();// 下单时间
				LOGGER.info("userId {}  ,orderId {} ,activity id {},startDate {},endDate {},curDate {}", userId,
						orderId, awardActivityInfoVo.getId(), startDate, endDate, date);
				if (date.before(endDate) && date.after(startDate)) {// 下单时间在活动有效期
					AwardBindRel awardBindRel = awardBindRelService.getByInviterUserId(String.valueOf(userId));
					if (awardBindRel != null) {// 当前用户已经被邀请
						AwardDetailDto awardDetailDto = new AwardDetailDto();
						awardDetailDto.setActivityId(awardBindRel.getActivityId());
						// 返点金额
						String rebateString = awardActivityInfoVo.getRebate();
						BigDecimal rebate = new BigDecimal(rebateString.substring(0, rebateString.length() - 1))
								.multiply(BigDecimal.valueOf(0.01));
						BigDecimal rebateAmt = orderInfoEntity.getOrderAmt().multiply(rebate);
						int rebateInt = rebateAmt.intValue();
						// 返现金额小于1时 不返现
						if (rebateInt == 0) {
							return;
						}
						awardDetailDto.setAmount(new BigDecimal(rebateInt));
						awardDetailDto.setMainOrderId(orderId);
						awardDetailDto.setCreateDate(new Date());
						awardDetailDto.setUpdateDate(new Date());
						// 处理中
						awardDetailDto.setStatus((byte) AwardActivity.AWARD_STATUS.PROCESSING.getCode());
						// 获得
						awardDetailDto.setType((byte) AwardActivity.AWARD_TYPE.GAIN.getCode());
						awardDetailDto.setUserId(awardBindRel.getUserId());
						awardDetailService.addAwardDetail(awardDetailDto);
						LOGGER.info(
								"userId {}  ,orderId {} ,activity id {},orderInfoEntity.getOrderAmt {} , awardActivityInfoVo.getRebate {}",
								userId, orderId, awardActivityInfoVo.getId(), orderInfoEntity.getOrderAmt(),
								awardActivityInfoVo.getRebate());
						return;
					}
				}

			}

		}
	}
}
