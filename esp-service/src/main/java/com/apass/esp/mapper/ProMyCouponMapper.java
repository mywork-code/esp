package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProMyCouponMapper extends GenericMapper<ProMyCoupon, Long> {
	
	List<ProMyCoupon> getCouponByUserIdAndRelId(ProMyCouponQuery query);
}
