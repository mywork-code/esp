package com.apass.esp.service.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.domain.dto.activity.AwardDetailDto;
import com.apass.esp.domain.entity.AwardDetail;
import com.apass.esp.mapper.AwardDetailMapper;
import com.apass.esp.utils.BeanUtils;

@Service
public class AwardDetailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwardDetailService.class);

	@Autowired
	public AwardDetailMapper awardDetailMapper;

	/**
	 * 添加明细记录
	 * 
	 * @param awardDetailDto
	 * @return
	 */
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
	public int updateAwardDetail(AwardDetailDto awardDetailDto) {
		AwardDetail awardDetail = new AwardDetail();
		BeanUtils.copyProperties(awardDetail, awardDetailDto);
		return awardDetailMapper.updateByPrimaryKeySelective(awardDetail);
	}

}
