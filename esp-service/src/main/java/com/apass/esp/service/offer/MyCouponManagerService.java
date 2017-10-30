package com.apass.esp.service.offer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.gfb.framework.exception.BusinessException;

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

	
	@Autowired
	private ProMyCouponMapper myCouponMapper;
	
	@Autowired
	private ProCouponRelMapper couponRelMapper;
	
	@Autowired
	private ProActivityCfgMapper activityCfgMapper;
	
	/**
	 * 点击领取优惠券
	 * @param userId 用户Id
	 * @param couponId 优惠券Id
	 * @param activityId 活动Id 可为空
	 * @throws BusinessException 
	 */
	public int giveCouponToUser(MyCouponVo vo) throws BusinessException{
		/**
		 * 首先，根据活动的Id和优惠券的id ,查询此活动和优惠券的关系表信息
		 */
		ProCouponRel couponRel = couponRelMapper.getRelByActivityIdAndCouponId(new ProCouponRelQuery(vo.getActivityId(),vo.getCouponId()));
		if(null == couponRel){
			throw new BusinessException("领取失败!");
		}
		if(couponRel.getRemainNum() == 0){
			throw new BusinessException("领取失败，优惠券数量不足！");
		}
		/**
		 * 限制领取优惠券张数
		 */
		int limitNum = couponRel.getLimitNum();
		/**
		 * 根据用户的Id和活动、优惠券的关系，查询当前活动下某一种券，该用户领取的记录
		 */
		List<ProMyCoupon> myCoupons = myCouponMapper.getCouponByUserIdAndRelId(new ProMyCouponQuery(vo.getUserId(), couponRel.getId()));
		int couponsNum = myCoupons.size();
		/**
		 * 如果用户领取张数，小于限制张数，则可以领取
		 */
		if(couponsNum < limitNum){
			couponRel.setRemainNum(couponRel.getRemainNum() - 1);
			int count = couponRelMapper.updateByPrimaryKeySelective(couponRel);
			if(count > 0){
				ProMyCoupon coupon = couponVoToPojo(vo);
				return myCouponMapper.insertSelective(coupon);
			}
		}
		return 0;
	}
	
	/**
	 * 活动商品列表页面，领取优惠券
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public ProMyCoupon couponVoToPojo(MyCouponVo vo) throws BusinessException{
		
		ProMyCoupon coupon = new ProMyCoupon();
		if(null == vo){
			return null;
		}
		coupon.setUserId(vo.getUserId());
		coupon.setCouponId(vo.getCouponId());
		//根据活动的Id和优惠券的Id，获取活动和券的关系表信息
		ProCouponRel couponRel = couponRelMapper.getRelByActivityIdAndCouponId(new ProCouponRelQuery(vo.getActivityId(),vo.getCouponId()));
		if(null == couponRel){
			throw new BusinessException("领取失败!");
		}
		
		ProActivityCfg activity = activityCfgMapper.selectByPrimaryKey(vo.getActivityId());
		if(null == activity){
			throw new BusinessException("领取失败!");
		}
		coupon.setCouponRelId(couponRel.getId());
		coupon.setStartDate(activity.getStartTime());
		coupon.setEndDate(activity.getEndTime());
		
		coupon.setCreatedTime(new Date());
		coupon.setUpdatedTime(new Date());
		coupon.setStatus("N");
		
		return coupon;
	}
	
	/**
	 * 根据用户的Id，获取用户未使用的优惠券
	 * @param userId
	 * @return
	 *  未使用的券
	 * 条件： 
	 *   1.首先是status = 'N'
	 *   2.当前日期应该小于end_date
	 */
	public List<ProMyCoupon> getCouponsUnused(String userId){
		Date now = new Date();
		Long userID = Long.parseLong(userId);
		return myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,now,"N"));
	}
	/**
	 * 
	 * @param userId
	 * @return
	 * *
	 * 已使用的券
	 * 1.status = 'Y'
	 */
	public List<ProMyCoupon> getCouponsUsed(String userId){
		Long userID = Long.parseLong(userId);
		return myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,null,"Y"));
	}
	
	/**
	 * @param userId
	 * @return
	 * 已过期的券
	 * 1.首先是status = 'N'
	 * 2.当前日期应该大于end_date
	 */
	public List<ProMyCoupon> getExpire(String userId){
		Date now = new Date();
		Long userID = Long.parseLong(userId);
		return myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,"N",now));
	}
}
