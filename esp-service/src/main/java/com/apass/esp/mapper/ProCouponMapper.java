package com.apass.esp.mapper;

import java.util.List;
import java.util.Map;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.gfb.framework.mybatis.GenericMapper;
import com.apass.gfb.framework.mybatis.page.Pagination;


/**
 * Created by jie.xu on 17/10/27.
 */
public interface ProCouponMapper extends GenericMapper<ProCoupon, Long> {

    Pagination<ProCoupon> pageList(Map<String, Object> paramMap);
    //
    List<ProCoupon> getProCouponListByGoodsCode(Map<String, Object> paramMap);

    Integer pageListCount(Map<String, Object> paramMap);

}
