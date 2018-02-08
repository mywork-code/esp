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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.CommonUtils;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataEsporderAnalysisService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataEsporderAnalysisService.class);
	
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
	@Autowired
    private SystemEnvConfig systemEnvConfig;
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
	 * READ
	 * @param entity
	 * @return
	 */
	public DataEsporderAnalysis readDataEsporderAnalysis(DataEsporderAnalysis entity){
		return dataEsporderAnalysisMapper.readDataEsporderAnalysis(entity);
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
		long currentTime = System.currentTimeMillis();
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
			if(dataAppuserAnalysis!=null){
				BeanUtils.copyProperties(dataAppuserAnalysis, vof);
			}
			BeanUtils.copyProperties(entity, vof);
			vof.setId(orderAnalysisId);
			vof.setList(orderVolist);
			voflist.add(vof);
		}
		
		logger.info("excute method time is --->{}",(System.currentTimeMillis() - currentTime));
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
			dateStart = CommonUtils.getValue(map, "dateStart");
			dateEnd = CommonUtils.getValue(map, "dateEnd");
			dateStart = DateFormatUtil.string2string(dateStart, "yyyy-MM-dd","yyyyMMdd");
			dateEnd = DateFormatUtil.string2string(dateEnd, "yyyy-MM-dd","yyyyMMdd");
			map.put("dateStart", dateStart);
			map.put("dateEnd", dateEnd);
			return map;
		}
		date = DateFormatUtil.addDays(now, Integer.parseInt(days));
		dateStart = DateFormatUtil.dateToString(date, "yyyyMMdd");
		map.put("dateStart", dateStart);
		map.put("dateEnd", dateEnd);
		return map;
	}
	/**
	 * 订单分析   批量刷新  刷新2017年历史数据
	 */
	public void batchFlushEsporderAnalysis() {
		String startdate = "2016-12-31";
		Date start2017 = DateFormatUtil.string2date(startdate, "yyyy-MM-dd");
		Date now = new Date();
		Date date = null;
		while(true){
			date = DateFormatUtil.addDays(start2017, 1);
			//直到当天时间  跳出循环
			if(date.getTime() - now.getTime()>0){
				return;
			}
			//以下复制flushEsporderAnalysis 方法  刷新每天商城订单数据
			String txnId = DateFormatUtil.dateToString(date, "yyyyMMdd");
			String day = DateFormatUtil.dateToString(date, "yyyy-MM-dd");
			DataEsporderAnalysis entity = new DataEsporderAnalysis();
			entity.setTxnId(txnId);
			entity.setIsDelete("00");
			String beginDate = day + " 00:00:00";
			String endDate = day + " 23:59:59";
			List<OrderInfoEntity> orderlist = orderService.getSectionOrderList(beginDate, endDate);
			if(CollectionUtils.isEmpty(orderlist)){
				//无订单直接 插入总表 t_data_esporder_analysis  详情表t_data_esporderdetail无需插入
				entity.setCreatedTime(new Date());
				entity.setUpdatedTime(new Date());
				entity.setPercentConv(new BigDecimal(0));
				if(dataEsporderAnalysisMapper.readDataEsporderAnalysis(entity)==null){
					createdEntity(entity);
				}
			}else{
				//统计总表字段数据  t_data_esporder_analysis
				entity = createdOrderEntity(entity, orderlist);
				if(entity!=null){
					DataEsporderAnalysis en = dataEsporderAnalysisMapper.readDataEsporderAnalysis(entity);
					if(en==null){
						createdEntity(entity);
						//统计详情表字段数据 t_data_esporderdetail
						if(createdOrderDetailEntity(entity.getId(),orderlist)==null){
							start2017 = date;
							continue;
						}
					}else{
						Long dataEsporderAnalysisId = en.getId();
						entity.setId(dataEsporderAnalysisId);
						updateEntity(entity);
					}
				}else{
					start2017 = date;
					continue;
				}
			}
			//刷新条件
			start2017 = date;
		}
	}
	/**
	 * 商城订单统计
     * 每日2点执行，刷新昨日订单统计
     * 针对 t_data_esporder_analysis 和  t_data_esporderdetail
	 * @throws BusinessException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public void flushEsporderAnalysis() throws BusinessException {
		//sit定时任务return
		if(systemEnvConfig.isDEV()){
			return;
		}
		Date now = new Date();
		Date date = DateFormatUtil.addDays(now, -1);
		String txnId = DateFormatUtil.dateToString(date, "yyyyMMdd");
		String day = DateFormatUtil.dateToString(date, "yyyy-MM-dd");
		DataEsporderAnalysis entity = new DataEsporderAnalysis();
		entity.setTxnId(txnId);
		entity.setIsDelete("00");
		String beginDate = day + " 00:00:00";
		String endDate = day + " 23:59:59";
		List<OrderInfoEntity> orderlist = orderService.getSectionOrderList(beginDate, endDate);
		if(CollectionUtils.isEmpty(orderlist)){
			//无订单直接 插入总表 t_data_esporder_analysis  详情表t_data_esporderdetail无需插入
			entity.setCreatedTime(new Date());
			entity.setUpdatedTime(new Date());
			entity.setPercentConv(new BigDecimal(0));
			if(dataEsporderAnalysisMapper.readDataEsporderAnalysis(entity)==null){
				createdEntity(entity);
			}
		}else{
			//统计总表字段数据  t_data_esporder_analysis
			entity = createdOrderEntity(entity, orderlist);
			if(entity!=null){
				createdEntity(entity);
				//统计详情表字段数据 t_data_esporderdetail
				if(createdOrderDetailEntity(entity.getId(),orderlist)==null){
					throw new BusinessException("订单统计异常！");
				}
			}else{
				throw new BusinessException("订单统计异常！");
			}
		}
	}
	/**
	 * 统计订单总量 t_data_esporder_analysis
	 * @param entity
	 * @param orderlist
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	private DataEsporderAnalysis createdOrderEntity(DataEsporderAnalysis entity , List<OrderInfoEntity> orderlist){
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
		try{
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
			return entity;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 统计订单详情商品总量 t_data_esporderdetail
	 * @param orderAnalysisId
	 * @param orderlist
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	private List<DataEsporderdetail> createdOrderDetailEntity(Long orderAnalysisId, List<OrderInfoEntity> orderlist) {
		try{
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
		}catch(Exception e){
			return null;
		}
	}
}