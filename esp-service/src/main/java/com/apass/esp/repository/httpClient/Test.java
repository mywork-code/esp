package com.apass.esp.repository.httpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaohai on 2017/11/3.
 */
public class Test {

    public static void main(String[] args) {
        CommonHttpClient commonHttpClient = new CommonHttpClient();
        commonHttpClient.getCustomerBasicInfoByTel("日志","15618174002");
    }
}
