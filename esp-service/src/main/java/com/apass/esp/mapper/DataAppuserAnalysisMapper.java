package com.apass.esp.mapper;
import java.util.List;
import java.util.Map;
import com.apass.esp.domain.entity.DataAppuserAnalysis;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface DataAppuserAnalysisMapper extends GenericMapper<DataAppuserAnalysis,Long>{
	/**
	 * 根据txnId获取某一个时间的数据
	 * @param analysis
	 * @return
	 */
	public DataAppuserAnalysis getDataAnalysisByTxnId(DataAnalysisVo analysis);
	
	/**
	 * 运营分析数据载入  查询时间区间
	 * 参数含有
	 * @param map
	 * @param dateStart  起止日期
	 * @param dateEnd  起止日期
	 * @param platformids  平台类型
	 * @return
	 */
	public List<DataAppuserAnalysis> getAppuserAnalysisList(Map<String, Object> map);
}