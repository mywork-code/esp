package com.apass.esp.mapper;
import java.util.List;
import com.apass.esp.domain.entity.GoodsBrand;
import com.apass.gfb.framework.mybatis.GenericMapper;
/**
 * @author Administrator
 *
 */
public interface GoodsBrandMapper extends GenericMapper<GoodsBrand,Long> {
    /**
     * 发票查询
     * @param entity
     * @return
     */
    public List<GoodsBrand> getGoodsBrandList(GoodsBrand entity);
}