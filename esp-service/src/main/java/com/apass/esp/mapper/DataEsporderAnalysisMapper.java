package com.apass.esp.mapper;
import java.util.List;
import java.util.Map;
import com.apass.esp.domain.entity.DataEsporderAnalysis;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by DELL on 2018/1/25.
 */
public interface DataEsporderAnalysisMapper extends GenericMapper<DataEsporderAnalysis,Long> {
	/**
	 * 运营分析数据载入
	 * 参数含有
	 * @param dateStart
	 * @param dateEnd
	 * @param platformids
	 * @param map
	 * @return
	 */
	public List<DataEsporderAnalysis> getOperationAnalysisList(Map<String, Object> map);
}