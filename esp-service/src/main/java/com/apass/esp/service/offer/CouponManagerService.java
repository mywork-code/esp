package com.apass.esp.service.offer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * 获取优惠券
	 * @param userId
	 */
	public void getCoupon(String userId,String couponId){
		
	}
}
