package com.apass.esp.mapper;

import com.apass.esp.domain.entity.PAUser;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PAUserMapper extends GenericMapper<PAUser, Long> {

	PAUser selectUserByUserId(@Param("userId")String userId);

	List<PAUser> selectUserByRangeDate(Map<String, Object> paramMap);

	Integer getCountZYCollecByStartandEndTime(Map<String, Object> paramMap);

	Integer getCountRegisterByStartandEndTime(Map<String, Object> paramMap);

	Integer getCountRegisterSuccessByStartandEndTime(Map<String, Object> paramMap);
}

