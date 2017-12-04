package com.apass.esp.service.activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apass.esp.mapper.LimitGoodsSkuMapper;
/**
 * 限时购活动活动
 * @author wht
 *
 */
@Service
public class LimitGoodsSkuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitGoodsSkuService.class);
    @Autowired
    public LimitGoodsSkuMapper limitGoodsSkuMapper;
    
}
