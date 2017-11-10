package com.apass.esp.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.query.ProCouponQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;


/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponMapper extends GenericMapper<ProCoupon, Long> {


	List<ProCoupon> pageList(ProCouponQuery query);
    //根据商品code查询优惠券
    //List<ProCoupon> getProCouponListByGoodsCode(ProCoupon proCoupon);

    Integer pageListCount(ProCouponQuery query);

    //根据proCoupon中的数据查询优惠券：公用
    List<ProCoupon> getProCouponBCoupon(ProCoupon proCoupon);


    List<ProCoupon> selectProCouponByIds(Map<String, Object> paramMap);
}

