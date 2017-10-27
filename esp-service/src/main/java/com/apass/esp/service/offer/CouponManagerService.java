package com.apass.esp.service.offer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.mapper.ProCouponMapper;

/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年10月27日 上午11:53:19 
 * @description 优惠券Service
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class CouponManagerService {

	@Autowired
	private ProCouponMapper couponMapper;
	/**
	 * 根据活动的Id，获取优惠券
	 * @param activityId
	 * @return
	 */
	public List<ProCoupon> getCouponsByActivityId(String activityId){
		
		return null;
	}
	
	
}
