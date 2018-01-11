package com.apass.esp.mapper;

import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.BsdiffQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

public interface BsdiffInfoEntityMapper extends GenericMapper<BsdiffInfoEntity, Long> {


    List<BsdiffInfoEntity> selectAllBsdiff();

    List<BsdiffInfoEntity> selectBsdiffInfoByVo(BsdiffQuery query);

    List<BsdiffInfoEntity> selectAllBsdiffNewest();

    BsdiffInfoEntity selectMaxBsdiffInfoById(String id);


}