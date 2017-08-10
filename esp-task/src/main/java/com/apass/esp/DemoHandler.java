package com.apass.esp;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @date 2017/8/10
 * @see
 * @since JDK 1.8
 */

@JobHander(value = "demoJobHandler")
@Service
public class DemoHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String... params) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }

}
