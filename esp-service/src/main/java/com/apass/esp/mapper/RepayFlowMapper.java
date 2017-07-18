package com.apass.esp.mapper;

import com.apass.esp.domain.entity.RepayFlow;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface RepayFlowMapper extends GenericMapper<RepayFlow, Long>{
	
	RepayFlow queryLatestSuccessOrderInfo(Long userId);
	
}
