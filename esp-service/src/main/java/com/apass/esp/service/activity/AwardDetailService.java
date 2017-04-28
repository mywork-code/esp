package com.apass.esp.service.activity;

import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.domain.entity.bill.TxnInfoEntity;
import com.apass.esp.domain.enums.AwardActivity;
import com.apass.esp.domain.enums.TxnTypeCode;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.AwardBindRelStatisticVo;
import com.apass.esp.mapper.AwardBindRelMapper;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.mapper.TxnInfoMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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


	/**
	 * 分页统计转介绍数据
	 * @return
	 */
	public ResponsePageBody<AwardBindRelStatisticVo> pageBindRelStatistic(ActivityBindRelStatisticQuery query) {
		ResponsePageBody<AwardBindRelStatisticVo> respBody = new ResponsePageBody<>();
		List<AwardBindRelStatistic> list = wihdrawBindRelMapper.selectBindRelStatistic(query);
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
					rebateAmt = rebateAmt.add(awardDetail.getAmount());
				}
				Long mainOrderId = awardDetail.getMainOrderId();
				if(mainOrderId != null){
					List<TxnInfoEntity> txnInfoEntityList = txnInfoMapper.selectByOrderId(mainOrderId);
					for(TxnInfoEntity txn : txnInfoEntityList){
						if(TxnTypeCode.SF_CODE.getCode().equals(txn.getTxnType())
								|| TxnTypeCode.KQEZF_CODE.getCode().equals(txn.getTxnType())){
							bankAmt = bankAmt.add(txn.getTxnAmt());
						} else if (TxnTypeCode.XYZF_CODE.getCode().equals(txn.getTxnType())) {
							creditAmt = creditAmt.add(txn.getTxnAmt());
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
		respBody.setRows(result);
		respBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

		return respBody;
	}
}
