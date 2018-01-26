package com.apass.esp.mapper;
import java.util.List;
import java.util.Map;
import com.apass.esp.domain.entity.DataEsporderdetail;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * Created by DELL on 2018/1/25.
 */
public interface DataEsporderdetailMapper extends GenericMapper<DataEsporderdetail,Long> {
	/**
	 * 根据order_analysis_id 外键 查询
	 * @param map
	 * 参数含有
	 * @param order_analysis_id
	 * @return
	 */
	public List<DataEsporderdetail> getDataEsporderdetailList(Map<String, Object> map);
}