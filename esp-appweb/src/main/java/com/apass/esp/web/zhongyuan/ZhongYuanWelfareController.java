package com.apass.esp.web.zhongyuan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.apass.esp.common.utils.JsonUtil;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.domain.vo.zhongyuan.ZYEmpInfoVo;
import com.apass.esp.domain.vo.zhongyuan.ZYResponseVo;
import com.apass.esp.service.zhongyuan.ZYPriceCollecService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.QHService;

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

    @Autowired
    private ZYPriceCollecService zyPriceCollecService;

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
            QHService qhService = new QHService();
            String response = qhService.getQHServiceSoap().getQH(empTel);
            ZYResponseVo responseVo = GsonUtils.convertObj(response, ZYResponseVo.class);
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
            zyPriceCollecEntity.setGoodsName("小米包包");
            zyPriceCollecEntity.setUserId(userId);
            zyPriceCollecService.addPriceCollec(zyPriceCollecEntity);

            return  Response.success("领取成功！");
        }catch (Exception e) {
            return Response.fail(e.getMessage());
        }

    }


}
