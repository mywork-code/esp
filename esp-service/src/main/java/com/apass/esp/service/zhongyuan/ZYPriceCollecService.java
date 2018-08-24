package com.apass.esp.service.zhongyuan;

import com.apass.esp.domain.entity.ZYPriceCollecEntity;
import com.apass.esp.mapper.ZYPriceCollecEntityMapper;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public long getZyActicityCollecId(){//TODO 生产环境写死activityId
        if(systemEnvConfig.isPROD()){
            return 0l;
        }else{
            return 315L;
        }
    }


    public void addPriceCollec(ZYPriceCollecEntity zyPriceCollecEntity) throws BusinessException{
       Integer count = zyPriceCollecEntityMapper.countByQHRewardType(zyPriceCollecEntity.getQhRewardType(),
                zyPriceCollecEntity.getCompanyName(),String.valueOf(getZyActicityCollecId()));
        int max;
        if(systemEnvConfig.isPROD()){
            max = 100;
        }else {
            max = 5;
        }
       if(count > max){
           throw new BusinessException("奖品领取已达到上限！");
       }
        zyPriceCollecEntityMapper.insertSelective(zyPriceCollecEntity);

    }

    /**
     * 校验奖励盗取是否已达上限：根据qhRewardType字段和companyName判断
     * @param qhRewardType
     * @param companyName
     * @return true--已达上限，false--未达上限
     */
    public boolean ifUpflag(String qhRewardType,String companyName,String activityId){
        Integer count = zyPriceCollecEntityMapper.countByQHRewardType(qhRewardType, companyName,activityId);
        int max;
        if(systemEnvConfig.isPROD()){
            max = 100;
        }else {
            max = 5;
        }
        if(count>max){//TODO
            return true;
        }
        return false;
    }


    public List<ZYPriceCollecEntity> getAllZYCollecByStartandEndTime( String startDate, String endDate) {
        Map<String,Object> paramMap1 = Maps.newHashMap();
        paramMap1.put("startDate",startDate);
        paramMap1.put("endDate",endDate);
        return zyPriceCollecEntityMapper.selectAllCollec(paramMap1);
    }
}
