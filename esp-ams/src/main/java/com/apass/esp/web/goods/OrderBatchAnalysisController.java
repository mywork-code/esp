package com.apass.esp.web.goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.apass.esp.service.dataanalysis.DataEsporderAnalysisService;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
/**
 * wz商品批量上架
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods/orderBatchAnalysisController")
public class OrderBatchAnalysisController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderBatchAnalysisController.class);
	@Autowired
    private DataEsporderAnalysisService dataEsporderAnalysisService;
    /**
     * 订单分析   批量刷新  刷新2017年历史数据
     */
    @ResponseBody
    @RequestMapping("/batchFlushEsporderAnalysis")
    @LogAnnotion(operationType = "订单分析批量", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public void batchFlushEsporderAnalysis() {
        try{
        	dataEsporderAnalysisService.batchFlushEsporderAnalysis();
        }catch(Exception e){
        	LOGGER.error("订单分析批量刷新,出现异常");
        }
    }
}