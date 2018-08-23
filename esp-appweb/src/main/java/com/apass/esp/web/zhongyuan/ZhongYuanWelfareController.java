package com.apass.esp.web.zhongyuan;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.domain.enums.QHRewardTypeEnums;
import com.apass.esp.domain.enums.SmsTypeEnums;
import com.apass.esp.domain.vo.zhongyuan.ZYCompanyCityAwardsVo;
import com.apass.esp.domain.vo.zhongyuan.ZYEmpInfoVo;
import com.apass.esp.domain.vo.zhongyuan.ZYResponseVo;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.esp.service.common.ZhongYuanQHService;
import com.apass.esp.service.offer.MyCouponManagerService;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Maps;
import com.sun.javafx.fxml.builder.JavaFXFontBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.QHService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    //领取活动商品接口
    @RequestMapping(value = "/addAwardGoods",method = RequestMethod.POST)
    @ResponseBody
    public Response addAwardGoods(@RequestBody Map<String, Object> paramMap){
        try {
            String consigneeName = CommonUtils.getValue(paramMap, "consigneeName");
            String consigneeTel = CommonUtils.getValue(paramMap, "consigneeTel");
            String consigneeAddr = CommonUtils.getValue(paramMap, "consigneeAddr");
            String userId = CommonUtils.getValue(paramMap, "userId");
            String empTel =  CommonUtils.getValue(paramMap, "empTel");
            if(StringUtils.isEmpty(consigneeTel) || StringUtils.isEmpty(consigneeAddr)
                    || StringUtils.isEmpty(consigneeName) || StringUtils.isEmpty(empTel)){
                return Response.fail("请完整输入信息！");
            }
            if(consigneeName.length() > 5){
                return Response.fail("收货人姓名不合法！");
            }
            if(consigneeAddr.length() > 40){
                return Response.fail("收货地址不合法！");
            }
            if(consigneeTel.length() > 11){
                return Response.fail("收货手机号不合法！");
            }
            QHService qhService = new QHService(ZhongYuanQHService.qhServiceWsdl);
            String response = qhService.getQHServiceSoap().getQH(empTel);
            ZYResponseVo responseVo = GsonUtils.convertObj(response, ZYResponseVo.class);

            if(!responseVo.isSuccess()){
                return Response.fail("该手机号未中奖，无法领取奖品");
            }
            ZYEmpInfoVo empInfoVo = responseVo.getResult().get(0);
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

            return  Response.success("领取成功！");
        }catch (Exception e) {
            return Response.fail(e.getMessage());
        }

    }

    @RequestMapping("/getAuthCode")
    @ResponseBody
    public Response getAuthCode(@RequestBody Map<String,Object> paramMap){
        try{
            String mobile = CommonUtils.getValue(paramMap, "mobile");
            LOGGER.info("sendAuthCode, 发送验证码入参:{}", GsonUtils.toJson(paramMap));
            mobile = mobile.replace(" ", "");

            Pattern p = Pattern.compile("^1[0-9]{10}$");
            Matcher m = p.matcher(mobile);
            if (StringUtils.isAnyBlank(mobile)) {
                return Response.fail("验证码接收手机号不能为空");
            }else if (!m.matches()) {
                LOGGER.error("手机号格式不正确,请重新输入！");
                return Response.fail("手机号格式不正确,请重新输入！");
            }

            // 判断短信验证码是否在有效期内，不在发送
            Boolean Flage = mobileRandomService.getCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
            if (Flage) {
                mobileRandomService.sendMobileVerificationCode(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile);
                return Response.success("验证码发送成功,请注意查收");
            }
        }catch (Exception e){
            LOGGER.error("获取验证码失败,------Exception-----");
        }


        return Response.fail("服务器忙，请稍后再试");
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
                throw new RuntimeException("手机号码不能为空");
            }
            if(StringUtils.isEmpty(authCode)){
                throw new RuntimeException("验证码不能为空");
            }
            //1,校验验证码是否正确
            boolean codeFlage = mobileRandomService.mobileCodeValidate(SmsTypeEnums.ZHONGYUAN_LINGQU.getCode(), mobile, authCode);
            if(!codeFlage){
                throw new RuntimeException("验证码不正确!");
            }
            //2,校验员工是否是否是中原员工
            ZYResponseVo zyqh = zhongYuanQHService.getZYQH(mobile);
            if(!zyqh.isSuccess()){
                throw new RuntimeException("您不是中原员工，不能参与该活动");
            }

            //3,校验该员工所属分公司领取免费礼品是否已达上限
            //判断是否进入领取奖励页面
            //如果没有userId就不校验此步骤
            if(!StringUtils.isEmpty(userId)){
                ZYEmpInfoVo zyEmpInfoVo = zyqh.getResult().get(0);
                //发优惠券,先获取活动id,根据活动id，找对应优惠券（该活动下只配一张优惠券）分发给用户
                long activityId = zyPriceCollecService.getZyActicityCollecId();
                if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_YI.getMessage())){
                    myCouponManagerService.giveCouponToUser(userId,activityId,2,mobile);
                    return Response.fail("领取成功，优惠券已发放到你的帐户");
                }else if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_ER.getMessage())){
                    myCouponManagerService.giveCouponToUser(userId,activityId,5,mobile);
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));
                    if(upflag){
                        return Response.fail("领取成功,优惠券已发放到你的帐户");
                    }
                }else if(StringUtils.equals(zyEmpInfoVo.getQHRewardType(),
                        QHRewardTypeEnums.ZHONGYUAN_SAN.getMessage())){
                    myCouponManagerService.giveCouponToUser(userId,activityId,10,mobile);
                    boolean upflag = zyPriceCollecService.ifUpflag(zyEmpInfoVo.getQHRewardType(), zyEmpInfoVo.getCompanyName(),String.valueOf(activityId));
                    if(upflag){
                        return Response.fail("领取成功，优惠券已发放到你的帐户");
                    }
                }
            }

        }catch (BusinessException be){
            LOGGER.error("校验未通过,--Exception---:{}",be);
            return Response.fail("校验未通过"+be.getMessage());
        }catch (Exception e){
            LOGGER.error("校验未通过,--Exception---:{}",e);
            return Response.fail("校验未通过"+e.getMessage());
        }

        return Response.success("校验通过，请继续下一步操作");
    }

    @RequestMapping("/listCompanyAwards")
    @ResponseBody
    public Response listCompanyAwards(){
        ZYCompanyCityAwardsVo v1 = new ZYCompanyCityAwardsVo("济南","三重奖","1000元代金券");
        List<ZYCompanyCityAwardsVo> result = new ArrayList<>();
        result.add(v1);
        return Response.success("ok",result);

    }

}
