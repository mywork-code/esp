package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.gfb.framework.mybatis.GenericMapper;

/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponRelMapper extends GenericMapper<ProCouponRel, Long> {
	/**
	 * 根据活动id查询与该活动相关的优惠券
	 * @param proActivityId
	 * @return
	 */
	List<ProCouponRel> queryProCouponRelListByActivityId(Long proActivityId);
}
