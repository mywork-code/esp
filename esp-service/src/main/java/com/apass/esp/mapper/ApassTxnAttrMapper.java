package com.apass.esp.mapper;

import com.apass.esp.domain.entity.ApassTxnAttr;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

public interface ApassTxnAttrMapper extends GenericMapper<ApassTxnAttr, Long> {

    public ApassTxnAttr getApassTxnAttrByTxnId(@Param("txnId") long txnId);

}