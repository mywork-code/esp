package com.apass.esp.web.zhongyuan;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ProMyCoupon;
import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.domain.enums.QHRewardTypeEnums;
import com.apass.esp.domain.enums.SmsTypeEnums;
import com.apass.esp.domain.vo.zhongyuan.ZYCompanyCityAwardsVo;
import com.apass.esp.domain.vo.zhongyuan.ZYEmpInfoVo;
import com.apass.esp.domain.vo.zhongyuan.ZYResponseVo;
import com.apass.esp.mapper.ProCouponRelMapper;
import com.apass.esp.repository.httpClient.CommonHttpClient;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.common.ZhongYuanQHService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.RegExpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/8/21.
 */
@Controller
@RequestMapping("/zhongyuan")
public class ZhongYuanWelfareController {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ZhongYuanWelfareController.class);
    @Autowired
    private MobileSmsService mobileRandomService;
    @Autowired
    private ZYPriceCollecService zyPriceCollecService;

    @Autowired
    private ZhongYuanQHService zhongYuanQHService;

    @Autowired
    private MyCouponManagerService myCouponManagerService;

    @Autowired
    private CommonHttpClient commonHttpClient;
    @Autowired
    private ProCouponRelMapper couponRelMapper;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    //领取活动商品接口
    @RequestMapping(value = "/addAwardGoods",method = RequestMethod.POST)
    @ResponseBody
    public Response addAwardGoods(@RequestBody Map<String, Object> paramMap){
        LOGGER.info("领取活动商品接口接收的参数:{}",GsonUtils.toJson(paramMap));
        try {
            String consigneeName = CommonUtils.getValue(paramMap, "consigneeName");
            String consigneeTel = CommonUtils.getValue(paramMap, "consigneeTel");
            String consigneeAddr = CommonUtils.getValue(paramMap, "consigneeAddr");
            String userId = CommonUtils.getValue(paramMap, "userId");
            String empTel =  CommonUtils.getValue(paramMap, "empTel");
            if(StringUtils.isEmpty(consigneeTel) || StringUtils.isEmpty(consigneeAddr)
                    || StringUtils.isEmpty(consigneeName) || StringUtils.isEmpty(empTel)){
                throw new BusinessException("请完整输入信息！");
            }
            if(consigneeName.length() > 5){
                throw new BusinessException("收货人姓名不合法！");
            }
            if(consigneeAddr.length() > 40){
                throw new BusinessException("收货地址不合法！");
            }
            if(!RegExpUtils.mobiles(empTel)){
                return Response.fail("收货手机号不合法！");
            }
            //3,校验该员工所属分公司领取免费礼品是否已达上限
            //判断是否进入领取奖励页面
            //如果没有userId就不校验此步骤
            ZYResponseVo zyqh = zhongYuanQHService.getZYQH(empTel);
            if(!StringUtils.isEmpty(userId)){
                ZYEmpInfoVo zyEmpInfoVo = zyqh.getResult().get(0);
                //发优惠券,先获取活动id,根据活动id，找对应优惠券（该活动下只配一张优惠券）分发给用户
                long activityId = zyPriceCollecService.getZyActicityCollecId();
                if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_ER.getMessage())){
                    myCouponManagerService.giveCouponToUser(userId,activityId,5,empTel);
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));
                    if(upflag){
                        return Response.fail("领取成功,优惠券已发放到你的帐户");
                    }
                }else if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_SAN.getMessage())){
                    myCouponManagerService.giveCouponToUser(userId,activityId,10,empTel);
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));
                    if(upflag){
                        return Response.fail("领取成功，优惠券已发放到你的帐户");
                    }
                }else {
                    throw new BusinessException("请告知该员工是几重奖");
                }
            }

            ZYEmpInfoVo empInfoVo = zyqh.getResult().get(0);
            ZYPriceCollecEntity zyPriceCollecEntity = new ZYPriceCollecEntity();
            zyPriceCollecEntity.setCompanyName(empInfoVo.getCompanyName());
            zyPriceCollecEntity.setConsigneeAddr(consigneeAddr);
            zyPriceCollecEntity.setConsigneeName(consigneeName);
            zyPriceCollecEntity.setConsigneeTel(consigneeTel);
            Date n = new Date();
            zyPriceCollecEntity.setCreatedTime(n);
            zyPriceCollecEntity.setUpdatedTime(n);
            zyPriceCollecEntity.setIsDelete("N");
            zyPriceCollecEntity.setEmpTel(empTel);
            zyPriceCollecEntity.setEmpName(empInfoVo.getEmpName());
            zyPriceCollecEntity.setQhRewardType(empInfoVo.getQHRewardType());
            zyPriceCollecEntity.setGoodsName("小米背包");
            zyPriceCollecEntity.setUserId(userId);
            zyPriceCollecEntity.setActivityId(zyPriceCollecService.getZyActicityCollecId() + "");
            zyPriceCollecService.addPriceCollec(zyPriceCollecEntity);

            return  Response.success("恭喜您成功领取小米背包,请您留意邮寄奖品和账户电子优惠券。");
        }catch (BusinessException be){
            LOGGER.error("领取活动商品接口异常啦----Exception----",be);
            return Response.fail(be.getErrorDesc());
        }catch (Exception e) {
            LOGGER.error("领取活动商品接口异常啦----Exception----",e);
            return Response.fail("服务器忙，请稍后再试!");
        }

    }

    @RequestMapping("/getAuthCode")
    @ResponseBody
    public Response getAuthCode(@RequestBody Map<String,Object> paramMap){
        try{
            String mobile = CommonUtils.getValue(paramMap, "mobile");
            LOGGER.info("sendAuthCode, 发送验证码入参:{}", GsonUtils.toJson(paramMap));
            mobile = mobile.replace(" ", "");

            if (StringUtils.isAnyBlank(mobile)) {
                return Response.fail("验证码接收手机号不能为空");
            }else if (!RegExpUtils.mobiles(mobile)) {
                LOGGER.error("手机号格式不正确,请重新输入！");
                return Response.fail("手机号格式不正确,请重新输入！");
            }

            //2,校验员工是否是否是中原员工
            ZYResponseVo zyqh = zhongYuanQHService.getZYQH(mobile);
            if(!zyqh.isSuccess()){
                throw new RuntimeException("您未通过中原总裁办认证，请认证后领取");
            }

            // 判断短信验证码是否在有效期内，不在发送
            Boolean flage = mobileRandomService.getCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
            if (flage) {
                mobileRandomService.sendMobileVerificationCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
                return Response.success("验证码发送成功,请注意查收");
            }else{
                return Response.fail("验证码仍有效，请两分钟后 再次获取");
            }
        }catch (Exception e){
            LOGGER.error("获取验证码失败,------Exception-----",e);
            return Response.fail(e.getMessage());
        }

    }

    /**
     * 校验手机号
     * @param paramMap
     * @return
     */
    @RequestMapping("/checkMessage")
    @ResponseBody
    public Response checkUser(@RequestBody Map<String,Object> paramMap){
        LOGGER.info("ZhongYuanWelfareController-->checkUser方法接收参数:{}", GsonUtils.toJson(paramMap));
        try{
            //获取参数
            String mobile = CommonUtils.getValue(paramMap,"mobile");
            String authCode = CommonUtils.getValue(paramMap,"authCode");
            String userId = CommonUtils.getValue(paramMap,"userId");
            if(StringUtils.isEmpty(mobile)){
                throw new BusinessException("手机号码不能为空");
            }


            if(systemEnvConfig.isPROD()){
                if(StringUtils.isEmpty(authCode)){
                    throw new BusinessException("验证码不能为空");
                }
                //校验验证码是否过期
                boolean expireFlag = mobileRandomService.getCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
                if(expireFlag){
                    throw new BusinessException("验证码已失效，请重新获取!");
                }
                //1,校验验证码是否正确
                boolean codeFlage = mobileRandomService.mobileCodeValidate(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile, authCode);
                if(!codeFlage){
                    throw new BusinessException("验证码不正确!");
                }
            }else{
                //测试环境不校验验证码
                if(!"123456".equals(authCode)){
                    throw new BusinessException("测试环境验证码为123456");
                }
            }


            //2,校验员工是否是否是中原员工
            ZYResponseVo zyqh = zhongYuanQHService.getZYQH(mobile);
            if(!zyqh.isSuccess()){
                throw new BusinessException("您未通过中原总裁办认证，请认证后领取");
            }

            //已达到上限在此处领优惠券,不进入领取货物界面
            if(!StringUtils.isEmpty(userId)){
                ZYEmpInfoVo zyEmpInfoVo = zyqh.getResult().get(0);
                //发优惠券,先获取活动id,根据活动id，找对应优惠券（该活动下只配一张优惠券）分发给用户
                long activityId = zyPriceCollecService.getZyActicityCollecId();
                //校验手机号是否已领取优惠券,如果已领取给出提示,否则发放优惠券
                List<ProMyCoupon> myCoupons = myCouponManagerService.selectMycouponCountByRelateTel(activityId,mobile);
                if(!CollectionUtils.isEmpty(myCoupons)){
                    if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                            QHRewardTypeEnums.ZHONGYUAN_YI.getMessage())){
                        return Response.fail("该员工奖励已被其他账号领取");
                    }else{
                        //判断该中原员工是否领包
                        ZYPriceCollecEntity zyPriceCollecEntity = zyPriceCollecService.selectByEmpTel(zyEmpInfoVo.getEmpTel(),zyPriceCollecService.getZyActicityCollecId() + "");
                        if(zyPriceCollecEntity != null){
                            return Response.fail("该员工奖励已被其他账号领取");
                        }
                    }
                }

                //如果是一重奖直接发优惠券，不进入领取奖品页
                if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_YI.getMessage())){
                        myCouponManagerService.giveCouponToUser(userId,activityId,2,mobile);
                        return Response.fail("领取成功，优惠券已发放到你的帐户");
                }else if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_ER.getMessage())){
                    if(StringUtils.equals("济南",zyEmpInfoVo.getCompanyName())){
                        //先发优惠券
                        myCouponManagerService.giveCouponToUser(userId,activityId,5,mobile);
                        //不领包、返回提示
                        return Response.fail("济南地区:奖励已安排线下发放");
                    }
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));

                    if(upflag){
                        myCouponManagerService.giveCouponToUser(userId,activityId,5,mobile);
                        return Response.fail("领取成功，优惠券已发放到你的帐户");
                    }
                }else if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_SAN.getMessage())){
                    if(StringUtils.equals("济南",zyEmpInfoVo.getCompanyName())){
                        myCouponManagerService.giveCouponToUser(userId,activityId,10,mobile);
                        return Response.fail("济南地区:奖励已安排线下发放");
                    }
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));
                    if(upflag){
                        myCouponManagerService.giveCouponToUser(userId,activityId,10,mobile);
                        return Response.fail("领取成功，优惠券已发放到你的帐户");
                    }
                }else {
                    throw new BusinessException("请告知该员工是几重奖");
                }
            }

        }catch (BusinessException be){
            LOGGER.error("校验未通过,--Exception---",be);
            return Response.fail(be.getErrorDesc());
        }catch (Exception e){
            LOGGER.error("校验未通过,--Exception---:{}",e);
            return Response.fail("服务器忙请稍后再试");
        }

        return Response.success("校验通过，请继续下一步操作");
    }

    @RequestMapping("/listCompanyAwards")
    @ResponseBody
    public Response listCompanyAwards(){
        ZYCompanyCityAwardsVo v1 = new ZYCompanyCityAwardsVo("济南","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v2 = new ZYCompanyCityAwardsVo("长沙","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v3 = new ZYCompanyCityAwardsVo("佛山","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v4 = new ZYCompanyCityAwardsVo("北京","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v5 = new ZYCompanyCityAwardsVo("惠州","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v6 = new ZYCompanyCityAwardsVo("武汉","三重奖","1000元代金券");
        ZYCompanyCityAwardsVo v7 = new ZYCompanyCityAwardsVo("长春","二重奖","500元代金券");
        ZYCompanyCityAwardsVo v8 = new ZYCompanyCityAwardsVo("深圳","二重奖","500元代金券");
        ZYCompanyCityAwardsVo v9 = new ZYCompanyCityAwardsVo("东莞","二重奖","500元代金券");
        ZYCompanyCityAwardsVo v10 = new ZYCompanyCityAwardsVo("哈尔滨","二重奖","500元代金券");
        ZYCompanyCityAwardsVo v11 = new ZYCompanyCityAwardsVo("昆明","二重奖","500元代金券");
        ZYCompanyCityAwardsVo v12 = new ZYCompanyCityAwardsVo("南宁","一重奖","200元代金券");
        ZYCompanyCityAwardsVo v13 = new ZYCompanyCityAwardsVo("大连","一重奖","200元代金券");
        ZYCompanyCityAwardsVo v14 = new ZYCompanyCityAwardsVo("重庆","一重奖","200元代金券");
        ZYCompanyCityAwardsVo v15 = new ZYCompanyCityAwardsVo("沈阳","一重奖","200元代金券");
        ZYCompanyCityAwardsVo v16 = new ZYCompanyCityAwardsVo("杭州","一重奖","200元代金券");
        List<ZYCompanyCityAwardsVo> result = new ArrayList<>();
        result.add(v1);
        result.add(v2);
        result.add(v3);
        result.add(v4);
        result.add(v5);
        result.add(v6);
        result.add(v7);
        result.add(v8);
        result.add(v9);
        result.add(v10);
        result.add(v11);
        result.add(v12);
        result.add(v13);
        result.add(v14);
        result.add(v15);
        result.add(v16);
        return Response.success("ok",result);

    }

}
