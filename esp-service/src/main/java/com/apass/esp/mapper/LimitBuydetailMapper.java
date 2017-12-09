package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.LimitBuydetail;
import com.apass.esp.domain.vo.LimitBuyParam;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitBuydetailMapper extends GenericMapper<LimitBuydetail,Long> {
	/**
	 * 根据限时购的活动Id和用户Id和skuId，查询对应用户的记录
	 * @param param
	 * @return
	 */
	public List<LimitBuydetail> getUserBuyGoodsNum(LimitBuyParam param);
}
