package com.apass.esp.web.coupon;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.CouponList;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.CouponExtendType;
import com.apass.esp.domain.enums.CouponIsDelete;
import com.apass.esp.domain.enums.CouponSillType;
import com.apass.esp.domain.enums.CouponStatus;
import com.apass.esp.domain.enums.CouponType;
import com.apass.esp.domain.query.ProCouponQuery;
import com.apass.esp.domain.vo.ProMyCouponAmsVo;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.offer.ProCouponService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private CommonHttpClient commonHttpClient;

    @RequestMapping("/page")
    public ModelAndView page(){
        return new ModelAndView(COUPONPAGE);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public ResponsePageBody<ProCoupon> pageList(ProCouponQuery query){
        LOGGER.info("优惠券分页查询开始,pageList()方法.....");
        ResponsePageBody<ProCoupon> responseBody = new ResponsePageBody<>();
        try{
            Pagination<ProCoupon> pagination = proCouponService.pageList(query);
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

    @RequestMapping("/loadp")
    @ResponseBody
    public List<ProCoupon> loadCouponPTFF(ProCoupon proCoupon){
        proCoupon.setIsDelete(CouponIsDelete.COUPON_N.getCode());

        return proCouponService.getProCouponList(proCoupon);
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
    public Response issueCoupon(@RequestBody String request) {//ProMyCouponAmsVo
        String requestDecode = EncodeUtils.urlDecode(request);
        requestDecode = requestDecode.substring(0,requestDecode.length()-1);
        LOGGER.info("前台传递的参数有：{}", requestDecode);
        ProMyCouponAmsVo proMyCouponAmsVo = GsonUtils.convertObj(requestDecode, ProMyCouponAmsVo.class);
        try{
            //优惠券id作为key，每个id对应的优惠券数量作为list
            Map<Long,List<ProMyCoupon>> proMycouponMap = Maps.newHashMap();

            List<CouponList> couponLists = proMyCouponAmsVo.getCouponListIssue();
            if(CollectionUtils.isNotEmpty(couponLists)){
                for (int i=0;i<couponLists.size();i++) {
                    for(int j=i+1; j<couponLists.size();j++){
                        if(StringUtils.equals(couponLists.get(i).getId(),couponLists.get(j).getId())){
                            return Response.fail("本次发放存在重复优惠券种类，请修改后重试");
                        }
                    }
                }
            }

            Response response = commonHttpClient.getCustomerBasicInfoByTel("ProCouponBaseInfoController.issueCoupon(),优惠券发布...", proMyCouponAmsVo.getTelephone());
            if(response==null||!response.statusResult()){
                return Response.fail("手机号输入错误，请重新输入。");
            }
            CustomerBasicInfo customerBasicInfo = Response.resolveResult(response, CustomerBasicInfo.class);
            if(customerBasicInfo==null){
                return Response.fail("手机号输入错误，请重新输入。");
            }
            //封装数据 map
            for(CouponList couponList: couponLists){
                List<ProMyCoupon> proMycouponList = Lists.newArrayList();
                ProCoupon proCoupon = proCouponService.selectProCouponByPrimaryID(Long.valueOf(couponList.getId()));

                int i = 0;
                while (i<couponList.getNumer()){
                    ProMyCoupon proMyCoupon = new ProMyCoupon();
                    proMyCoupon.setUserId(customerBasicInfo.getAppId());
                    proMyCoupon.setCouponRelId(-1l);
                    proMyCoupon.setStatus(CouponStatus.COUPON_N.getCode());
                    proMyCoupon.setCouponId(Long.valueOf(couponList.getId()));
                    proMyCoupon.setTelephone(proMyCouponAmsVo.getTelephone());
                    proMyCoupon.setStartDate(new Date());
                    proMyCoupon.setEndDate(DateFormatUtil.addDays(new Date(),proCoupon.getEffectiveTime()));
                    proMyCoupon.setRemarks(proMyCouponAmsVo.getRemarks());
                    proMyCoupon.setCreatedTime(new Date());
                    proMyCoupon.setUpdatedTime(new Date());
                    proMycouponList.add(proMyCoupon);
                    i++;
                }
                proMycouponMap.put(Long.valueOf(couponList.getId()),proMycouponList);
            }

            //便利，批量插入数据

            Set<Map.Entry<Long, List<ProMyCoupon>>> entries = proMycouponMap.entrySet();
            for (Map.Entry<Long, List<ProMyCoupon>> entry:entries) {
                List<ProMyCoupon> lists = entry.getValue();
                for (ProMyCoupon proCoup:lists) {
                    myCouponManagerService.insertProMyCoupo(proCoup);
                }
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
            return Response.fail(e.getMessage());
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
            throw new RuntimeException("优惠券名称不能大于20字符");
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
                throw new RuntimeException("商品编码不能为空");
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
