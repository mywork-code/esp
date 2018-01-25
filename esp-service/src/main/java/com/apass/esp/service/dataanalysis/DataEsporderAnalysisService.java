package com.apass.esp.service.dataanalysis;
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
import com.apass.esp.domain.vo.DataAppuserAnalysisVo;
import com.apass.esp.domain.vo.DataEsporderAnalysisVo;
import com.apass.esp.mapper.DataEsporderAnalysisMapper;
import com.apass.gfb.framework.utils.DateFormatUtil;
@Service
public class DataEsporderAnalysisService {
	@Autowired
	private DataEsporderAnalysisMapper dataEsporderAnalysisMapper;
	@Autowired
	private DataEsporderdetailService dataEsporderdetailService;
	@Autowired
	private DataAppuserAnalysisService dataAppuserAnalysisService;
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
			DataAppuserAnalysis dataAppuserAnalysis = dataAppuserAnalysisService.getDataAnalysisByTxnId(new DataAppuserAnalysisVo(entity.getTxnId(), null, "2"));
			BeanUtils.copyProperties(dataAppuserAnalysis, entity);
			entity.setId(orderAnalysisId);
			entity.setList(orderlist);
		}
		return Response.success("运营分析数据载入成功！", list);
	}
}