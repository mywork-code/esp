package com.apass.esp.service.coupon;

import java.util.List;

import javax.xml.ws.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.mapper.ProCouponRelMapper;
/**
 * 优惠券与活动关系Service
 * @author aopai
 */
@Component
public class ProCouponRelService {
	@Autowired
	private ProCouponRelMapper proCouponRelMapper;
	/**
	 * 根据活动id查询与该活动相关的优惠券
	 * @param proActivityId
	 * @return
	 */
	public List<ProCouponRel> queryProCouponRelListByActivityId(Long proActivityId){
		return proCouponRelMapper.queryProCouponRelListByActivityId(proActivityId);
	};
}
