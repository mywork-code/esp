package com.apass.esp.mapper;

import com.apass.esp.domain.entity.AwardBindRel;
import com.apass.esp.domain.extentity.AwardBindRelStatistic;
import com.apass.esp.domain.query.ActivityBindRelStatisticQuery;
import com.apass.esp.domain.vo.ActivityDetailStatisticsVo;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

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
   * 查看当前手机号码是否已被邀请
   * @param AwardBindRel
   * @return
   */
  Integer selectByMobile(AwardBindRel abr);
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
  AwardBindRel getByInviterUserId(@Param("userId") String userId,@Param("activityId") int activityId);
  /**
   * 根据userId查询被邀请人的记录
   * @param userId
   * @return
   */
  List<AwardBindRel> getAllByInviterUserId(@Param("userId") String userId);

  /**
   * 统计查询同一用户邀总人数
   *
   * @return
   */
  List<AwardBindRelStatistic> selectAllBindRelStatistic(ActivityBindRelStatisticQuery query);
  
  /**
   * 统计查询在某一时间内邀请的总人数
   */
  Integer getInviterUserCountByTime(ActivityBindRelStatisticQuery query);

  List<AwardBindRel> selectByInviterUserId(@Param("userId") String userId);
  /**
   * 某段时间内某活动下的推荐人总数
   */
  Integer refereeNums(ActivityBindRelStatisticQuery query);   
  /**
   * 某段时间内某活动下的拉新总数
   */
  Integer newNums(ActivityBindRelStatisticQuery query); 
  /**
   *  查询某段时间内某活动下的推荐人及推荐人拉新人数 
   */
  List<ActivityDetailStatisticsVo> getUserIdListByActivityId(ActivityBindRelStatisticQuery query);
}
