package com.apass.esp.mapper;

import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/8/21.
 */
public interface ZYPriceCollecEntityMapper extends GenericMapper<ZYPriceCollecEntity,Long>{

    Integer countByQHRewardType(@Param("qhRewardType") String qhRewardType,
                                @Param("companyName") String companyName,
                                @Param("activityId") String activityId);

    List<ZYPriceCollecEntity> selectAllCollec(Map<String,Object> paramMap);

    ZYPriceCollecEntity selectByEmpTel(@Param("empTel")String empTel,
                                       @Param("activityId")String activityId);
}
