package com.apass.esp.web.coupon;

import com.alibaba.druid.support.logging.Log;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.ProCouponService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponBaseInfoController.class);
    @Autowired
    private ProCouponService proCouponService;

    @RequestMapping("/page")
    public ModelAndView page(){
        return new ModelAndView(COUPONPAGE);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public ResponsePageBody<ProCoupon> pageList(HttpServletRequest request){
        LOGGER.info("优惠券分页查询开始,pageList()方法.....");
        ResponsePageBody<ProCoupon> responseBody = new ResponsePageBody<>();
        try{
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSiz = HttpWebUtils.getValue(request, "rows");

            //分页参数
            Integer pageNum = Integer.valueOf(pageNo) <= 0 ? 1 : Integer.valueOf(pageNo);
            Integer pageSize = Integer.valueOf(pageSiz) <= 0 ? 1 : Integer.valueOf(pageSiz);
            Integer pageBegin = (pageNum - 1) * pageSize;

            Map<String,Object> paramMap = Maps.newHashMap();
            paramMap.put("pageBegin",pageBegin);
            paramMap.put("pageSize",pageSize);

            Pagination<ProCoupon> pagination = proCouponService.pageList(paramMap);
            responseBody.setTotal(pagination.getTotalCount()==null ? 0 : pagination.getTotalCount());
            responseBody.setRows(pagination.getDataList());
            responseBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

        }catch (Exception e){
            responseBody.setStatus(BaseConstants.CommonCode.ERROR_CODE);
            LOGGER.error("优惠券查询异常,....Exception...",e);
        }

        return responseBody;
    }
}
