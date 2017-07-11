package com.apass.esp.noauth.common;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apass.esp.common.code.BusinessErrorCode;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.common.DictDTO;
import com.apass.esp.service.nation.NationService;
import com.apass.gfb.framework.utils.CommonUtils;

/**
 * 省份、城市、区域 查询
 * 
 * @description
 *
 * @author liuchao01
 * @version $Id: RegionController.java, v 0.1 2017年1月11日 下午2:43:20 liuchao01 Exp
 *          ${0xD}
 */
@Path("/region")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    public NationService        nationService;

    /**
     * 查询省份、城市、区 列表
     */
    @POST
    @Path("/list")
    public Response selectProvinceList(Map<String, Object> paramMap) {
        try {
            String code = CommonUtils.getValue(paramMap, "code");
            String cityFlag = CommonUtils.getValue(paramMap, "flag");
            if (StringUtils.isBlank(code)) {
                code = "000000";
            }
            boolean flag = true;
            if (StringUtils.isNotBlank(cityFlag)) {
                flag = Boolean.valueOf(cityFlag);
            }

            List<DictDTO> dictList = nationService.queryDistrict(code, flag);
            return Response.success("success", dictList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }
    /**
     * 查询省份、城市、区 列表(京东)
     */
    @POST
    @Path("/listJd")
    public Response selectProvinceListJd(Map<String, Object> paramMap) {
        try {
            String code = CommonUtils.getValue(paramMap, "code");
            if (StringUtils.isBlank(code)) {
                code = "0";
            }
            List<DictDTO> dictList = nationService.queryDistrictJd(code);
            return Response.success("success", dictList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Response.fail(BusinessErrorCode.QUREY_INFO_FAILED);
        }
    }
}
