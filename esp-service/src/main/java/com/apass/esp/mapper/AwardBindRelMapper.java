package com.apass.esp.mapper;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface AwardBindRelMapper extends GenericMapper<AwardBindRel, Long> {

	/**
	 * 查询邀请人数量
	 * 
	 * @param valueOf
	 * @return
	 */
	Integer selectCountByUserId(Long valueOf);

	/**
	 * 查看是否已被邀请
	 * 
	 * @param moblie
	 * @return
	 */
	Integer selectCountByInviteMobile(String moblie);

	/**
	 * 查询被邀请人的记录
	 * 
	 * @param userId
	 * @return
	 */
	AwardBindRel getByInviterUserId(String userId);
}
