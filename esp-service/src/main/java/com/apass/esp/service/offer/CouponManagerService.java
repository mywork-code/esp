package com.apass.esp.service.offer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.Category;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.CouponType;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.domain.vo.ProCouponVo;
import com.apass.esp.mapper.CategoryMapper;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;

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
	private ProMyCouponMapper myCouponMapper;
	
	@Autowired
	private CouponRelService couponRelService;
	
	@Autowired
	private ActivityCfgService activityCfgService;
	
	@Autowired
	private MyCouponManagerService myCouponManagerService;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
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
	/**
	 * 根据活动的Id，获取优惠券(不过滤优惠券剩余数量为0的优惠券)
	 * @param activityId
	 * @return
	 */
	public List<ProCoupon> getCouponListsByActivityId(String activityId){
		List<ProCoupon> couponList = new ArrayList<ProCoupon>();
		List<ProCouponRel> relList = couponRelService.getCouponRelList(activityId);
		for (ProCouponRel rel : relList) {
				ProCoupon pro = couponMapper.selectByPrimaryKey(rel.getCouponId());
				couponList.add(pro);
		}
		return couponList;
	}
	/**
	 * 获取有效活动优惠券(过滤优惠券剩余数量为0的优惠券和用户已经领取的优惠券)
	 * @return
	 */
	public List<ProCouponVo> getCouponList(Long userId){
		List<ProCouponVo> proCouponList = new ArrayList<ProCouponVo>();//可以领取的优惠券列表
		List<ProCouponRel> relList = couponRelService.getCouponList();
		for (ProCouponRel rel : relList) {
		   ProActivityCfg activityCfg = activityCfgService.getById(rel.getProActivityId());
		   //判断活动是否有效（正在进行中）
		   if(null !=activityCfg && ActivityStatus.PROCESSING == activityCfgService.getActivityStatus(activityCfg)){
				//获取优惠券信息
			    ProCoupon proCoupon = couponMapper.selectByPrimaryKey(rel.getCouponId());
				ProCouponVo proCouponVo=new ProCouponVo();
				proCouponVo.setId(proCoupon.getId());
				proCouponVo.setActivityId(rel.getProActivityId());
				proCouponVo.setCouponSill(proCoupon.getCouponSill());
				proCouponVo.setDiscountAmonut(proCoupon.getDiscountAmonut());
				proCouponVo.setName("【限"+activityCfg.getActivityName()+"活动商品】\t"+proCoupon.getName());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
				String startTimeString = formatter.format(activityCfg.getStartTime());
				String endTimeTimeString = formatter.format(activityCfg.getEndTime());
				proCouponVo.setStartTime(startTimeString);
				proCouponVo.setStartTimeDate(activityCfg.getStartTime());
				proCouponVo.setEndTime(endTimeTimeString);
				proCouponVo.setEffectiveTiem(startTimeString, endTimeTimeString);
			   //判断该用户是否已经领取了该优惠券
			   List<ProMyCoupon> proMyCouponList=myCouponManagerService.getCouponByUserIdAndRelCouponId(userId,rel.getId());
			   if(null !=proMyCouponList && proMyCouponList.size()>0){
					if(proMyCouponList.size()<rel.getLimitNum()){//领取的数量小于限领的数量则该优惠券还可以领取
						proCouponList.add(proCouponVo);
					}
				}else{
					proCouponList.add(proCouponVo);
				}
		      }
		}
		//根据ProCouponVo里面的开始时间排序
		Collections.sort(proCouponList,new Comparator<ProCouponVo>(){
			@Override
			public int compare(ProCouponVo o1, ProCouponVo o2) {
				if(o1.getStartTimeDate().getTime() > o2.getStartTimeDate().getTime()){
					return -1;
				}else{
					return 1;
				}
			}
			
		});
		return proCouponList;
	}
	

	
	public List<ProCouponVo> getCouponVos(String userId,String activityId) throws BusinessException{
		List<ProCouponVo> couponList = new ArrayList<ProCouponVo>();
		List<ProCouponRel> relList = couponRelService.getCouponRelList(activityId);
		ProActivityCfg cfg  = activityCfgService.getById(Long.parseLong(activityId));
		if(null == cfg){
			throw new BusinessException("活动不存在");
		}
		for (ProCouponRel rel : relList) {
			ProCouponVo vo  = new ProCouponVo();
			ProCoupon proCoupon = couponMapper.selectByPrimaryKey(rel.getCouponId());
			if(StringUtils.isNotBlank(userId)){
				List<ProMyCoupon> myCoupons = myCouponMapper.getCouponByUserIdAndRelId(new ProMyCouponQuery(Long.parseLong(userId), rel.getId()));
				if(rel.getLimitNum() <= myCoupons.size()){
					vo.setReceiveFlag(true);
				}
				vo.setUserReceiveNum(rel.getLimitNum()-myCoupons.size());//用户计算当前券，当前用户还可领取的张数
			}
			vo.setRemainNum(rel.getRemainNum());
			vo.setId(proCoupon.getId());
			vo.setName(proCoupon.getName());
			vo.setCouponSill(proCoupon.getCouponSill());
			vo.setDiscountAmonut(proCoupon.getDiscountAmonut());
			vo.setStartTime(DateFormatUtil.dateToString(cfg.getStartTime(), "yyyy.MM.dd"));
			vo.setEndTime(DateFormatUtil.dateToString(cfg.getEndTime(), "yyyy.MM.dd"));
			vo.setEffectiveTiem(vo.getStartTime(), vo.getEndTime());
			couponList.add(vo);
		}
		return couponList;
	}

}
