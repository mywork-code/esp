package com.apass.esp.service.contract;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.apass.gfb.framework.BootApplication;

/**
 * Created by lixining on 2017/4/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebAppConfiguration
public class ContractServiceTest {
    @Autowired
    private ContractService contractService;

//    @Test
//    public void generatePDFTest() throws BusinessException {
//        contractService.generatePDF("2017040502222233");
//    }
//
//    @Test
//    public void scheduleDataListTest() {
//        contractService.schedulePSContractPDF();
//    }
}
