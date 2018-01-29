package com.apass.esp.mapper;
import java.util.List;
import java.util.Map;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.esp.domain.vo.DataAnalysisVo;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface DataAppuserRetentionMapper extends GenericMapper<DataAppuserRetention,Long> {
	/**
	 * 用户留存数据载入  查询时间区间
	 * 参数含有
	 * @param map
	 * @param dateStart  起止日期
	 * @param dateEnd  起止日期
	 * @param platformids  平台类型
	 * @return
	 */
	public List<DataAppuserRetention> getAppuserRetentionList(Map<String, Object> map);
	
	DataAppuserRetention getDataAnalysisByTxnId(DataAnalysisVo analysis);
}
