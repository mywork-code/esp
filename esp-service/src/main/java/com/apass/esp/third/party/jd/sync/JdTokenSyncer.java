package com.apass.esp.third.party.jd.sync;

import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.client.JdTokenClient;
import com.apass.gfb.framework.cache.CacheManager;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(JdTokenSyncer.class);
    private static final String JD_TOKEN_REDIS_KEY = "JD_TOKEN_REDIS_KEY";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JdTokenClient jdTokenClient;

    @Autowired
    private SystemEnvConfig systemEnvConfig;

    @Override
    public void run() {
        if (systemEnvConfig.isPROD()) {
            LOGGER.info(getName() + "  sync exec....................................");
            String json = cacheManager.get(JD_TOKEN_REDIS_KEY);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String time = jsonObject.getString("time");
            long interVal = System.currentTimeMillis() - Long.valueOf(time);
            if (3600 * 24 * 7 * 1000 <= interVal) {
                JSONObject jsonObject1 = jdTokenClient.getToken();
                cacheManager.set(JD_TOKEN_REDIS_KEY, jsonObject1.toJSONString());
            }
        }
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
