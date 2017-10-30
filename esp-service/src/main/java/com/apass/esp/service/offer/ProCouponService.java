package com.apass.esp.service.offer;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.mapper.ProCouponMapper;
import com.apass.gfb.framework.mybatis.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2017/10/30.
 */
@Service
@Transactional
public class ProCouponService {
    @Autowired
    private ProCouponMapper couponMapper;

    public Pagination<ProCoupon> pageList(Map<String, Object> paramMap) {
        List<ProCoupon> proCouponList = couponMapper.pageList(paramMap);

    }
}
