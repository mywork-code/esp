package com.apass.esp.service.offer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.esp.mapper.ProCouponRelMapper;
/**
 * 
 * @author pyc
 * @email  E-mail: pengyingchao@apass.cn
 * @version V2.0
 * @createTime：2017年10月30日 上午10:52:20 
 * @description
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class CouponRelService {

	@Autowired
	private ProCouponRelMapper couponRelMapper;
	
	/**
	 * 根据活动的Id，获取对应的关系表中的信息
	 * @param activityId
	 * @return
	 */
	public List<ProCouponRel> getCouponRelList(String activityId){
		List<ProCouponRel> relList = couponRelMapper.getCouponByActivityId(new ProCouponRelQuery(Long.parseLong(activityId)));
		return relList;
	}
	/**
	 * 根据活动和优惠券的Id，查找对应的关系表中的记录
	 * @param couponRel
	 * @return
	 */
	public ProCouponRel getRelByActivityIdAndCouponId(Long activityId,Long couponId){
		ProCouponRelQuery couponRel=new ProCouponRelQuery();
		couponRel.setActivityId(activityId);
		couponRel.setCouponId(couponId);
		return couponRelMapper.getRelByActivityIdAndCouponId(couponRel);
	};
	/**
	 * 获取优惠券剩余数量大于0的优惠券列表
	 * @return
	 */
	public List<ProCouponRel> getCouponList(){
		return couponRelMapper.getCouponList();
	};
}
