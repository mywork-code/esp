package com.apass.esp.schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.enums.OrderStatus;
import com.apass.esp.domain.vo.ChanelStatisticsVo;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.google.common.collect.Maps;

/**
 * 电商渠道统计日报
 */
@Controller
@RequestMapping("/channel")
public class OrderChannelStatisticsScheduleTask {

	private static final Logger logger  = LoggerFactory.getLogger(OrderChannelStatisticsScheduleTask.class);
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/introduce")
	@ResponseBody
	public Response channelStatistics(){
		
		
		List<ChanelStatisticsVo> chanelStatistisList = chanelStatistisList();
		
		
		
		return Response.success("成功!");
	}

	
	public List<ChanelStatisticsVo> chanelStatistisList(){
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -1);
		List<OrderInfoEntity> orderList = orderService.getchannelStatisticsOrders(DateFormatUtil.dateToString(cal.getTime()), DateFormatUtil.dateToString(new Date()));
		
		List<ChanelStatisticsVo> chanelList = new ArrayList<ChanelStatisticsVo>();
		
		BigDecimal buyAmtSum = BigDecimal.ZERO;
		BigDecimal payAmtSum = BigDecimal.ZERO;
		
		Long buyPersonNums = 0l;
		Long payPersonNums = 0l;
		
		Map<String,String> buyPersonParams = Maps.newHashMap();
		Map<String,String> payPersonParams = Maps.newHashMap();
		
		for (OrderInfoEntity order : orderList) {
			buyAmtSum.add(order.getOrderAmt());
			if(OrderStatus.isContail(order.getStatus())){
				payAmtSum.add(order.getOrderAmt());
				if(!payPersonParams.containsKey(order.getUserId()+"")){
					payPersonNums +=1;
					payPersonParams.put(order.getUserId()+"", order.getUserId()+"");
				}
			}
			
			if(!buyPersonParams.containsKey(order.getUserId()+"")){
				//下单人数
				buyPersonNums += 1;
				buyPersonParams.put(order.getUserId()+"", order.getUserId()+"");
			}
		}
		ChanelStatisticsVo v = null;
		for (String userId : buyPersonParams.keySet()) {
			v = new ChanelStatisticsVo();
			v.setChanel(userId);
			v.setBuyAmtSum(buyAmtSum);
			v.setPayAmtSum(payAmtSum);
			v.setBuyPersonNums(buyPersonNums);
			v.setPayPersonNums(payPersonNums);
			v.setDate(DateFormatUtil.dateToString(new Date()));
			chanelList.add(v);
		}
		return chanelList;
	}
	
}
