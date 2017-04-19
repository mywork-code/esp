package com.apass.esp.schedule;

import com.apass.esp.service.contract.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 合同生成
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class ContractScheduleTask {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractScheduleTask.class);

    @Autowired
    private ContractService contractService;

    /**
     * 每天凌晨一点执行，校验活动过期时间
     */
    @Scheduled(cron = "0 0/2 * * * *")
    public void handlePSContractPDF() {
        try {
            contractService.schedulePSContractPDF();
        } catch (Exception e) {
            LOGGER.error("购销合同PDF签章异常", e);
        }
    }
}
