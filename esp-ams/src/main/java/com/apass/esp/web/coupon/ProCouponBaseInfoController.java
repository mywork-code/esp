package com.apass.esp.web.coupon;

import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.ProCouponService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohai on 2017/10/27.
 */
@Controller
@RequestMapping("/application/coupon/management")
public class ProCouponBaseInfoController {
    private static final String COUPONPAGE = "coupon/procouponList";

    @Autowired
    private ProCouponService proCouponService;

    @RequestMapping("/page")
    public ModelAndView page(){
        return new ModelAndView(COUPONPAGE);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public ResponsePageBody<ProCoupon> pageList(HttpServletRequest request){
        ResponsePageBody<ProCoupon> responseBody = new ResponsePageBody<>();
        String pageNo = HttpWebUtils.getValue(request, "page");
        String pageSiz = HttpWebUtils.getValue(request, "rows");

        //分页参数
        Integer pageNum = Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo);
        Integer pageSize = Integer.valueOf(pageSiz) <= 0 ? 1 : Integer.valueOf(pageSiz);
        Integer pageBegin = (pageNum - 1) * pageSize;

        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("pageBegin",pageBegin);
        paramMap.put("pageSize",pageSize);

        Pagination<ProCoupon> pagenation = proCouponService.pageList(paramMap);


        return null;
    }
}
