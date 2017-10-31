package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponRelMapper extends GenericMapper<ProCouponRel, Long> {
	/**
	 * 根据活动和优惠券的Id，查找对应的关系表中的记录
	 * @param couponRel
	 * @return
	 */
	ProCouponRel getRelByActivityIdAndCouponId(ProCouponRelQuery couponRel);
	/**
	 * 根据活动的Id，查询对应的所属活动下的优惠券的记录
	 * @param activityId
	 * @return
	 */
	List<ProCouponRel> getCouponByActivityId(ProCouponRelQuery couponRel);
}