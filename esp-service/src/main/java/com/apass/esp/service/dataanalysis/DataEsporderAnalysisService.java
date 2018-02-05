package com.apass.esp.service.dataanalysis;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.DataEsporderAnalysis;
import com.apass.esp.domain.entity.DataEsporderdetail;
import com.apass.esp.domain.entity.goods.GoodsBasicInfoEntity;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.esp.domain.vo.DataEsporderAnalysisVo;
import com.apass.esp.domain.vo.DataEsporderdetailVo;
import com.apass.esp.mapper.DataEsporderAnalysisMapper;
import com.apass.esp.service.goods.GoodsService;
import com.apass.esp.service.order.OrderDetailInfoService;
import com.apass.esp.service.order.OrderService;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataEsporderAnalysisService {
	@Autowired
	private DataEsporderAnalysisMapper dataEsporderAnalysisMapper;
	@Autowired
	private DataEsporderdetailService dataEsporderdetailService;
	@Autowired
	private DataAppuserAnalysisService dataAppuserAnalysisService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailInfoService orderDetailInfoService;
	@Autowired
	private GoodsService goodsService;
	/**
	 * CREATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer createdEntity(DataEsporderAnalysis entity){
		return dataEsporderAnalysisMapper.insertSelective(entity);
	}
	/**
	 * DELETE
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(Long id){
		return dataEsporderAnalysisMapper.deleteByPrimaryKey(id);
	}
	/**
	 * DELETE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer deleteEntity(DataEsporderAnalysis entity){
		return dataEsporderAnalysisMapper.deleteByPrimaryKey(entity.getId());
	}
	/**
	 * UPDATE
	 * @param entity
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public Integer updateEntity(DataEsporderAnalysis entity){
		return dataEsporderAnalysisMapper.updateByPrimaryKeySelective(entity);
	}
	/**
	 * 运营分析数据载入
	 * 参数含有
	 * @param dateStart
	 * @param dateEnd
	 * @param platformids
	 * @return
	 * @throws BusinessException 
	 */
	public Response getOperationAnalysisList(Map<String, Object> map) throws BusinessException {
		map = conversionParam(map);
		List<DataEsporderAnalysis> list = dataEsporderAnalysisMapper.getOperationAnalysisList(map);
		List<DataEsporderAnalysisVo> voflist = new ArrayList<DataEsporderAnalysisVo>();
		for(DataEsporderAnalysis entity : list){
			DataEsporderAnalysisVo vof = new DataEsporderAnalysisVo();
			Long orderAnalysisId = entity.getId();
			map.put("orderAnalysisId", orderAnalysisId);
			List<DataEsporderdetail> orderlist = dataEsporderdetailService.getDataEsporderdetailList(map);
			String dayData = DateFormatUtil.string2string(entity.getTxnId(), "yyyyMMdd", "MM月dd日");
			DataAppuserAnalysis dataAppuserAnalysis = dataAppuserAnalysisService.getDataAnalysisByTxnId(new DataAnalysisVo(entity.getTxnId(), map.get("platformids").toString(), "2","00"));
			List<DataEsporderdetailVo> orderVolist = new ArrayList<DataEsporderdetailVo>();
			for(DataEsporderdetail order : orderlist){
				DataEsporderdetailVo vo = new DataEsporderdetailVo();
				BeanUtils.copyProperties(order, vo);
				GoodsBasicInfoEntity goods = goodsService.serchGoodsByGoodsId(order.getGoodsId().toString());
				if(goods!=null){
					vo.setGoodsName(goods.getGoodsName());
				}
				orderVolist.add(vo);
			}
			vof.setDayData(dayData);
			BeanUtils.copyProperties(dataAppuserAnalysis, vof);
			BeanUtils.copyProperties(entity, vof);
			vof.setId(orderAnalysisId);
			vof.setList(orderVolist);
			voflist.add(vof);
		}
		return Response.success("运营分析数据载入成功！", voflist);
	}
	/**
	 * 转化参数
	 * @param map
	 * @return
	 * @throws BusinessException 
	 */
	private Map<String, Object> conversionParam(Map<String, Object> map) throws BusinessException {
		String days = CommonUtils.getValue(map, "days");
		Date now = new Date();
		Date date = null;
		String dateStart = null;
		String dateEnd = DateFormatUtil.dateToString(now, "yyyyMMdd");
		if(StringUtils.isBlank(days)){
//			date = DateFormatUtil.addDays(now, -7);
//			dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
//			map.put("dateStart", dateStart);
//			map.put("dateEnd", dateEnd);
//			map.put("platformids", "0");
			return map;
		}
		map.put("dateEnd", dateEnd);
		switch (days) {
			case "0":
				dateStart = dateEnd;
				map.put("dateStart", dateStart);
				break;
			case "-1":
				date = DateFormatUtil.addDays(now, -1);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
			case "-7":
				date = DateFormatUtil.addDays(now, -7);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
			case "-30":
				date = DateFormatUtil.addDays(now, -30);
				dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
				map.put("dateStart", dateStart);
				break;
		}
		return map;
	}
	/**
	 * 商城订单统计
     * 每日2点执行，刷新昨日订单统计
     * 针对 t_data_esporder_analysis 和  t_data_esporderdetail
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public void flushEsporderAnalysis() {
		Date now = new Date();
		Date date = DateFormatUtil.addDays(now, -1);
		String txnId = DateFormatUtil.dateToString(date, "yyyyMMdd");
		String day = DateFormatUtil.dateToString(date, "yyyy-MM-dd");
		DataEsporderAnalysis entity = new DataEsporderAnalysis();
//		txnId = "20180112";
//		day = "2018-01-12";
		entity.setTxnId(txnId);
		entity.setIsDelete("00");
		String beginDate = day + " 00:00:00";
		String endDate = day + " 23:59:59";
		List<OrderInfoEntity> orderlist = orderService.getSectionOrderList(beginDate, endDate);
		if(orderlist==null||orderlist.size()==0){
			//无订单直接 插入总表 t_data_esporder_analysis  详情表t_data_esporderdetail无需插入
			entity.setPercentConv(new BigDecimal(0));
			createdEntity(entity);
		}else{
			//统计总表字段数据  t_data_esporder_analysis
			Long orderAnalysisId = createdOrderEntity(entity, orderlist);
			//统计详情表字段数据 t_data_esporderdetail
			createdOrderDetailEntity(orderAnalysisId,orderlist);
		}
	}
	/**
	 * 统计订单总量 t_data_esporder_analysis
	 * @param entity
	 * @param orderlist
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	private Long createdOrderEntity(DataEsporderAnalysis entity , List<OrderInfoEntity> orderlist){
		String userIdStr = "";
		String userIdStrByPayfalg = "";
		Boolean payfalg = true;
		Integer confirmNum = 0;//下单人数   去重
		Integer confirmGoodsNum = 0;//下单商品件数
		BigDecimal confirmAmt = new BigDecimal(0);//下单总金额
		Integer payNum = 0;//支付人数  去重
		Integer payGoodsNum = 0;//支付商品件数
		BigDecimal payAmt = new BigDecimal(0);//支付总金额
		BigDecimal percentConv = new BigDecimal(0);//支付下单环比
		for(OrderInfoEntity order : orderlist){
			payfalg = true;
			Long userId = order.getUserId();
			if(!userIdStr.contains(userId.toString()+",")){//根据userId或者用户手机号去重
				userIdStr=userIdStr+userId.toString()+",";
				confirmNum ++;
			}
			confirmAmt = confirmAmt.add(order.getOrderAmt());
			if(order.getPayTime()!=null){//支付时间非空
				payfalg = false;
				if(!userIdStrByPayfalg.contains(userId.toString()+",")){//根据userId或者用户手机号去重
					userIdStrByPayfalg=userIdStrByPayfalg+userId.toString()+",";
					payNum ++;
				}
				payAmt = payAmt.add(order.getOrderAmt());
			}
			List<OrderDetailInfoEntity> orderDetaillist = orderDetailInfoService.queryOrderDetailInfo(order.getOrderId());
			for(OrderDetailInfoEntity orderDetail : orderDetaillist){
				confirmGoodsNum = confirmGoodsNum + Integer.parseInt(orderDetail.getGoodsNum().toString());
				if(!payfalg){
					payGoodsNum = payGoodsNum + Integer.parseInt(orderDetail.getGoodsNum().toString());
				}
			}
		}
		percentConv = new BigDecimal(payNum).divide(new BigDecimal(confirmNum),4);
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setConfirmNum(confirmNum);
		entity.setConfirmGoodsNum(confirmGoodsNum);
		entity.setConfirmAmt(confirmAmt);
		entity.setPayNum(payNum);
		entity.setPayGoodsNum(payGoodsNum);
		entity.setPayAmt(payAmt);
		entity.setPercentConv(percentConv);
		createdEntity(entity);
		return entity.getId();
	}
	/**
	 * 统计订单详情商品总量 t_data_esporderdetail
	 * @param orderAnalysisId
	 * @param orderlist
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	private List<DataEsporderdetail> createdOrderDetailEntity(Long orderAnalysisId, List<OrderInfoEntity> orderlist) {
		Map<Long,List<OrderDetailInfoEntity>> map = new HashMap<Long,List<OrderDetailInfoEntity>>();
		for(OrderInfoEntity order : orderlist){//所有订单详情  根据商品分组。
			List<OrderDetailInfoEntity> orderDetaillist = orderDetailInfoService.queryOrderDetailInfo(order.getOrderId());
			for(OrderDetailInfoEntity orderDetail : orderDetaillist){
				Long goodsId = orderDetail.getGoodsId();
				if(map.get(goodsId)!=null){//所有订单详情  根据商品ID分组。
					List<OrderDetailInfoEntity> value = map.get(goodsId);
					value.add(orderDetail);
				}else{
					List<OrderDetailInfoEntity> value = new ArrayList<OrderDetailInfoEntity>();
					value.add(orderDetail);
					map.put(goodsId, value);
				}
			}
		}
		List<DataEsporderdetail> dlist = new ArrayList<DataEsporderdetail>();
		Set<Entry<Long, List<OrderDetailInfoEntity>>> set = map.entrySet();
		for(Entry<Long, List<OrderDetailInfoEntity>> entry : set){
			Integer confirmGoodsNum = 0;//下单商品件数
			BigDecimal confirmAmt = new BigDecimal(0);//下单总金额
			Integer payGoodsNum = 0;//支付商品件数
			BigDecimal payAmt = new BigDecimal(0);//支付总金额
			BigDecimal percentConv = new BigDecimal(0);//支付下单环比
			Long goodsId = entry.getKey();
			DataEsporderdetail entity = new DataEsporderdetail();
			entity.setCreatedTime(new Date());
			entity.setUpdatedTime(new Date());
			entity.setIsDelete("00");
			entity.setOrderAnalysisId(orderAnalysisId);
			entity.setGoodsId(goodsId);
			List<OrderDetailInfoEntity> value = entry.getValue();
			for(OrderDetailInfoEntity orderDetail : value){
				BigDecimal actualPay = orderDetail.getGoodsPrice().subtract(orderDetail.getDiscountAmount()).subtract(orderDetail.getCouponMoney());
				String orderId = orderDetail.getOrderId();
				OrderInfoEntity order = orderService.getOrderInfoEntityByOrderId(orderId);
				confirmGoodsNum = confirmGoodsNum + Integer.parseInt(orderDetail.getGoodsNum().toString());
				confirmAmt = confirmAmt.add(actualPay);
				if(order.getPayTime()!=null){
					payGoodsNum = payGoodsNum + Integer.parseInt(orderDetail.getGoodsNum().toString());
					payAmt = payAmt.add(actualPay);
				}
			}
			percentConv = new BigDecimal(payGoodsNum).divide(new BigDecimal(confirmGoodsNum),4);
			entity.setConfirmGoodsNum(confirmGoodsNum);
			entity.setConfirmAmt(confirmAmt);
			entity.setPayGoodsNum(payGoodsNum);
			entity.setPayAmt(payAmt);
			entity.setPercentConv(percentConv);
			dataEsporderdetailService.createdEntity(entity);
			dlist.add(entity);
		}
		return dlist;
	}
}