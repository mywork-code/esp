package com.apass.esp.web.dataanalysis;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.domain.Response;
import com.apass.esp.service.dataanalysis.DataAppuserAnalysisService;
import com.apass.esp.service.dataanalysis.DataAppuserRetentionService;
import com.apass.gfb.framework.utils.CommonUtils;
/**
 * 报表相关数据  
 * 
 * 用户留存页面相关
 * 运行分析页面相关
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/operationAnalysisController")
public class OperationAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(OperationAnalysisController.class);
    @Autowired
    private DataAppuserAnalysisService dataAppuserAnalysisService;
    @Autowired
    private DataAppuserRetentionService dataAppuserRetentionService;
    /**
     * 运行分析数据载入
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOperationAnalysisList")
    public Response getOperationAnalysisList(@RequestBody Map<String, Object> map) {
        try{
            String dateType = CommonUtils.getValue(map, "dateType");
            String dateStrat = CommonUtils.getValue(map, "dateStrat");
            String dateEnd = CommonUtils.getValue(map, "dateEnd");
            return null;
//            return dataAppuserAnalysisService.getOperationAnalysisList(dateType,dateStrat,dateEnd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("运行分析数据载入失败");
        }
    }
    /**
     * 用户留存数据载入
     * @param map
     * 参数含有
	 * dateType
	 * dateStart
	 * dateEnd
	 * platformids
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAppuserRetentionList")
    public Response getAppuserRetentionList(@RequestBody Map<String, Object> map) {
    	try{
            return dataAppuserRetentionService.getAppuserRetentionList(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Response.fail("用户留存数据载入失败");
        }
    }
}