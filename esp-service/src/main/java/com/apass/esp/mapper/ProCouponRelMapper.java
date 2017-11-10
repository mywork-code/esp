package com.apass.esp.mapper;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 获取优惠券剩余数量大于0的优惠券列表
	 * @return
	 */
	List<ProCouponRel> getCouponList();

	List<ProCouponRel> getCouponRelListByActivityIdBanch(Map<String, Object> activityIds);
}
