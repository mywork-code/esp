package com.apass.esp.service.activity;

import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.AwardBindRelStatisticVo;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.service.order.OrderService;
import com.apass.esp.utils.BeanUtils;
import com.apass.esp.utils.ResponsePageIntroStaticBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		BigDecimal bankAmtSum = BigDecimal.ZERO;
                BigDecimal creditAmtSum = BigDecimal.ZERO;
                BigDecimal rebateAmtSum = BigDecimal.ZERO;
                Integer allCount = 0;
		List<AwardBindRelStatisticVo> result = new ArrayList<>();
		for(AwardBindRelStatistic rs : list){
		        allCount += rs.getTotalInviteNum();
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
				                    OrderInfoEntity order = orderService.selectByOrderId(orderId);
                            if(order != null && order.getMainOrderId() != null){
                                    List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(order.getMainOrderId());
                                    for(TxnInfoEntity txn : txnInfoEntityList){
                                            if(TxnTypeCode.SF_CODE.getCode().equals(txn.getTxnType())
                                                            || TxnTypeCode.KQEZF_CODE.getCode().equals(txn.getTxnType())){
                                                    bankAmt = bankAmt.add(txn.getTxnAmt());//银行卡支付
                                            } else if (TxnTypeCode.XYZF_CODE.getCode().equals(txn.getTxnType())) {
                                                    creditAmt = creditAmt.add(txn.getTxnAmt());//信用支付
                                            }
                                    }
                            }
			}
			rebateAmtSum = rebateAmtSum.add(rebateAmt);
			bankAmtSum = bankAmtSum.add(bankAmt);
			creditAmtSum = creditAmtSum.add(creditAmt);
			vo.setRebateAmt(rebateAmt);
			vo.setBankAmt(bankAmt);
			vo.setCreditAmt(creditAmt);
			result.add(vo);
			
		}
		if(CollectionUtils.isNotEmpty(list)){
		    respBody.setTotal(wihdrawBindRelMapper.countBindRelByGroup(query));
		}
		respBody.setBankAmtSum(bankAmtSum);
		respBody.setCreditAmtSum(creditAmtSum);
		respBody.setRebateAmtSum(rebateAmtSum);
		respBody.setRows(result);
		respBody.setAllCount(allCount);
		respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

		return respBody;
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

}
