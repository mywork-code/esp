package com.apass.esp.mapper;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

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
   * 查看在当前活动下是否已被邀请
   * @param AwardBindRel
   * @return
   */
  Integer selectByMobileAndActivityId(AwardBindRel abr);
  /**
   * 统计查询同一用户邀请人数
   *
   * @return
   */
  List<AwardBindRelStatistic> selectBindRelStatistic(ActivityBindRelStatisticQuery query);

  Integer countBindRelByGroup(ActivityBindRelStatisticQuery query);

  /**
   * 查询被邀请人的记录
   *
   * @param userId
   * @return
   */
  AwardBindRel getByInviterUserId(String userId,int activityId);
}
