package com.apass.esp.service.offer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProCoupon;

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
public class MyCouponManagerService {

	/**
	 * 点击领取优惠券
	 * @param userId 用户Id
	 * @param couponId 优惠券Id
	 * @param activityId 活动Id 可为空
	 */
	public void giveCouponToUser(String userId,String couponId,String activityId){
		
		
	}
	
	/**
	 * 根据用户的Id，获取用户所属的优惠券
	 * @param userId
	 * @return
	 */
	public List<ProCoupon> getCouponsByUserId(String userId){
		
		return null;
	}
	
	
}
