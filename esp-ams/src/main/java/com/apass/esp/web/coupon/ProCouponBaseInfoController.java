package com.apass.esp.web.coupon;

import com.alibaba.druid.support.logging.Log;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.enums.*;
import com.apass.esp.domain.vo.ProMyCouponAmsVo;
import com.apass.esp.domain.vo.ProMyCouponVo;
import com.apass.esp.service.offer.CouponManagerService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.offer.ProCouponService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Autowired
    private MyCouponManagerService myCouponManagerService;

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
            responseBody.setMsg("优惠券查询成功");
            responseBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

        }catch (Exception e){
            responseBody.setTotal(0);
            responseBody.setStatus(BaseConstants.CommonCode.ERROR_CODE);
            responseBody.setMsg("优惠券查询失败！");
            LOGGER.error("优惠券查询异常,....Exception...",e);
        }

        return responseBody;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Response addCoupon(ProCoupon proCoupon){
        try{
            if(validate(proCoupon)){
                proCoupon.setIsDelete(CouponIsDelete.COUPON_N.getCode());
                proCoupon.setCreateUser(SpringSecurityUtils.getCurrentUser());
                proCoupon.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                proCoupon.setCreatedTime(new Date());
                proCoupon.setUpdatedTime(new Date());
                if(proCoupon.getEffectiveTime() == null){
                    proCoupon.setEffectiveTime(-1);
                }
                Integer count = proCouponService.inserProcoupon(proCoupon);
            }

        }catch (Exception e){
            LOGGER.error("添加优惠券异常，Exception-----",e);
            return Response.fail(e.getMessage());
        }

        return Response.success("添加优惠券成功");
    }


    @RequestMapping("/issue")
    @ResponseBody
    public Response issueCoupon(ProMyCouponAmsVo proMyCouponAmsVo){
        try{
            //优惠券id作为key，每个id对应的优惠券数量作为list
            Map<Long,List<ProMyCoupon>> proMycouponMap = Maps.newHashMap();

            List<ProMyCoupon> proMycouponList = Lists.newArrayList();
            List<Long> couponIdList = proMyCouponAmsVo.getCouponIdList();
            if(new TreeSet<Long>(couponIdList).size() != couponIdList.size()){
                return Response.fail("本次发放存在重复优惠券种类，请修改后重试");
            }
            //封装数据 map
            for(Long couponId: couponIdList){
                ProCoupon proCoupon = proCouponService.selectProCouponByPrimaryID(couponId);
                int i = 0;
                while (i<proMyCouponAmsVo.getCouponNum()){
                    ProMyCoupon proMyCoupon = new ProMyCoupon();
                    proMyCoupon.setCouponId(couponId);
                    proMyCoupon.setTelephone(proMyCouponAmsVo.getTelephone());
                    proMyCoupon.setRemarks(proMyCouponAmsVo.getRemarks());
                    proMyCoupon.setStartDate(new Date());
                    proMyCoupon.setEndDate(DateFormatUtil.addDays(new Date(),proCoupon.getEffectiveTime()==-1?0:proCoupon.getEffectiveTime()));
                    proMyCoupon.setStatus(CouponStatus.COUPON_N.getCode());
                    proMyCoupon.setCreatedTime(new Date());
                    proMyCoupon.setUpdatedTime(new Date());
                    proMycouponList.add(proMyCoupon);
                    i++;
                }
                proMycouponMap.put(couponId,proMycouponList);
            }

            //便利，批量插入数据

            Set<Map.Entry<Long, List<ProMyCoupon>>> entries = proMycouponMap.entrySet();
            for (Map.Entry<Long, List<ProMyCoupon>> entry:entries) {
                List<ProMyCoupon> lists = entry.getValue();
                myCouponManagerService.insertProMyCoupoBach(lists);
            }

        }catch (Exception e){
            LOGGER.error("手动发放优惠券异常，Exception-----",e);
            return Response.fail(e.getMessage());
        }

        return Response.success("手动添加优惠券成功.");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response deleteByCouponId(ProCoupon proCoupon){
        try{
            proCoupon.setIsDelete(CouponIsDelete.COUPON_Y.getCode());
            Integer count = proCouponService.deleteByCouponId(proCoupon);

        }catch (Exception e){
            LOGGER.error("删除优惠券异常，Exception-----",e);
            return Response.fail("删除优惠券失败");
        }

        return Response.success("删除优惠券成功");
    }

    /**
     * 添加优惠券参数验证
     * @param proCoupon
     * @return
     */
    private boolean validate(ProCoupon proCoupon) {
        if(StringUtils.isBlank(proCoupon.getName())){
           throw new RuntimeException("优惠券名称不能为空");
        }
        if(proCoupon.getName().length()>20 ){
            throw new RuntimeException("优惠券名称必须小于等于20字符");
        }
        if(StringUtils.isBlank(proCoupon.getExtendType())){
            throw new RuntimeException("推广方式不能为空");
        }

        if(StringUtils.equals(proCoupon.getExtendType(), CouponExtendType.COUPON_PTFF.getCode())
                ||StringUtils.equals(proCoupon.getExtendType(),CouponExtendType.COUPON_XYH.getCode())){
            if(proCoupon.getEffectiveTime() == null){
                throw new RuntimeException("有效期不能为空");
            }
        }
        if(StringUtils.isBlank(proCoupon.getType())){
            throw new RuntimeException("优惠券类型不能为空");
        }
        if(StringUtils.equals(proCoupon.getType(),CouponType.COUPON_ZDPL.getCode())){
            if(StringUtils.isBlank(proCoupon.getCategoryId1())
                    &&StringUtils.isBlank(proCoupon.getCategoryId2())){
                throw new RuntimeException("商品类目不能为空");
            }
        }
        if(StringUtils.equals(proCoupon.getType(),CouponType.COUPON_ZDSP.getCode())){
            if(StringUtils.isBlank(proCoupon.getGoodsCode())){
                throw new RuntimeException("商品类目不能为空");
            }
        }
        if(StringUtils.equals(CouponSillType.COUPON_Y.getCode(),proCoupon.getSillType())){
            if(proCoupon.getCouponSill() == null){
                throw new RuntimeException("优惠门槛不能为空");
            }
        }
        if(proCoupon.getDiscountAmonut() == null){
            throw new RuntimeException("优惠金额不能为空");
        }

        return true;
    }





}
