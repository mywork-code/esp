package com.apass.esp.service.dataanalysis;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.entity.DataEsporderAnalysis;
import com.apass.esp.domain.entity.DataEsporderdetail;
import com.apass.esp.domain.entity.order.OrderDetailInfoEntity;
import com.apass.esp.domain.entity.order.OrderInfoEntity;
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.DataEsporderAnalysisVo;
import com.apass.esp.mapper.DataEsporderAnalysisMapper;
import com.apass.esp.service.order.OrderDetailInfoService;
import com.apass.esp.service.order.OrderService;
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
	 */
	public Response getOperationAnalysisList(Map<String, Object> map) {
		List<DataEsporderAnalysisVo> list = dataEsporderAnalysisMapper.getOperationAnalysisList(map);
		for(DataEsporderAnalysisVo entity : list){
			Long orderAnalysisId = entity.getId();
			map.put("orderAnalysisId", orderAnalysisId);
			List<DataEsporderdetail> orderlist = dataEsporderdetailService.getDataEsporderdetailList(map);
			String dayData = DateFormatUtil.string2string(entity.getTxnId(), "yyyyMMdd", "MM月dd日");
			entity.setDayData(dayData);
			DataAppuserAnalysis dataAppuserAnalysis = dataAppuserAnalysisService.getDataAnalysisByTxnId(new DataAppuserAnalysisVo(entity.getTxnId(), map.get("platformids").toString(), "2"));
			BeanUtils.copyProperties(dataAppuserAnalysis, entity);
			entity.setId(orderAnalysisId);
			entity.setList(orderlist);
		}
		return Response.success("运营分析数据载入成功！", list);
	}
	/**
	 * 商城订单统计
     * 每日10点执行，刷新昨日订单统计
     * 针对 t_data_esporder_analysis 和  t_data_esporderdetail
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	public void flushEsporderAnalysis() {
		String txnId = DateFormatUtil.dateToString(new Date(), "yyyyMMdd");
		String day = DateFormatUtil.dateToString(new Date(), "yyyy-MM-dd");
		DataEsporderAnalysis entity = new DataEsporderAnalysis();
		entity.setTxnId(txnId);
		entity.setIsDelete("00");
		String beginDate = day + " 00:00:00";
		String endDate = day + " 23:59:59";
		List<OrderInfoEntity> orderlist = orderService.getSectionOrderList(beginDate, endDate);
		if(orderlist==null||orderlist.size()==0){
			entity.setIsDelete("00");
			createdEntity(entity);
		}else{
			entity = createdOrderEntity(entity, orderlist);
			createdEntity(entity);
			Long orderAnalysisId = entity.getId();
			createdOrderDetailEntity(orderAnalysisId,orderlist);
		}
	}
	/**
	 * 统计订单 t_data_esporder_analysis
	 * @param entity
	 * @param orderlist
	 * @return
	 */
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
		for(OrderInfoEntity order : orderlist){
			payfalg = true;
			Long userId = order.getUserId();
			if(!userIdStr.contains(userId.toString()+",")){
				userIdStr=userIdStr+userId.toString()+",";
				confirmNum ++;
			}
			confirmAmt = confirmAmt.add(order.getOrderAmt());
			if(order.getPayTime()!=null){//支付时间非空
				payfalg = false;
				if(!userIdStrByPayfalg.contains(userId.toString()+",")){
					userIdStrByPayfalg=userIdStrByPayfalg+userId.toString()+",";
					payNum ++;
				}
				payAmt = payAmt.add(order.getOrderAmt());
			}
			List<OrderDetailInfoEntity> orderDetaillist = orderDetailInfoService.queryOrderDetailInfo(order.getId());
			for(OrderDetailInfoEntity orderDetail : orderDetaillist){
				confirmGoodsNum = confirmGoodsNum + Integer.parseInt(orderDetail.getGoodsNum().toString());
				if(payfalg){
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
	}
	/**
	 * 统计订单详情 t_data_esporderdetail
	 * @param orderAnalysisId
	 * @param orderlist
	 */
	private void createdOrderDetailEntity(Long orderAnalysisId, List<OrderInfoEntity> orderlist) {
		Map<Long,List<OrderDetailInfoEntity>> map = new HashMap<Long,List<OrderDetailInfoEntity>>();
		for(OrderInfoEntity order : orderlist){//所有订单详情  根据商品分组。
			List<OrderDetailInfoEntity> orderDetaillist = orderDetailInfoService.queryOrderDetailInfo(order.getId());
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
		String orderDetailIdStr = "";
		String orderDetailIdStrByPayfalg = "";
		Integer confirmGoodsNum = 0;//下单商品件数
		BigDecimal confirmAmt = new BigDecimal(0);//下单总金额
		Integer payGoodsNum = 0;//支付商品件数
		BigDecimal payAmt = new BigDecimal(0);//支付总金额
		BigDecimal percentConv = new BigDecimal(0);//支付下单环比
		for(List<OrderDetailInfoEntity> value : map.values()){
			
		}
		DataEsporderdetail entity = new DataEsporderdetail();
		entity.setCreatedTime(new Date());
		entity.setUpdatedTime(new Date());
		entity.setIsDelete("00");
		entity.setOrderAnalysisId(orderAnalysisId);
	}
}