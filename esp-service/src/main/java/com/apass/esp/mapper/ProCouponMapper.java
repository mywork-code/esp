package com.apass.esp.mapper;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.gfb.framework.mybatis.GenericMapper;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

import java.util.Map;

/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponMapper extends GenericMapper<ProCoupon, Long> {
    Pagination<ProCoupon> pageList(Map<String, Object> paramMap);
}
