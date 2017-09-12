package com.apass.esp.mapper;

import com.apass.esp.domain.entity.Kvattr;
import com.apass.esp.domain.entity.WeexInfoEntity;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeexInfoEntityMapper extends GenericMapper<WeexInfoEntity, Long> {

    List<WeexInfoEntity> queryWeexInfoList();

    Integer updateWeexJs(WeexInfoEntity weexInfoEntity);
}