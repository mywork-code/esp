package com.apass.esp.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.vo.AgentChannelVo;
import com.apass.esp.domain.vo.ChanelStatisticsVo;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import com.google.common.collect.Maps;

/**
 * 电商渠道统计日报
 */
@Component
@Configurable
@EnableScheduling
@Profile("Schedule")
public class OrderChannelStatisticsScheduleTask {

	private static final Logger logger  = LoggerFactory.getLogger(OrderChannelStatisticsScheduleTask.class);
	
	private static final String INTERFACEFORORDER_GETCHANNELINFO="/interfaceForOrder/getChannelInfo";
	/**
     * 供房帮服务地址
     */
    @Value("${gfb.service.url}")
    protected String gfbServiceUrl;
	
    @Value("${orderchannel.daily.sendto}")
    public String sendToAddress;
    
    @Value("${orderchannel.daily.copyto}")
    public String copyToAddress;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ExportExcleCommonModel model;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void channelStatistics(){
		List<ChanelStatisticsVo> chanelStatistisList = chanelStatistisList();
		String fileName = "安家趣花电商渠道统计日报";
		String[] rowHeadArr = {"日期", "渠道", "下单买家数", "支付买家数", "下单金额", "支付金额"};
        String[] headKeyArr = {"date", "chanel", "buyPersonNums", "payPersonNums", "buyAmtSum", "payAmtSum"};
		try {
			model.generateFile(chanelStatistisList,rowHeadArr,headKeyArr,fileName);
		} catch (Exception e) {
			logger.error("export order channel with exception information ");
			e.printStackTrace();
		}
		model.sendMail(sendToAddress, copyToAddress, fileName);
	}
	
	public List<ChanelStatisticsVo> chanelStatistisList(){
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		List<OrderInfoEntity> orderList = orderService.getchannelStatisticsOrders(DateFormatUtil.dateToString(cal.getTime()), DateFormatUtil.dateToString(new Date()));
		
		List<ChanelStatisticsVo> chanelList = new ArrayList<ChanelStatisticsVo>();
		
		/**
		 * 渠道-总人数
		 */
		Map<String,Set<Long>> buyParams = Maps.newHashMap();
		/**
		 * 渠道-下单金额
		 */
		Map<String,BigDecimal> buyAmtSum = Maps.newHashMap();
		/**
		 * 渠道--支付金额
		 */
		Map<String,BigDecimal> payAmtSum = Maps.newHashMap();
		/**
		 * 渠道--支付买家数
		 */
		Map<String,Set<Long>> payParams = Maps.newHashMap();
		for (OrderInfoEntity order : orderList) {
			
			String userChannel = getChannelInfo(order.getUserId()+"");
			/**
			 * 根据渠道统计人数(渠道-总人数)
			 */
			if(buyParams.containsKey(userChannel)){
				buyParams.get(userChannel).add(order.getUserId());
			}else{
				Set<Long> ls = new HashSet<Long>();
				ls.add(order.getUserId());
				buyParams.put(userChannel,ls);
			}
			/**
			 * 根据渠道统计下单金额
			 */
			if(buyAmtSum.containsKey(userChannel)){
				BigDecimal  money =  buyAmtSum.get(userChannel).add(order.getOrderAmt());
				buyAmtSum.put(userChannel, money);
			}else{
				buyAmtSum.put(userChannel, order.getOrderAmt());
			}
			
			/**
			 * 根据渠道统计支付金额
			 */
			if(OrderStatus.isContail(order.getStatus())){
				if(payAmtSum.containsKey(userChannel)){
					BigDecimal  money =  payAmtSum.get(userChannel).add(order.getOrderAmt());
					payAmtSum.put(userChannel, money);
				}else{
					payAmtSum.put(userChannel, order.getOrderAmt());
				}
			}
			
			/**
			 * 根据渠道统计支付的人数
			 */
			if(OrderStatus.isContail(order.getStatus())){
				if(payParams.containsKey(userChannel)){
					payParams.get(userChannel).add(order.getUserId());
				}else{
					Set<Long> ls = new HashSet<Long>();
					ls.add(order.getUserId());
					payParams.put(userChannel,ls);
				}
			}
		}
		
		ChanelStatisticsVo v = null;
		for (String channel : buyParams.keySet()) {
			v = new ChanelStatisticsVo();
			v.setChanel(channel);
			v.setBuyAmtSum(buyAmtSum.get(channel).setScale(2, BigDecimal.ROUND_DOWN));
			BigDecimal payAmt = payAmtSum.get(channel);
			if(payAmt == null){
				payAmt = new BigDecimal(0);
			}
			v.setPayAmtSum(payAmt.setScale(2, BigDecimal.ROUND_DOWN));
			v.setBuyPersonNums(buyParams.get(channel).size());
			Long size = 0L;
			if(payParams.get(channel) != null){
				size = (long) payParams.get(channel).size();
			}
			v.setPayPersonNums(size);
			v.setDate(DateFormatUtil.dateToString(cal.getTime()));
			chanelList.add(v);
		}
		return chanelList;
	}
	
	/**
	 * 根据用户的ID获取用户的渠道
	 * @param userId
	 * @return
	 */
	public String getChannelInfo(String userId){
    	try {
			 Map<String,Object> map=Maps.newHashMap();
			 map.put("userId", userId);
	         String requestUrl = gfbServiceUrl + INTERFACEFORORDER_GETCHANNELINFO;
	         String reqJson = GsonUtils.toJson(map);
	         StringEntity entity = new StringEntity(reqJson, ContentType.APPLICATION_JSON);
	         String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
	         Response   response=GsonUtils.convertObj(responseJson, Response.class);
	         AgentChannelVo agent = Response.resolveResult(response, AgentChannelVo.class);
	         return agent == null ? "":agent.getAgent();
    	} catch (Exception e) {
			logger.error("判断获取用户渠道失败！", e);
		}
		return "";
    }
}
