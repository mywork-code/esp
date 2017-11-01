package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.CouponStatus;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.domain.vo.ProMyCouponVo;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

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
	
	@Autowired
	private ProCouponMapper couponMapper;
	
	@Autowired
	private ActivityCfgService activityCfgService;
	
	/**
	 * 点击领取优惠券
	 * @param userId 用户Id
	 * @param couponId 优惠券Id
	 * @param activityId 活动Id 可为空
	 * @throws BusinessException 
	 */
	public int giveCouponToUser(MyCouponVo vo) throws BusinessException{
		/**
		 * 判断活动是否已经结束
		 */
	    ProActivityCfg activityCfg = activityCfgService.getById(vo.getActivityId());
	    if(null==activityCfg){
	    	throw new BusinessException("领取失败，活动已经结束!");
	    }
	    if(ActivityStatus.PROCESSING != activityCfgService.getActivityStatus(activityCfg)){
	    	throw new BusinessException("领取失败，活动已经结束!");
	    }
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
		if(couponsNum >= limitNum){
			throw new BusinessException("您已领取该券!");
		}
		
		couponRel.setRemainNum(couponRel.getRemainNum() - 1);
		int count = couponRelMapper.updateByPrimaryKeySelective(couponRel);
		if(count > 0){
			ProMyCoupon coupon = couponVoToPojo(vo);
			return myCouponMapper.insertSelective(coupon);
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
	 * 根据用户的Id，获取用户的未使用、已使用、已过期的优惠券信息
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getCoupons(String userId){
		/**
		 * 未使用
		 */
		List<ProMyCouponVo> unUsedList = getCouponsUnused(userId);
		/**
		 * 已使用
		 */
		List<ProMyCouponVo> usedList = getCouponsUsed(userId);
		/**
		 * 已过期
		 */
		List<ProMyCouponVo> expireList = getExpire(userId);
		
		Map<String,Object> params = Maps.newHashMap();
		params.put("unUsed", unUsedList);
		params.put("used",usedList);
		params.put("expire",expireList);
		return params;
	}
	/**
	 * 根据用户的Id和优惠券Id查询对应的信息
	 * @param query
	 * @return
	 */
	public List<ProMyCoupon> getCouponByUserIdAndCouponId(Long userId,Long couponId){
		ProMyCouponQuery query=new ProMyCouponQuery();
		if(null !=userId && null !=couponId){
			query.setUserId(userId);
			query.setCouponId(couponId);
			return myCouponMapper.getCouponByUserIdAndCouponId(query);
		}
		return null;
	};

	/**
	 * 根据用户的Id，获取用户未使用的优惠券
	 * @param userId
	 * @return
	 *  未使用的券
	 * 条件： 
	 *   1.首先是status = 'N'
	 *   2.当前日期应该小于end_date
	 */
	public List<ProMyCouponVo> getCouponsUnused(String userId){
		Date now = new Date();
		Long userID = Long.parseLong(userId);
		List<ProMyCoupon> list = myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,now,"N"));
		return getVoByPos(list);   
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * *
	 * 已使用的券
	 * 1.status = 'Y'
	 */
	public List<ProMyCouponVo> getCouponsUsed(String userId){
		Long userID = Long.parseLong(userId);
		List<ProMyCoupon> list =  myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,null,"Y"));
		return getVoByPos(list);
	}
	
	/**
	 * @param userId
	 * @return
	 * 已过期的券
	 * 1.首先是status = 'N'
	 * 2.当前日期应该大于end_date
	 */
	public List<ProMyCouponVo> getExpire(String userId){
		Date now = new Date();
		Long userID = Long.parseLong(userId);
		List<ProMyCoupon> list = myCouponMapper.getCouponByStatusAndDate(new ProMyCouponQuery(userID,"N",now));
		return getVoByPos(list);
	}
	
	/**
	 * List po 2 vo
	 * @param list
	 * @return
	 */
	public List<ProMyCouponVo> getVoByPos(List<ProMyCoupon> list){
		List<ProMyCouponVo> voList = new ArrayList<>();
		for (ProMyCoupon p : list) {
			voList.add(getVoByPo(p));
		}
		return voList;
	}
	
	/**
	 * po 2 vo
	 * @param p
	 * @return
	 */
	public ProMyCouponVo getVoByPo(ProMyCoupon p){
		ProMyCouponVo vo = new ProMyCouponVo();
		vo.setId(p.getId());
		if(null != p.getCouponRelId()){
			ProCouponRel rel = couponRelMapper.selectByPrimaryKey(p.getCouponRelId());
			vo.setActivityId(null != rel ? rel.getProActivityId() : -1L);
		}
		vo.setCouponRelId(p.getCouponRelId());
		vo.setCouponId(p.getCouponId());
		ProCoupon coupon = couponMapper.selectByPrimaryKey(p.getCouponId());
		vo.setCategoryId1(coupon.getCategoryId1());
		vo.setCategoryId2(coupon.getCategoryId2());
		vo.setSimilarGoodsCode(coupon.getSimilarGoodsCode());
		vo.setType(coupon.getType());
		vo.setCouponSill(coupon.getCouponSill());
		vo.setDiscountAmonut(coupon.getDiscountAmonut());
		vo.setCouponName(null != coupon ? coupon.getName():"");
		vo.setEndDate(DateFormatUtil.dateToString(p.getEndDate(),""));
		vo.setRemarks(p.getRemarks());
		vo.setStartDate(DateFormatUtil.dateToString(p.getStartDate(),""));
		vo.setStatus(p.getStatus());
		vo.setTelephone(p.getTelephone());
		vo.setUserId(p.getUserId());
		return vo;
	}


	public void updateStatus(String status,Long userId,Long couponId){
		myCouponMapper.updateStatusByUserIdAndCouponId(status,userId,couponId);
	}

	/**
	 * 批量插入优惠券
	 * @param lists
     */
	public void insertProMyCoupoBach(List<ProMyCoupon> lists) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("proMyCouponList",lists);
		myCouponMapper.insertProMyCoupoBach(paramMap);
	}
	
	/**
	 * 逻辑删除券
	 * @param mycouponId
	 */
	public void deleteMyCoupon(String mycouponId){
		if(StringUtils.isNotBlank(mycouponId)){
			ProMyCoupon myCoupon = myCouponMapper.selectByPrimaryKey(Long.parseLong(mycouponId));
			if(null != myCoupon){
				myCoupon.setStatus(CouponStatus.COUPON_D.getCode());
				myCoupon.setUpdatedTime(new Date());
				myCouponMapper.updateByPrimaryKeySelective(myCoupon);
			}
		}
	}
}
