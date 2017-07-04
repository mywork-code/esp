package com.apass.esp.mapper;

import com.apass.esp.third.party.jd.entity.base.JdGategory;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

public interface JdGategoryMapper extends GenericMapper<JdGategory, Long> {
    JdGategory getCateGoryByCatId(@Param("catId") long catId);
}