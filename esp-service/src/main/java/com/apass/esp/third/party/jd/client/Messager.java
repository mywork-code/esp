package com.apass.esp.third.party.jd.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apass.esp.third.party.jd.entity.base.JdApiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * type: class
 *
 * @author xianzhi.wang
 * @see
 * @since JDK 1.8
 */
@Service
public class Messager {

    @Autowired
    private JdProductApiClient jdProductApiClient;

    public List<JdApiMessage> getJdApiMessages(int... types) {
        JdApiResponse<JSONArray> response = jdProductApiClient.messageGet(types);
        if (response.isSuccess()) {
            List<JdApiMessage> results = new ArrayList<>();
            for (Object o : response.getResult()) {
                results.add(new JdApiMessage((JSONObject) o));
            }
            return results;
        }
        return Collections.emptyList();
    }

    public boolean delete(long messageId) {
        JdApiResponse<Boolean> response = jdProductApiClient.messageDel(messageId);
        return response.isSuccess() && response.getResult().booleanValue();
    }
}
