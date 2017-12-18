package com.apass.esp.service.activity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.entity.LimitGoodsSku;
import com.apass.esp.domain.entity.LimitUserMessage;
import com.apass.esp.mapper.LimitUserMessageMapper;
/**
 * 限时购活动用户短信提醒
 * @author wht
 *
 */
@Service
public class LimitUserMessageService {
    @Autowired
    private LimitUserMessageMapper limitUserMessageMapper;
    /**
     * 插入提醒
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = { Exception.class})
    public LimitUserMessage activityAddRemind(LimitUserMessage entity) {
        Integer i = limitUserMessageMapper.insertSelective(entity);
        if(i==1){
            return entity;
        }
        return null;
    }
    /**
     * getLimitUserMessageList
     * @param entity
     * @return
     */
    public List<LimitUserMessage> getLimitUserMessageList(LimitUserMessage entity){
        return limitUserMessageMapper.getLimitUserMessageList(entity);
    }
    /**
     * 验证用户面对该商品是否开启提醒
     * @param sku
     * @param userId
     * @return
     */
    public Boolean validateLimitUserMessage(LimitGoodsSku sku, Long userId) {
        LimitUserMessage entity = new LimitUserMessage();
        entity.setUserId(userId);
        entity.setLimitBuyActId(sku.getLimitBuyActId());
        entity.setLimitGoodsSkuId(sku.getId());
        List<LimitUserMessage> list = getLimitUserMessageList(entity);
        if(list==null||list.size()==0){
            return true;
        }
        return false;
    }
}