package com.apass.esp.mapper;


import com.apass.esp.domain.entity.JdGoodSalesVolume;
import com.apass.gfb.framework.mybatis.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface JdGoodSalesVolumeMapper extends GenericMapper<JdGoodSalesVolume, Long> {
    int updateJdGoodSalesVolumeByGoodsId(@Param("goodsId") Long goodsId, @Param("salesNum") Integer salesNum, @Param("updateDate") Date updateDate, @Param("oriSalesNum") Integer oriSalesNum);

    JdGoodSalesVolume getJdGoodSalesVolumeByGoodsId(@Param("goodsId") Long goodsId);
}
