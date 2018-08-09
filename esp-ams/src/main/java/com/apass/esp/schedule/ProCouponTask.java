package com.apass.esp.schedule;

import com.apass.esp.domain.Response;
import com.apass.esp.service.offer.ProCouponTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaohai on 2018/8/9.
 */
@Component
@RequestMapping("/noauth/coupon")
public class ProCouponTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProCouponTask.class);
    @Autowired
    private ProCouponTaskService taskService;

    /**
     * 定时发送房易贷券使用情况报表
     * 思路：1，查询要导出的数据
     *      2，导入到服务器中
     *      3，发送邮件
     *
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEamil_1() {
        try {
            taskService.senProcouponEmai();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEamil_2() {
        try {
            taskService.senProcouponEmai_2();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
        }
    }

    @RequestMapping("test1")
    public Response sendEmailTest(){
        try {
            taskService.senProcouponEmai();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }

        return Response.success("邮件发送成功!");
    }
    @RequestMapping("test2")
    public Response sendEmailTest_2(){
        try {
            taskService.senProcouponEmai_2();
        }catch (Exception e){
            LOGGER.error("-----Exception------",e);
            return Response.fail("邮件发送失败！");
        }
        return Response.success("邮件发送成功!");
    }
}
