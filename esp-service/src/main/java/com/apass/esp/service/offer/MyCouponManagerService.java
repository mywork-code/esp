package com.apass.esp.service.offer;

import com.apass.esp.domain.entity.CashRefund;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.ActivityStatus;
import com.apass.esp.domain.enums.CashRefundStatus;
import com.apass.esp.domain.enums.CouponExtendType;
import com.apass.esp.domain.enums.CouponStatus;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.query.ProCouponRelQuery;
import com.apass.esp.domain.query.ProMyCouponQuery;
import com.apass.esp.domain.vo.MyCouponVo;
import com.apass.esp.domain.vo.ProMyCouponVo;
import com.apass.esp.mapper.CashRefundMapper;
import com.apass.esp.mapper.ProActivityCfgMapper;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.mapper.ProMyCouponMapper;
import com.apass.esp.repository.order.OrderInfoRepository;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@Autowired
	private CashRefundMapper cashRefundMapper;
	
	/**
	 * 点击领取优惠券
	 * @throws BusinessException
	 */
	public int giveCouponToUser(MyCouponVo vo) throws BusinessException{
		/**
		 * 判断活动是否已经结束
		 */
	    ProActivityCfg activityCfg = activityCfgService.getById(vo.getActivityId());
	    if(null == activityCfg){
	    	throw new BusinessException("活动已经结束啦，看看其他的券吧!");
	    }
	    if(activityCfg.getEndTime().getTime() < new Date().getTime()){
	    	throw new BusinessException("活动已经结束啦，看看其他的券吧!");
	    }
		/**
		 * 首先，根据活动的Id和优惠券的id ,查询此活动和优惠券的关系表信息
		 */
		ProCouponRel couponRel = couponRelMapper.getRelByActivityIdAndCouponId(new ProCouponRelQuery(vo.getActivityId(),vo.getCouponId()));
		if(null == couponRel){
			throw new BusinessException("该券被抢空啦，看看其他的券吧!");
		}
		if(couponRel.getRemainNum() == 0){
			throw new BusinessException("该券被抢空啦，看看其他的券吧！");
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
		
		ProActivityCfg activity = activityCfgMapper.selectByPrimaryKey(vo.getActivityId());
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
		 * 未使用(已根据updated_time 降序排列)
		 */
		List<ProMyCouponVo> unUsedList = getCouponsUnused(userId);
		/**
		 * 已使用(已根据updated_time 降序排列)
		 */
		List<ProMyCouponVo> usedList = getCouponsUsed(userId);
		/**
		 * 已过期(需要根据失效时间排序)
		 */
		List<ProMyCouponVo> expireList = getExpire(userId);
		/**
		 * 排序时间  失效时间
		 */
		Collections.sort(expireList, new Comparator<ProMyCouponVo>() {  
            public int compare(ProMyCouponVo obj1, ProMyCouponVo obj2) {
            	Date now1 = DateFormatUtil.string2date(obj1.getEndDate(), "");
            	Date now2 = DateFormatUtil.string2date(obj2.getEndDate(), "");
                int retVal = 0;  
                try {  
                	if( now1.getTime() > now2.getTime() ){
                		retVal = -1;
                	}
                	if( now1.getTime() < now2.getTime() ){
                		retVal = 1;
                	}
                } catch (Exception e) {  
                    throw new RuntimeException();  
                }  
                return retVal;  
            }  
        });
		
		Map<String,Object> params = Maps.newHashMap();
		params.put("unUsed", unUsedList);
		params.put("used",usedList);
		params.put("expire",expireList);
		return params;
	}
	/**
	 * 根据用户的Id和活动优惠券Id查询对应的信息
	 * @return
	 */
	public List<ProMyCoupon> getCouponByUserIdAndRelCouponId(Long userId,Long couponRelId){
		ProMyCouponQuery query=new ProMyCouponQuery();
		if(null !=userId && null !=couponRelId){
			query.setUserId(userId);
			query.setCouponRelId(couponRelId);
			return myCouponMapper.getCouponByUserIdAndRelId(query);
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
		vo.setTypeDesc(getTypeDesc());
		vo.setCouponSill(coupon.getCouponSill());
		vo.setDiscountAmonut(coupon.getDiscountAmonut());
		vo.setCouponName(null != coupon ? coupon.getName():"");
		vo.setEndDate(DateFormatUtil.dateToString(p.getEndDate(),""));
		vo.setRemarks(p.getRemarks());
		vo.setStartDate(DateFormatUtil.dateToString(p.getStartDate(),""));
		vo.setEffectiveTime(DateFormatUtil.dateToString(p.getStartDate(),"yyyy.MM.dd")+"-"+DateFormatUtil.dateToString(p.getEndDate(),"yyyy.MM.dd"));
		vo.setStatus(p.getStatus());
		vo.setTelephone(p.getTelephone());
		vo.setUserId(p.getUserId());
		return vo;
	}
	
	/**
	 * 根据券的类型，获取type描述信息
	 * @param coupon
	 * @return
	 */
	public String getTypeDesc(){
		return "";
	}
	
	/**
	 * 订单失效、退款返回优惠券
	 */
	public void returnCoupon(Long userId,Long couponId,String selfOrderId){
		if(couponId == null || couponId < 0){
			return;
		}
		//根据couponId查询orderList,判断订单状态是否是退款或订单失效
		List<OrderInfoEntity> orderList = orderInfoRepository.selectByCouponId(couponId);
		for(OrderInfoEntity order : orderList){
			if(selfOrderId.equals(order.getOrderId())){
				continue;
			}
			if(order.getStatus().equals(OrderStatus.ORDER_CANCEL.getCode())){
				//这里空代码是正确的代码，请勿删除！！！
			}
		  else if(order.getStatus().equals(OrderStatus.ORDER_TRADCLOSED.getCode())){
				CashRefund cr = cashRefundMapper.getCashRefundByOrderId(order.getOrderId());
				if(cr != null){
					if(cr.getStatus().toString().equals(CashRefundStatus.CASHREFUND_STATUS4.getCode())){

					}else{
						return;
					}
				}else{
					return ;
				}
			}else {
				return;
			}
		}
		updateStatus("N",userId,couponId);
	}



	public void updateStatus(String status,Long userId,Long couponId){
		myCouponMapper.updateStatusByUserIdAndCouponId(status,userId,couponId);
	}

	/**
	 * 批量插入优惠券
     */
	public void insertProMyCoupo(ProMyCoupon proMyCoupon) {
		myCouponMapper.insertSelective(proMyCoupon);
	}

	/**
	 * 逻辑删除券
	 * @param mycouponId
	 */
	public void deleteMyCoupon(String mycouponId){
		updateMyCoupon(mycouponId, CouponStatus.COUPON_D.getCode());
	}
	
	/**
	 * 使用我的优惠券
	 * @param mycouponId
	 */
	public void useMyCoupon(String mycouponId){
		updateMyCoupon(mycouponId, CouponStatus.COUPON_Y.getCode());
	}
	
	/**
	 * 修改我的优惠券状态
	 * @param mycouponId
	 * @param status
	 * @return
	 */
	public int updateMyCoupon(String mycouponId,String status){
		if(StringUtils.isNotBlank(mycouponId) && StringUtils.isNotBlank(status)){
			ProMyCoupon myCoupon = myCouponMapper.selectByPrimaryKey(Long.parseLong(mycouponId));
			if(null != myCoupon){
				myCoupon.setStatus(status);
				myCoupon.setUpdatedTime(new Date());
				return myCouponMapper.updateByPrimaryKeySelective(myCoupon);
			}
		}
		return 0;
	}

	/**
	 * 给新注册的用户添加新用户专享优惠券
	 */
	public void addXYHCoupons(Long userId,String tel) throws BusinessException {
		//查询新用户专享优惠券
		ProCoupon proCoupon = new ProCoupon();
		proCoupon.setExtendType(CouponExtendType.COUPON_XYH.getCode());
		List<ProCoupon> couponList = couponMapper.getProCouponBCoupon(proCoupon);
		for(ProCoupon coupon : couponList){
			ProMyCoupon proMyCoupon = new ProMyCoupon();
			proMyCoupon.setUserId(userId);
			proMyCoupon.setCouponRelId(-1l);
			proMyCoupon.setStatus(CouponStatus.COUPON_N.getCode());
			proMyCoupon.setCouponId(Long.valueOf(coupon.getId()));
			proMyCoupon.setTelephone(tel);
			Date d = new Date();
			proMyCoupon.setStartDate(d);
			proMyCoupon.setEndDate(DateFormatUtil.addDays(d,coupon.getEffectiveTime()));
			proMyCoupon.setCreatedTime(d);
			proMyCoupon.setUpdatedTime(d);
			myCouponMapper.insertSelective(proMyCoupon);
		}
	}
}
