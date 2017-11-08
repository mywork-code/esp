package com.apass.esp.mq.listener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.entity.CreditAuthActivityMessage;
import com.apass.esp.domain.enums.AwardActivity.ActivityName;
import com.apass.esp.domain.vo.AwardActivityInfoVo;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.service.activity.AwardActivityInfoService;
import com.apass.esp.service.activity.AwardBindRelService;
import com.apass.esp.service.activity.AwardDetailService;
import com.apass.esp.service.registerInfo.RegisterInfoService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;

/**
 * Created by jie.xu on 17/7/14.
 */
@Component("creditAuthActivityTaskListener")
//@Profile("zqs")
public class CreditAuthActivityTaskListener implements MessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditAuthActivityTaskListener.class);
	@Autowired
	public AwardActivityInfoService awardActivityInfoService;
	@Autowired
	public RegisterInfoService registerInfoService;
	@Autowired
	public AwardBindRelService awardBindRelService;
	@Autowired
	public AwardDetailService awardDetailService;
	@Autowired
	private AwardDetailMapper awardDetailMapper;

	@Override
	public void onMessage(Message message) {
		CreditAuthActivityMessage cAAMessage = JSONObject.parseObject(message.getBody(),
				CreditAuthActivityMessage.class);
		if (cAAMessage == null) {
			LOGGER.info("creditAuthActivityMessage null error...");
			return;
		}
		LOGGER.info("creditAuthActivityMessage:" + GsonUtils.toJson(cAAMessage));
		BigDecimal creditAuthMmount = BigDecimal.ZERO;
		if (null != cAAMessage.getAmount()) {
			creditAuthMmount = cAAMessage.getAmount();
		}
		if (!cAAMessage.getIsException() && !cAAMessage.getIsUpdate() && cAAMessage.getSource() == 0
				&& creditAuthMmount.compareTo(BigDecimal.ZERO) > 0) {
			try {
				Response response = registerInfoService.customerIsFirstCredit(cAAMessage.getCustomerId().toString());
				LOGGER.info("用户获得额度时 response.status=" + response.getStatus());
				if (null != response && "1".equals(response.getStatus())) {//判断该用户是否是第一次活动额度
					Map<String, Object> resultMap = (Map<String, Object>) response.getData();
					LOGGER.info("用户获得额度时 response.data="+(Map<String, Object>) response.getData());
					String isFirstCredit = (String) resultMap.get("isFirstCredit");
					String userId = (String) resultMap.get("userId");//被邀请人的userId
					if (StringUtils.equals(isFirstCredit, "true")) {// 如果该用户是第一次获取额度则奖励给他的邀请人
						AwardActivityInfoVo aInfoVo = awardActivityInfoService.getActivityByName(ActivityName.INTRO);
						LOGGER.info("用户获得额度时 活动aInfoVo="+aInfoVo);
						if (null != aInfoVo) {
							Date aEndDate = DateFormatUtil.string2date(aInfoVo.getaEndDate(), "yyyy-MM-dd HH:mm:ss");
							int falge = aEndDate.compareTo(new Date());
							if (falge > 0) {
								//获取邀请人的信息
								List<AwardBindRel> awardBindRel = awardBindRelService.getAllByInviterUserId(userId);
								LOGGER.info("用户获得额度时 获取邀请人的信息awardBindRel="+awardBindRel);
								if(null !=awardBindRel && awardBindRel.size()>0){
									//判断在当前活动下邀请人是否已经获得了被邀请人的奖励
									Map<String, Object> parMap2=new HashMap<>();
									parMap2.put("userId", awardBindRel.get(0).getUserId());
//									parMap2.put("activityId",awardBindRel.get(0).getActivityId());
									parMap2.put("orderId",userId);
									parMap2.put("type","0");
									int result=awardDetailMapper.isAwardSameUserId(parMap2);//判断在当前活动下邀请人是否已经获得了被邀请人的奖励
									LOGGER.info("用户获得额度时 判断邀请人是否已经获得了被邀请人的奖励(大于0为已经获得)result="+result);
									if(0==result){
										//获取当前月的第一天和最后一天的时间
										SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
										//获取当前月第一天
										Calendar c = Calendar.getInstance();    
										c.add(Calendar.MONTH, 0);
										c.set(Calendar.DAY_OF_MONTH,1);
										String first = format.format(c.getTime());
										String firstDay=first+" 00:00:00";
										String nowTime=DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD_HH_MM_SS);
										if(null !=awardBindRel && awardBindRel.size()>0){
											AwardDetailDto awardDetailDto=new AwardDetailDto();
											awardDetailDto.setActivityId(aInfoVo.getId());
											awardDetailDto.setUserId(awardBindRel.get(0).getUserId());
											awardDetailDto.setRealName(awardBindRel.get(0).getName());
											awardDetailDto.setMobile(awardBindRel.get(0).getMobile());
											awardDetailDto.setOrderId(userId);
											awardDetailDto.setType(new Byte("0"));
											awardDetailDto.setStatus(new Byte("0"));
											awardDetailDto.setCreateDate(new Date());
											awardDetailDto.setUpdateDate(new Date());
											
											Map<String, Object> parMap=new HashMap<>();
											parMap.put("userId", awardBindRel.get(0).getUserId());
											parMap.put("type", "0");
											parMap.put("applyDate1", DateFormatUtil.string2date(firstDay, "yyyy-MM-dd HH:mm:ss"));
											parMap.put("applyDate2", DateFormatUtil.string2date(nowTime, "yyyy-MM-dd HH:mm:ss"));
											BigDecimal amountAward=awardDetailMapper.queryAmountAward(parMap);//已经获得的奖励金额
											LOGGER.info("用户获得额度时 已经获得的奖励金额amountAward="+amountAward);
											if(amountAward == null){
												amountAward = new BigDecimal(0);
											}
											BigDecimal  awardAmont=new BigDecimal(aInfoVo.getAwardAmont());//即将获得的奖励金额
											BigDecimal amount=awardAmont.add(amountAward);
											if(new BigDecimal("800").compareTo(amount)>=0){//总奖励金额小于800，直接插入记录
												awardDetailDto.setTaxAmount(new BigDecimal("0"));
												awardDetailDto.setAmount(awardAmont);
												awardDetailService.addAwardDetail(awardDetailDto);
											}else if(new BigDecimal("800").compareTo(amountAward)>0 && new BigDecimal("800").compareTo(amount)<0){
												BigDecimal more=amount.subtract(new BigDecimal("800"));
												//扣除20%个人所得税后的奖励金额
												BigDecimal  awardAmont2=awardAmont.subtract(more.multiply(new BigDecimal("0.2")));
												awardDetailDto.setTaxAmount(more.multiply(new BigDecimal("0.2")));
												awardDetailDto.setAmount(awardAmont2);
												awardDetailService.addAwardDetail(awardDetailDto);
											}else if(new BigDecimal("800").compareTo(amountAward)<=0){
												awardDetailDto.setTaxAmount(awardAmont.multiply(new BigDecimal("0.2")));
												awardDetailDto.setAmount(awardAmont.multiply(new BigDecimal("0.8")));
												awardDetailService.addAwardDetail(awardDetailDto);
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (BusinessException e) {
				LOGGER.error("customerId 用户获得额度时：customerId="+cAAMessage.getCustomerId().toString());
			}

		}
	}

}
