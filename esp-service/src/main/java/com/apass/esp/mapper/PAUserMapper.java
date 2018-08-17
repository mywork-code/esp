package com.apass.esp.mapper;

import com.apass.esp.domain.entity.PAUser;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

public interface PAUserMapper extends GenericMapper<PAUser, Long> {

	PAUser selectUserByUserId(@Param("userId")String userId);
}

