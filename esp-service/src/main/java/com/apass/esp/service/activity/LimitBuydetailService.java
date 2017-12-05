package com.apass.esp.service.activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apass.esp.mapper.LimitBuydetailMapper;
/**
 * 限时购活动用户限购数量
 * @author wht
 *
 */
@Service
public class LimitBuydetailService {
    @Autowired
    public LimitBuydetailMapper limitBuydetailMapper;
    
}
