package com.apass.esp.task.handler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apass.esp.service.order.OrderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/14
 * @see
 * @since JDK 1.8
 */

@JobHander(value = "demoJobHandler")
@Service
public class DemoHandler extends IJobHandler {

    @Autowired
    private OrderService orderService;


    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 3; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        
        return ReturnT.SUCCESS;
    }
    

}
