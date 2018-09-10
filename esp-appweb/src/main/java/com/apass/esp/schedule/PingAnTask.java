package com.apass.esp.schedule;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.PAUser;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.service.bill.CustomerServiceClient;
import com.apass.esp.service.home.PAUserService;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * type: class
 * es初始化
 *
 * @author xianzhi.wang
 * @date 2017/8/21
 * @see
 * @since JDK 1.8
 */
@Component
@Configurable
@EnableScheduling
@RequestMapping("/pingan/task")
@Profile("Schedule")
public class PingAnTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingAnTask.class);

    @Autowired
    private PAUserService paUserService;

    @Autowired
    private CustomerServiceClient customerServiceClient;
    /**
     * 平安保险推送：每日凌晨跑定时任务
     * 思路：
     * 1，去平安表中查询 数据
     * 2，遍历，查看是否有身份证，有-->解析各个参数，调用平安接口。
     * 3，无--调占两个接口获取身份证号--解析调平安接口
     */
    @Scheduled(cron = "0 15 0 * * *")
    public void putToPAUserSchedule() {
        //查询平安表，一个星期内数据
        Date begin = DateFormatUtil.addDays(new Date(),-1);
        String startDate = DateFormatUtil.dateToString(begin)+" 00:00:00";
        String endDate = DateFormatUtil.dateToString(begin)+" 23:59:59";
       putToPAUserMethod(startDate,endDate);
    }

    @RequestMapping("/test")
    @ResponseBody
    public Response test(String startDate,String endDate){
        putToPAUserMethod(startDate,endDate);
        return Response.success("成功");
    }


    private void putToPAUserMethod(String startDate, String endDate) {
        try{
            //根据时间区间查询
            List<PAUser> paUsers = paUserService.selectUserByRangeDate(startDate,endDate);
            LOGGER.info("平安保险推送task putToPAUserMethod(){}点击领取查询结果:{}", startDate, GsonUtils.toJson(paUsers));
            if(CollectionUtils.isEmpty(paUsers)){
                return;
            }

            for(PAUser paUser : paUsers){
                try {
                    if(paUser.getAge().intValue() == -2){
                        //-2是投保成功，不重复请求对方接口
                        continue;
                    }
                    String identity = paUser.getIdentity();
                    paUser.setUpdatedTime(new Date());
                    if(StringUtils.isEmpty(identity)){
                        //调远程接口，获取identity
                        CustomerInfo customerInfo = customerServiceClient.getDouDoutCustomerInfo(paUser.getTelephone());
                        if(customerInfo == null){
                            customerInfo = customerServiceClient.getFydCustomerInfo(paUser.getTelephone());
                        }
                        if(customerInfo == null){
                            continue;
                        }
                        convertToPaUser(paUser, customerInfo);

                        //推送数据至平安
                        boolean flag = paUserService.saveToPAInterface(paUser);
                        if(!flag) {
                            //保存数据到数据库存
                            //请求平安接口失败 age = -1,成功 age=-2
                            paUser.setAge(-1);
                        }else{
                            paUser.setAge(-2);
                        }
                        paUserService.updateSelectivePAUser(paUser);
                    }else {
                        boolean flag = paUserService.saveToPAInterface(paUser);
                        if(!flag){
                            paUser.setAge(-1);
                        }else{
                            paUser.setAge(-2);
                        }
                        paUserService.updateSelectivePAUser(paUser);
                    }
                }catch (Exception e){
                    LOGGER.error("单个平安保险零点推送task异常,---Exception---",e);
                }
            }

        }catch (Exception e){
            LOGGER.error("平安保险零点推送task异常,---Exception---",e);
        }
    }

    private void convertToPaUser(PAUser paUser, CustomerInfo customerInfo) {
        String identityNo = customerInfo.getIdentityNo();
        paUser.setUsername(customerInfo.getRealName());
        paUser.setIdentity(identityNo);
        if(StringUtils.isNotEmpty(identityNo)){
            String sexStr = CommonUtils.getIdentityGender(identityNo);
            paUser.setSex(sexStr == "女"?Byte.valueOf("0"):Byte.valueOf("1"));
            paUser.setAge(CommonUtils.getAge(identityNo));
            paUser.setBirthday(DateFormatUtil.string2date(CommonUtils.getIdentityBirth(identityNo),"yyyyMMdd"));
            paUser.setIdentity(identityNo);
        }
    }
}
