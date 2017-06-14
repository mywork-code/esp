package com.apass.esp.mapper;

import com.apass.esp.domain.entity.Kvattr;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KvattrMapper extends GenericMapper<Kvattr, Long> {
    public List<Kvattr> getBySource(@Param("source") String source);
}