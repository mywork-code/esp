package com.apass.esp.service.common;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.apass.gfb.framework.BootApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebAppConfiguration
public class CommonServiceTest {

    @Autowired
    private CommonService commonService;
    
    Long goodsStockId=80L;
    Long goodsId=51L;
    
    @Test
    public void loadLogisticInfo() throws Exception{
        BigDecimal calculateGoodsPrice = commonService.calculateGoodsPrice(goodsId,goodsStockId);
        System.out.println(calculateGoodsPrice.longValue());
        
    }
}
