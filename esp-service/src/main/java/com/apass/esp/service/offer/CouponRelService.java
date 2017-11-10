package com.apass.esp.service.offer;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
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

	/**
	 * 往优惠券活动关系表中添加数据
	 * @param proCouponRel
	 * @return
     */
	public Integer addProCouponRel(ProCouponRel proCouponRel) {
		return couponRelMapper.insertSelective(proCouponRel);
	}

	public Integer updateProCouponRel(ProCouponRel proCouponRel) {
		return couponRelMapper.updateByPrimaryKeySelective(proCouponRel);
	}

	public ProCouponRel getcoupoRelByPrimary(Long id) {
		return couponRelMapper.selectByPrimaryKey(id);
	}


	public List<ProCouponRel> getCouponRelListByActivityIdBanch(List<Long> activityIds) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("activityIds",activityIds);
		return couponRelMapper.getCouponRelListByActivityIdBanch(paramMap);
	}
}
