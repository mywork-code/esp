package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.AwardActivityInfo;
import com.apass.esp.domain.entity.categroy.Category;
import com.apass.gfb.framework.mybatis.GenericMapper;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;

public interface CategoryMapper extends GenericMapper<Category, Long> {
	/**
	 * 商品分类信息分页查询
	 * @param map
	 * @param page
	 * @return
	 */
    public Pagination<Category> queryCategoryInforPage(Category infoEntity, Page page);
    
    List<Category> pageEffectiveList(QueryParams query);
    
}