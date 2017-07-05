package com.apass.esp.web.commons;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.common.SystemParamEntity;
import com.apass.esp.service.common.SystemParamService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 系统参数管理
 *
 * @author chenbo
 * @version $Id: SystemParamController.java, v 0.1 2017年1月11日 上午11:15:57 chenbo
 *          Exp $
 */
@Controller
@RequestMapping("/application/system/param")
public class SystemParamController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SystemParamController.class);

    @Autowired
    private SystemParamService  systemParamService;

    /**
     * 系统参数信息页面
     */
    @RequestMapping("/page")
    public String handlePage() {
        return "common/param/systemParam";
    }

    /**
     * 系统参数信息查询
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/pagelist")
    public ResponsePageBody<SystemParamEntity> querySystemParam(HttpServletRequest request) {
        ResponsePageBody<SystemParamEntity> respBody = new ResponsePageBody<SystemParamEntity>();
        try {
            List<SystemParamEntity> systemParamList = systemParamService.querySystemParamInfo();
            respBody.setRows(systemParamList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("系统参数信息查询失败", e);
            respBody.setStatus(CommonCode.FAILED_CODE);
        }
        return respBody;
    }

    /**
     * 系统参数信息修改
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public Response updateSystemParam(HttpServletRequest request) {
        try {
            // 获取请求的参数
            String id = HttpWebUtils.getValue(request, "id");
            String merchantSettleRate = HttpWebUtils.getValue(request, "merchantSettleRate");
//            String goodsPriceRate = HttpWebUtils.getValue(request, "goodsPriceRate");
            String priceCostDate = HttpWebUtils.getValue(request, "priceCostDate");
            if(StringUtils.isNotBlank(priceCostDate)){
            	priceCostDate = new BigDecimal(priceCostDate).divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
            }

            // 获取商户号
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String userName = listeningCustomSecurityUserDetails.getUsername();

            // 拼接参数
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id);
            map.put("merchantSettleRate", merchantSettleRate);
//            map.put("goodsPriceRate", goodsPriceRate);
            map.put("priceCostDate", priceCostDate);
            map.put("userName", userName);
            map.put("systemtime", DateFormatUtil.getCurrentDate());

            // 验证参数
            paramCheck(map);

            systemParamService.updateSystemParamInfo(map);
        } catch (BusinessException e) {
            LOG.error("系统参数信息查询失败", e);
            return Response.fail(e.getErrorDesc());
        }
        return Response.success("修改系统参数成功！");
    }

    public void paramCheck(Map<String, String> map) throws BusinessException {

        if (StringUtils.isAnyBlank(map.get("id"))) {
            throw new BusinessException("主键id不能为空!");
        }

        if (StringUtils.isAnyBlank(map.get("merchantSettleRate"))) {
            throw new BusinessException("商户结算折扣率不能为空!");
        }

        if (Double.parseDouble(map.get("merchantSettleRate")) < 0
            || Double.parseDouble(map.get("merchantSettleRate").toString()) > 1) {
            throw new BusinessException("商户结算折扣率字段不合法，必须在0到1之间!");
        }

//        if (StringUtils.isAnyBlank(map.get("goodsPriceRate"))) {
//            throw new BusinessException("商品价格折扣率不能为空!");
//        }
        if (StringUtils.isAnyBlank(map.get("priceCostDate"))) {
        	throw new BusinessException("保本率不能为空!");
        }

//        if (Double.parseDouble(map.get("goodsPriceRate").toString()) < 0
//            || Double.parseDouble(map.get("goodsPriceRate").toString()) > 1) {
//            throw new BusinessException("商品价格折扣率字段不合法，必须在0到1之间!");
//        }
        if (Double.parseDouble(map.get("priceCostDate").toString()) < 0) {
        	throw new BusinessException("保本率字段不合法，必须大于0");
        }
    }
}
