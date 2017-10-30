package com.apass.esp.service.offer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.gfb.framework.mybatis.page.Pagination;

/**
 * Created by xiaohai on 2017/10/30.
 */
@Service
@Transactional
public class ProCouponService {
    @Autowired
    private ProCouponMapper couponMapper;

    public Pagination<ProCoupon> pageList(Map<String, Object> paramMap) {
    	Pagination<ProCoupon> proCouponList = couponMapper.pageList(paramMap);
        return proCouponList;
    }
    public List<ProCoupon> getProCouponListByGoodsCode(String goodsCode) {
    	return couponMapper.getProCouponListByGoodsCode(goodsCode);
    }
}
