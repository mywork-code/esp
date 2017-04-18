package com.apass.esp.web.datadic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.datadic.DataDicInfoEntity;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.service.datadic.DataDicService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.utils.BaseConstants.CommonCode;
import com.apass.gfb.framework.utils.HttpWebUtils;

/**
 * 
 * @description 数据字典查询
 *
 * @author chenbo
 * @version $Id: OrderQueryController.java, v 0.1 2016年12月20日 上午11:15:57 chenbo Exp $
 */
@Controller
@RequestMapping("/application/business/datadic")
public class DataDicQueryController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(DataDicQueryController.class);

    @Autowired
    private DataDicService      dataDicService;

    /**
     * 数据字典信息查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pagelist")
    public ResponsePageBody<DataDicInfoEntity> handlePageList(HttpServletRequest request) {
        ResponsePageBody<DataDicInfoEntity> respBody = new ResponsePageBody<DataDicInfoEntity>();
        try {
            //获取请求的参数 
            String dataTypeNo = HttpWebUtils.getValue(request, "dataTypeNo");
            String dataNo = HttpWebUtils.getValue(request, "dataNo");
            //通过数据类型编号查询数据字典
            Map<String, String> map = new HashMap<String, String>();
            map.put("dataTypeNo", dataTypeNo);
            map.put("dataNo", dataNo);
            List<DataDicInfoEntity> dataDicList = dataDicService.getDataDicByparam(map);

            if (dataDicList == null) {
                respBody.setTotal(0);
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }

            respBody.setTotal(dataDicList.size());
            respBody.setRows(dataDicList);
            respBody.setStatus(CommonCode.SUCCESS_CODE);
        } catch (Exception e) {
            LOG.error("数据字典查询失败", e);
            respBody.setMsg("数据字典查询失败");
        }
        return respBody;
    }

}
