package com.apass.esp.mapper;

import com.apass.esp.domain.entity.BsdiffInfoEntity;
import com.apass.esp.domain.entity.BsdiffQuery;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.gfb.framework.mybatis.GenericMapper;

import java.util.List;

public interface BsdiffInfoEntityMapper extends GenericMapper<BsdiffInfoEntity, Long> {


    List<BsdiffInfoEntity> selectAllBsdiff();

<<<<<<< HEAD
    BsdiffInfoEntity selectByEntityQuery(BsdiffInfoEntity entity);
=======
    BsdiffInfoEntity selectBsdiffInfoByVo(BsdiffQuery query);
>>>>>>> 17a180e9ec8c78cbc3a05bf77778d15a306e8ae9
}