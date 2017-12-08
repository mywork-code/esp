package com.apass.esp.mapper;
import java.util.List;

import com.apass.esp.domain.entity.LimitBuyAct;
import com.apass.esp.domain.entity.activity.LimitBuyActVo;
import com.apass.gfb.framework.mybatis.GenericMapper;
public interface LimitBuyActMapper extends GenericMapper<LimitBuyAct,Long> {
    /**
     * 反选
     * @param entity
     * @return
     */
    public List<LimitBuyAct> getLimitBuyActListDeSelect(LimitBuyAct entity);
    /**
     * 剧条件查询
     * @param entity
     * @return
     */
    public List<LimitBuyAct> getLimitBuyActList(LimitBuyAct entity);
    /**
     * 分页查询
     * @param entity
     * @return
     */
    public List<LimitBuyAct> getLimitBuyActPage(LimitBuyActVo entity);
    /**
     * 分页查询
     * @param entity
     * @return
     */
    public Integer getLimitBuyActPageCount(LimitBuyActVo entity);

}