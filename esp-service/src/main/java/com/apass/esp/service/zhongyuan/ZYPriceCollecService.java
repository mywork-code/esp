package com.apass.esp.service.zhongyuan;

import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DELL on 2018/8/21.
 */
@Service
public class ZYPriceCollecService {

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    //获取中原领取奖品活动id
    public long getZyActicityCollecId(){
        if(systemEnvConfig.isPROD()){
            return 0l;
        }else{
            return 0l;
        }
    }

}
