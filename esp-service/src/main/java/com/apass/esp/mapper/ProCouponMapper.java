package com.apass.esp.mapper;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.gfb.framework.mybatis.GenericMapper;


/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponMapper extends GenericMapper<ProCoupon, Long> {


	List<ProCoupon> pageList(Map<String, Object> paramMap);
    //根据商品code查询优惠券
    List<ProCoupon> getProCouponListByGoodsCode(String goodsCode);

    Integer pageListCount(Map<String, Object> paramMap);

    List<ProCoupon> getProCouponByName(ProCoupon proCoupon);
}
