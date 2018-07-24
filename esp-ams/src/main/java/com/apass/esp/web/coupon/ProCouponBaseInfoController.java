package com.apass.esp.web.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.apass.esp.domain.enums.*;
import com.google.gson.Gson;
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

import com.apass.esp.domain.Response;
import com.apass.esp.domain.dto.CouponList;
import com.apass.esp.domain.entity.ProActivityCfg;
import com.apass.esp.domain.entity.ProCoupon;
import com.apass.esp.domain.entity.ProCouponRel;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.query.ProCouponQuery;
import com.apass.esp.domain.vo.ActivityCfgQuery;
import com.apass.esp.domain.vo.ProActivityRelVo;
import com.apass.esp.domain.vo.ProMyCouponAmsVo;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.repository.httpClient.RsponseEntity.CustomerBasicInfo;
import com.apass.esp.service.offer.ActivityCfgService;
import com.apass.esp.service.offer.CouponRelService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.offer.ProCouponService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Created by xiaohai on 2017/10/27.
 */
@Controller
@RequestMapping("/application/coupon/management")
public class ProCouponBaseInfoController {
    private static final String COUPONPAGE = "coupon/procouponList";
    private static final Logger logger = LoggerFactory.getLogger(ProCouponBaseInfoController.class);
    @Autowired
    private ProCouponService proCouponService;
    @Autowired
    private MyCouponManagerService myCouponManagerService;
    @Autowired
    private CommonHttpClient commonHttpClient;
    @Autowired
    private ActivityCfgService activityCfgService;
    @Autowired
    private CouponRelService couponRelService;

    @RequestMapping("/page")
    public ModelAndView page() {
        return new ModelAndView(COUPONPAGE);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public ResponsePageBody<ProCoupon> pageList(ProCouponQuery query) {
        logger.info("优惠券分页查询开始,pageList()方法.....");
        ResponsePageBody<ProCoupon> responseBody = new ResponsePageBody<>();
        try {
            Pagination<ProCoupon> pagination = proCouponService.pageList(query);
            responseBody.setTotal(pagination.getTotalCount() == null ? 0 : pagination.getTotalCount());
            responseBody.setRows(pagination.getDataList());
            responseBody.setMsg("优惠券查询成功");
            responseBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);

        } catch (Exception e) {
            responseBody.setTotal(0);
            responseBody.setStatus(BaseConstants.CommonCode.ERROR_CODE);
            responseBody.setMsg("优惠券查询失败！");
            logger.error("优惠券查询异常,....Exception...", e);
        }

        return responseBody;
    }


    @RequestMapping("/loadp")
    @ResponseBody
    public List<ProCoupon> loadCouponPTFF(ProCoupon proCoupon) {
        proCoupon.setIsDelete(CouponIsDelete.COUPON_N.getCode());
        //如果是用户领取，只显示未关联有效活动的优惠券显示
        if (StringUtils.equals(CouponExtendType.COUPON_YHLQ.getCode(), proCoupon.getExtendType())) {
            //先捞取所有使用优惠券的 且有效的 活动
            ActivityCfgQuery activityCfgQuery = new ActivityCfgQuery();
            activityCfgQuery.setCoupon(ActivityCfgCoupon.COUPON_Y.getCode());
            activityCfgQuery.setStatus("processing");
            List<ProActivityCfg> activityCfgList = activityCfgService.selectProActivityCfgByActivitCfgQuery(activityCfgQuery);
            logger.info("活动配置表中有效活动数:{},参数activityCfgList:{}", String.valueOf(activityCfgList.size()), GsonUtils.toJson(activityCfgList));
            List<Long> activityIds = Lists.newArrayList();//有效活动id集合
            if (CollectionUtils.isNotEmpty(activityCfgList)) {
                for (ProActivityCfg proActivityCfg : activityCfgList) {
                    activityIds.add(proActivityCfg.getId());
                }
                //根据活动id去优惠券关联表中查询相应的优惠券id
                List<ProCouponRel> couponRels = couponRelService.getCouponRelListByActivityIdBanch(activityIds);
                Set<Long> couponIds = Sets.newTreeSet();
                if (CollectionUtils.isNotEmpty(couponRels)) {
                    for (ProCouponRel proRel : couponRels) {
                        couponIds.add(proRel.getCouponId());
                    }
                    ArrayList<Long> couponIdList = new ArrayList<>(couponIds);
                    logger.info("有效活动对应的优惠券ids：{}", GsonUtils.toJson(couponIdList));
                    //查询用户领取类型优惠券，并not in上述id的优惠券显示在前端
                    List<ProCoupon> lists = proCouponService.selectProCouponByIds(couponIdList);
                    return lists;
                }
            }

        }


        List<ProCoupon> coupons = proCouponService.getProCouponList(proCoupon);
        Iterator<ProCoupon> it = coupons.iterator();
        outter:
        while (it.hasNext()) {
            ProCoupon c = it.next();
            List<ProCouponRel> relList = couponRelService.getByCouponId(c.getId());
            if (CollectionUtils.isNotEmpty(relList)) {
                //判断是否存在有效的活动，如果有效，去除。
                for (ProCouponRel proCouponRel : relList) {
                    ProActivityCfg cfg = activityCfgService.getById(proCouponRel.getProActivityId());
                    if (cfg != null) {
                        logger.info("activityId:{},cfg:{}", proCouponRel.getProActivityId(), GsonUtils.toJson(cfg));
                        ActivityStatus activityStatus = activityCfgService.getActivityStatus(cfg);
                        if (StringUtils.equals(activityStatus.getCode(), ActivityStatus.NO.getCode())
                                || StringUtils.equals(activityStatus.getCode(), ActivityStatus.PROCESSING.getCode())) {
                            it.remove();
                            continue outter;
                        }
                    }else {
                        it.remove();
                        continue outter;
                    }
                }
            }
        }
        return coupons;
    }

    @RequestMapping("/loadp3")
    @ResponseBody
    public List<ProCoupon> loadCouponPTFF3(ProCoupon proCoupon) {
        List<ProCoupon> coupons = Lists.newArrayList();
        proCoupon.setIsDelete(CouponIsDelete.COUPON_N.getCode());
        List<ProCoupon> ptff = proCouponService.getProCouponList(proCoupon);
        coupons.addAll(ptff);
        proCoupon.setExtendType(CouponExtendType.COUPON_FYDYHZX.getCode());
        List<ProCoupon> fyd = proCouponService.getProCouponList(proCoupon);
        coupons.addAll(fyd);
        return coupons;
    }

    @RequestMapping("/loadp2")
    @ResponseBody
    public List<ProCoupon> loadCouponPTFF2(String extendType,String activityId) {
        ProCoupon proCoupon = new ProCoupon();
        proCoupon.setExtendType(extendType);
        List<ProCoupon> result = proCouponService.getProCouponList(proCoupon);
        Iterator<ProCoupon> it = result.iterator();
        outter: while (it.hasNext()) {
            ProCoupon c = it.next();
            List<ProCouponRel> relList = couponRelService.getByCouponId(c.getId());

            if (CollectionUtils.isNotEmpty(relList)) {
                //判断是否存在有效的活动，如果有效，去除。
                for (ProCouponRel proCouponRel : relList) {
                    if(proCouponRel.getProActivityId().toString().equals(activityId)){
                        continue outter;
                    }

                    ProActivityCfg cfg = activityCfgService.getById(proCouponRel.getProActivityId());
                    if (cfg != null) {
                        logger.info("activityId:{},cfg:{}", proCouponRel.getProActivityId(), GsonUtils.toJson(cfg));
                        ActivityStatus activityStatus = activityCfgService.getActivityStatus(cfg);
                        if (StringUtils.equals(activityStatus.getCode(), ActivityStatus.NO.getCode())
                                || StringUtils.equals(activityStatus.getCode(), ActivityStatus.PROCESSING.getCode())) {
                            it.remove();
                            continue outter;
                        }
                    }else {
                        it.remove();
                        continue outter;
                    }
                }
            }
        }
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Response addCoupon(ProCoupon proCoupon) {
        try {
            if (validate(proCoupon)) {
                proCoupon.setIsDelete(CouponIsDelete.COUPON_N.getCode());
                proCoupon.setCreateUser(SpringSecurityUtils.getCurrentUser());
                proCoupon.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                proCoupon.setCreatedTime(new Date());
                proCoupon.setUpdatedTime(new Date());
                proCouponService.inserProcoupon(proCoupon);
            }

        } catch (Exception e) {
            logger.error("添加优惠券异常，Exception-----", e);
            return Response.fail(e.getMessage());
        }

        return Response.success("添加优惠券成功");
    }


    @RequestMapping("/issue")
    @ResponseBody
    public Response issueCoupon(@RequestBody String request) {//ProMyCouponAmsVo
        String requestDecode = EncodeUtils.urlDecode(request);
        requestDecode = requestDecode.substring(0, requestDecode.length() - 1);
        logger.info("前台传递的参数有：{}", requestDecode);
        ProMyCouponAmsVo proMyCouponAmsVo = GsonUtils.convertObj(requestDecode, ProMyCouponAmsVo.class);
        try {
            //优惠券id作为key，每个id对应的优惠券数量作为list
            Map<Long, List<ProMyCoupon>> proMycouponMap = Maps.newHashMap();

            List<CouponList> couponLists = proMyCouponAmsVo.getCouponListIssue();
            if (CollectionUtils.isNotEmpty(couponLists)) {
                for (int i = 0; i < couponLists.size(); i++) {
                    for (int j = i + 1; j < couponLists.size(); j++) {
                        if (StringUtils.equals(couponLists.get(i).getId(), couponLists.get(j).getId())) {
                            return Response.fail("本次发放存在重复优惠券种类，请修改后重试");
                        }
                    }
                }
            }

            CustomerBasicInfo customerBasicInfo = commonHttpClient.getCustomerInfo("ProCouponBaseInfoController.issueCoupon(),优惠券发布...", proMyCouponAmsVo.getTelephone());
            if (null == customerBasicInfo) {
                return Response.fail("该手机号可能尚未注册!");
            }
            //封装数据 map
            for (CouponList couponList : couponLists) {
                List<ProMyCoupon> proMycouponList = Lists.newArrayList();
                ProActivityRelVo vo = proCouponService.getByActivityAndCoupon(Long.valueOf(couponList.getId()));
                int i = 0;
                while (i < couponList.getNumer()) {
                    ProMyCoupon proMyCoupon = new ProMyCoupon();
                    proMyCoupon.setUserId(customerBasicInfo.getAppId());
                    proMyCoupon.setCouponRelId(vo.getRelId());
                    proMyCoupon.setStatus(CouponStatus.COUPON_N.getCode());
                    proMyCoupon.setCouponId(Long.valueOf(couponList.getId()));
                    proMyCoupon.setTelephone(proMyCouponAmsVo.getTelephone());
                    proMyCoupon.setStartDate(vo.getStartDate());
                    proMyCoupon.setEndDate(vo.getEndDate());
                    proMyCoupon.setRemarks(proMyCouponAmsVo.getRemarks());
                    proMyCoupon.setCreatedTime(new Date());
                    proMyCoupon.setUpdatedTime(new Date());
                    proMycouponList.add(proMyCoupon);
                    i++;
                }
                proMycouponMap.put(Long.valueOf(couponList.getId()), proMycouponList);
            }

            //便利，批量插入数据

            Set<Map.Entry<Long, List<ProMyCoupon>>> entries = proMycouponMap.entrySet();
            for (Map.Entry<Long, List<ProMyCoupon>> entry : entries) {
                List<ProMyCoupon> lists = entry.getValue();
                for (ProMyCoupon proCoup : lists) {
                    myCouponManagerService.insertProMyCoupo(proCoup);
                }
            }

        } catch (BusinessException e) {
            logger.error("手动发放优惠券异常，Exception-----", e);
            return Response.fail(e.getErrorDesc());
        } catch (Exception e) {
            logger.error("手动发放优惠券异常，Exception-----", e);
            return Response.fail("发送优惠券失败！");
        }

        return Response.success("发放优惠券成功！");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Response deleteByCouponId(ProCoupon proCoupon) {
        try {
            Integer count = proCouponService.deleteByCouponId(proCoupon);
        } catch (Exception e) {
            logger.error("删除优惠券异常，Exception-----", e);
            return Response.fail(e.getMessage());
        }
        return Response.success("删除优惠券成功");
    }

    /**
     * 添加优惠券参数验证
     *
     * @param proCoupon
     * @return
     */
    private boolean validate(ProCoupon proCoupon) {
        if (StringUtils.isBlank(proCoupon.getName())) {
            throw new RuntimeException("优惠券名称不能为空");
        }
        if (proCoupon.getName().length() > 16) {
            throw new RuntimeException("优惠券名称不能大于16字符");
        }
        if (StringUtils.isBlank(proCoupon.getExtendType())) {
            throw new RuntimeException("推广方式不能为空");
        }

        if (StringUtils.equals(proCoupon.getExtendType(), CouponExtendType.COUPON_PTFF.getCode())
                || StringUtils.equals(proCoupon.getExtendType(), CouponExtendType.COUPON_XYH.getCode())) {
            if (proCoupon.getEffectiveTime() == null) {
                throw new RuntimeException("有效期不能为空");
            }
        }
        if (StringUtils.isBlank(proCoupon.getType())) {
            throw new RuntimeException("优惠券类型不能为空");
        }

        if (StringUtils.equals(proCoupon.getExtendType(), CouponExtendType.COUPON_FYDYHZX.getCode())) {
            if (StringUtils.isBlank(proCoupon.getGrantNode())) {
                throw new RuntimeException("发放节点不能为空");
            }
        }
        if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDPL.getCode())) {
            if (StringUtils.isBlank(proCoupon.getCategoryId1())
                    && StringUtils.isBlank(proCoupon.getCategoryId2())) {
                throw new RuntimeException("商品类目不能为空");
            }
        }
        if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_ZDSP.getCode())) {
            if (StringUtils.isBlank(proCoupon.getGoodsCode())) {
                throw new RuntimeException("商品编码不能为空");
            }
        }
        if (StringUtils.equals(CouponSillType.COUPON_Y.getCode(), proCoupon.getSillType())) {
            if (proCoupon.getCouponSill() == null) {
                throw new RuntimeException("优惠门槛不能为空");
            }
        }
        if (proCoupon.getDiscountAmonut() == null) {
            throw new RuntimeException("优惠金额不能为空");
        }

        /**
         * 如果是活动商品的优惠券,则要判断优惠范围，根据优惠范围，判断是否需要传值
         */
        if (StringUtils.equals(proCoupon.getType(), CouponType.COUPON_HDSP.getCode())
                && !StringUtils.equals(proCoupon.getExtendType(), CouponExtendType.COUPON_FYDYHZX.getCode())) {
            if (proCoupon.getOfferRange() == null) {
                throw new RuntimeException("优惠范围不能为空!");
            }
            int offerRange = proCoupon.getOfferRange().intValue();
            switch (offerRange) {
                case 1:
                    if (proCoupon.getBrandId() == null) {
                        throw new RuntimeException("品牌不能为空!");
                    }
                    break;
                case 2:
                    if (StringUtils.isBlank(proCoupon.getCategoryId1()) &&
                            StringUtils.isBlank(proCoupon.getCategoryId2()) &&
                            StringUtils.isBlank(proCoupon.getCategoryId3())) {
                        throw new RuntimeException("类目不能为空!");
                    }
                    break;
                case 3:
                    if (StringUtils.isBlank(proCoupon.getSkuId())) {
                        throw new RuntimeException("商品skuId不能为空!");
                    }
                    if (StringUtils.length(proCoupon.getSkuId()) > 20) {
                        throw new RuntimeException("商品skuId不能大于20字符!");
                    }
                    break;
                default:
                    throw new RuntimeException("优惠范围传入值不合法!");
            }
        }
        return true;
    }

}
