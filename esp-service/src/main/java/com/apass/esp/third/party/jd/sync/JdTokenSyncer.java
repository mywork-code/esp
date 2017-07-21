package com.apass.esp.third.party.jd.sync;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.gfb.framework.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * type: class
 * 京东token同步
 * token有效期剩余半数以上，重新获取,token字符串不变，有效期不变。（token获取接口调用限制次数30次/月）
 * token有效期剩余不足半数，重新获取,token字符串不变，有效期恢复30天。
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class JdTokenSyncer extends AbstractSyncer {
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @Override
    public void run() {
        JSONObject jsonObject = jdTokenClient.getToken();
        cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject.toJSONString());
    }

    @Override
    protected String getName() {
        return "jdTokenSyncer";
    }

    protected boolean isFrequently() {
        return true;
    }

    @Override
    protected int getIntervalSeconds() {
        return 3600 * 24 * 7;
    }
}
