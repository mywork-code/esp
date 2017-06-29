package com.apass.gfb.framework.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.apass.gfb.framework.utils.HttpWebUtils;

public class BaseController {
    /**
     * 客户签收后过7天自动结算(可变更)
     */
    public static final int FINALLY_SETTLETIME = 7;

    /**
     * 合并导出报表的列数
     */
    public static final int COMBINE_COL = 23;

    /**
     * 日志工具
     */
    protected Logger  logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Get Long Value
     */
    protected Long getLongValue(HttpServletRequest request, String code, Long def) {
        String value = HttpWebUtils.getValue(request, code);
        if (!StringUtils.isNumeric(value)) {
            return def;
        }
        return Long.valueOf(value);
    }

    /**
     * Get Long Value
     */
    protected Long getLongValue(HttpServletRequest request, String code) {
        return getLongValue(request, code, 0L);
    }

    protected String mvcFail(String address, String msg, ModelMap model) {
        model.put("errMsg", msg);
        return address;
    }

}
