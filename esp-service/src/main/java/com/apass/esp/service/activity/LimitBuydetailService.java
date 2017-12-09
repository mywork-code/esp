package com.apass.esp.service.activity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apass.esp.domain.entity.LimitBuydetail;
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
    /**
     * 查询该限时购活动商品Id查询所有用户购买记录，计算已购总量  和限购总量比较
     * @param limitGoodsSkuId
     * @return
     */
    public List<LimitBuydetail> findActCurrAmtBylimitGoodsSkuId(Long limitGoodsSkuId) {
        LimitBuydetail entity = new LimitBuydetail();
        entity.setLimitGoodsSkuId(limitGoodsSkuId);
        return limitBuydetailMapper.findActCurrAmtBylimitGoodsSkuId(entity);
    }
    /**
     * 计算 已购总量
     * @param list
     * @return
     */
    public Integer findActCurrAmtCount(List<LimitBuydetail> list) {
        Integer count = 0;
        if(list==null||list.size()==0){
            return count;
        }
        for(LimitBuydetail entity : list){
            count=count+entity.getBuyNo();
        }
        return count;
    }
}