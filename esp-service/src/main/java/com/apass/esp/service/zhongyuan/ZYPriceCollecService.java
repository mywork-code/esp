package com.apass.esp.service.zhongyuan;

import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.mapper.ZYPriceCollecEntityMapper;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DELL on 2018/8/21.
 */
@Service
public class ZYPriceCollecService {

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Autowired
    private ZYPriceCollecEntityMapper zyPriceCollecEntityMapper;

    //获取中原领取奖品活动id
    public long getZyActicityCollecId(){
        if(systemEnvConfig.isPROD()){
            return 0l;
        }else{
            return 0l;
        }
    }

    public void addPriceCollec(ZYPriceCollecEntity zyPriceCollecEntity) throws BusinessException{
       Integer count = zyPriceCollecEntityMapper.countByQHRewardType(zyPriceCollecEntity.getQhRewardType(),
                zyPriceCollecEntity.getCompanyName());
       if(count > 100){
           throw new BusinessException("奖品领取已达到上限！");
       }
        zyPriceCollecEntityMapper.insertSelective(zyPriceCollecEntity);

    }

}
