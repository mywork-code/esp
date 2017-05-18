package com.apass.esp.service.activity;

import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.PaymentType;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.AwardBindRelStatisticVo;
import com.apass.esp.domain.vo.AwardDetailVo;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.BeanUtils;
import com.apass.esp.utils.ResponsePageIntroStaticBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AwardDetailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwardDetailService.class);

	@Autowired
	private AwardDetailMapper awardDetailMapper;

	@Autowired
	private AwardBindRelMapper wihdrawBindRelMapper;

	@Autowired
	private TxnInfoMapper txnInfoMapper;

	@Autowired
	private OrderService orderService;


	/**
	 * 分页统计转介绍数据
	 * @return
	 */
	public ResponsePageIntroStaticBody<AwardBindRelStatisticVo> pageBindRelStatistic(ActivityBindRelStatisticQuery query) throws BusinessException {
	    ResponsePageIntroStaticBody<AwardBindRelStatisticVo> respBody = new ResponsePageIntroStaticBody<>();
		List<AwardBindRelStatistic> list = wihdrawBindRelMapper.selectBindRelStatistic(query);
                Map<String, Object> maps = getAllSum(query);
		List<AwardBindRelStatisticVo> result = new ArrayList<>();
		for(AwardBindRelStatistic rs : list){
			AwardBindRelStatisticVo vo = new AwardBindRelStatisticVo();
			vo.setMobile(rs.getMobile());
			vo.setInviteNum(rs.getTotalInviteNum());
			//查询返现金额
			List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(rs.getUserId());
			BigDecimal bankAmt = BigDecimal.ZERO;
			BigDecimal creditAmt = BigDecimal.ZERO;
			BigDecimal rebateAmt = BigDecimal.ZERO;
			for(AwardDetail awardDetail : awardDetails){
			    if(awardDetail.getType() == AwardActivity.AWARD_TYPE.GAIN.getCode()){
                                rebateAmt = rebateAmt.add(awardDetail.getAmount());//反现
                            }   
                            String orderId = awardDetail.getOrderId();
                            if(StringUtils.isNotBlank(orderId)){
                                OrderInfoEntity order = orderService.selectByOrderId(orderId);
                                if(order != null){
                                    if (PaymentType.CARD_PAYMENT.getCode().equals(order.getPayType())) {
                                        // 银行卡全卡支付
                                        bankAmt = bankAmt.add(order.getOrderAmt());
                                    } else if (PaymentType.CREDIT_PAYMENT.getCode().equals(order.getPayType())) {
                                        creditAmt = creditAmt.add(order.getOrderAmt());
                                    }
                                }
                            }
			}
			vo.setRebateAmt(rebateAmt);
			vo.setBankAmt(bankAmt);
			vo.setCreditAmt(creditAmt);
			result.add(vo);
			
		}
		if(CollectionUtils.isNotEmpty(list)){
		    respBody.setTotal(wihdrawBindRelMapper.countBindRelByGroup(query));
		}
		respBody.setBankAmtSum((BigDecimal)maps.get("bankAmtSum"));
		respBody.setCreditAmtSum((BigDecimal)maps.get("creditAmtSum"));
		respBody.setRebateAmtSum((BigDecimal)maps.get("rebateAmtSum"));
		respBody.setRows(result);
		respBody.setAllCount((Integer)maps.get("allCount"));
		respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

		return respBody;
    }

     private Map<String,Object> getAllSum(ActivityBindRelStatisticQuery query) throws BusinessException {
         Map<String,Object> resultMap = Maps.newHashMap();
         List<AwardBindRelStatistic> list = wihdrawBindRelMapper.selectAllBindRelStatistic(query);
         BigDecimal bankAmtSum = BigDecimal.ZERO;
         BigDecimal creditAmtSum = BigDecimal.ZERO;
         BigDecimal rebateAmtSum = BigDecimal.ZERO;
         Integer allCount = 0;
         List<AwardBindRelStatisticVo> result = new ArrayList<>();
         for(AwardBindRelStatistic rs : list){
                 allCount += rs.getTotalInviteNum();
                 AwardBindRelStatisticVo vo = new AwardBindRelStatisticVo();
                 //查询返现金额
                 List<AwardDetail> awardDetails = awardDetailMapper.queryAwardDetail(rs.getUserId());
                 BigDecimal bankAmt = BigDecimal.ZERO;
                 BigDecimal creditAmt = BigDecimal.ZERO;
                 BigDecimal rebateAmt = BigDecimal.ZERO;
                 for(AwardDetail awardDetail : awardDetails){
                     if(awardDetail.getType() == AwardActivity.AWARD_TYPE.GAIN.getCode()){
                         rebateAmt = rebateAmt.add(awardDetail.getAmount());//反现
                     }   
                     String orderId = awardDetail.getOrderId();
                     if(StringUtils.isNotBlank(orderId)){
                         OrderInfoEntity order = orderService.selectByOrderId(orderId);
                         if(order != null){
                             if (PaymentType.CARD_PAYMENT.getCode().equals(order.getPayType())) {
                                 // 银行卡全卡支付
                                 bankAmt = bankAmt.add(order.getOrderAmt());
                             } else if (PaymentType.CREDIT_PAYMENT.getCode().equals(order.getPayType())) {
                                 creditAmt = creditAmt.add(order.getOrderAmt());
                             }
                         }
                     }
                 }
                 rebateAmtSum = rebateAmtSum.add(rebateAmt);
                 bankAmtSum = bankAmtSum.add(bankAmt);
                 creditAmtSum = creditAmtSum.add(creditAmt);
                 result.add(vo);
                 
         }
         resultMap.put("rebateAmtSum", rebateAmtSum);
         resultMap.put("bankAmtSum", bankAmtSum);
         resultMap.put("creditAmtSum", creditAmtSum);
         resultMap.put("allCount", allCount);
         
         return resultMap;
     }

    /**
	 * 添加明细记录
	 * 
	 * @param awardDetailDto
	 * @return
	 */
    @Transactional(rollbackFor=Exception.class) 
	public int addAwardDetail(AwardDetailDto awardDetailDto) {
		AwardDetail awardDetail = new AwardDetail();
		BeanUtils.copyProperties(awardDetail, awardDetailDto);
		return awardDetailMapper.insert(awardDetail);
	}

	/**
	 * 根据status，type查询转活动金额明细
	 * 
	 * @param type
	 * @param status
	 * @return
	 */
	public List<AwardDetailDto> queryAwardDetailByStatusAndType(byte type, byte status) {
		List<AwardDetail> list = awardDetailMapper.queryAwardDetailByStatusAndType(status, type);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		List<AwardDetailDto> awardDetailDtoList = new ArrayList<AwardDetailDto>();
		for (AwardDetail awardDetail : list) {
			AwardDetailDto awardDetailDto = new AwardDetailDto();
			BeanUtils.copyProperties(awardDetailDto, awardDetail);
			awardDetailDtoList.add(awardDetailDto);
		}
		return awardDetailDtoList;
	}

	/**
	 * 更新
	 * 
	 * @param awardDetailDto
	 * @return
	 */
    @Transactional(rollbackFor=Exception.class) 
	public int updateAwardDetail(AwardDetailDto awardDetailDto) {
		AwardDetail awardDetail = new AwardDetail();
		BeanUtils.copyProperties(awardDetail, awardDetailDto);
		return awardDetailMapper.updateByPrimaryKeySelective(awardDetail);
	}

	/**
	 * delete
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int delete(Long id){
		return awardDetailMapper.deleteByPrimaryKey(id);
	}
	
	public void get(){
		
	}
	
	/**
	 * 统计在某一时间段内，已放款和预计放款总金额
	 * @param days
	 * @return
	 */
	public AwardDetailVo querySumAmountGroupByTypeStatus(int days){
 		ActivityBindRelStatisticQuery query = new ActivityBindRelStatisticQuery();
		//在当前时间的基础上减去days
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, days);
		query.setStartCreateDate(DateFormatUtil.dateToString(cal.getTime(),""));
		List<AwardDetail> detailList = awardDetailMapper.querySumAmountGroupByTypeStatus(query);
		AwardDetailVo v = new AwardDetailVo();
		if(!CollectionUtils.isEmpty(detailList)){
			for (AwardDetail a : detailList) {
				//type值为1,提取
				if(a.getType() == 1){
					v.setLoadAmount(a.getAmount());
				}
				
				if(a.getType() == 0){
					v.setExpectLoadAmount(a.getAmount());
				 }
			}
		}
		return v;
	}
}
