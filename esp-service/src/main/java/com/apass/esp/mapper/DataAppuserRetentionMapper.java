package com.apass.esp.mapper;
import java.util.List;
import java.util.Map;
import com.apass.esp.domain.entity.DataAppuserRetention;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface DataAppuserRetentionMapper extends GenericMapper<DataAppuserRetention,Long> {
	/**
	 * 用户留存数据载入  查询时间区间
	 * @param map
	 * 参数含有
	 * dateStart
	 * dateEnd
	 * platformids
	 * @return
	 */
	public List<DataAppuserRetention> getAppuserRetentionList(Map<String, Object> map);
}
