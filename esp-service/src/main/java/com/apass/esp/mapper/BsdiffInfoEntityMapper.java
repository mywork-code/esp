package com.apass.esp.mapper;

import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.BsdiffQuery;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

public interface BsdiffInfoEntityMapper extends GenericMapper<BsdiffInfoEntity, Long> {


    List<BsdiffInfoEntity> selectAllBsdiff();

    BsdiffInfoEntity selectByEntityQuery(BsdiffInfoEntity entity);
    BsdiffInfoEntity selectBsdiffInfoByVo(BsdiffQuery query);
}