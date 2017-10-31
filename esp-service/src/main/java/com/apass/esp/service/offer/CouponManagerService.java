package com.apass.esp.service.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.vo.ProCouponVo;
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
	
	@Autowired
	private CouponRelService couponRelService;
	
	/**
	 * 根据活动的Id，获取优惠券
	 * @param activityId
	 * @return
	 */
	public List<ProCoupon> getCouponsByActivityId(String activityId){
		List<ProCoupon> couponList = new ArrayList<ProCoupon>();
		List<ProCouponRel> relList = couponRelService.getCouponRelList(activityId);
		for (ProCouponRel rel : relList) {
			ProCoupon pro = couponMapper.selectByPrimaryKey(rel.getCouponId());
			couponList.add(pro);
		}
		return couponList;
	}
	/**
	 * 根据活动的Id，获取优惠券(过滤优惠券剩余数量为0的优惠券)
	 * @param activityId
	 * @return
	 */
	public List<ProCoupon> getCouponListByActivityId(String activityId){
		List<ProCoupon> couponList = new ArrayList<ProCoupon>();
		List<ProCouponRel> relList = couponRelService.getCouponRelList(activityId);
		for (ProCouponRel rel : relList) {
			 if(rel.getRemainNum()>0){
				ProCoupon pro = couponMapper.selectByPrimaryKey(rel.getCouponId());
				couponList.add(pro);
			 }
		}
		return couponList;
	}
	public List<ProCouponVo> getCouponVos(String activityId){
		List<ProCouponVo> couponList = new ArrayList<ProCouponVo>();
		List<ProCoupon> coupons = getCouponsByActivityId(activityId);
		for (ProCoupon proCoupon : coupons) {
			ProCouponVo vo  = new ProCouponVo();
			vo.setId(proCoupon.getId());
			vo.setName(proCoupon.getName());
			vo.setCouponSill(proCoupon.getCouponSill());
			vo.setDiscountAmonut(proCoupon.getDiscountAmonut());
			couponList.add(vo);
		}
		return couponList;
	}

}
