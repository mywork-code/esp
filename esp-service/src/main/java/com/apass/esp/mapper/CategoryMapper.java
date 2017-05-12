package com.apass.esp.mapper;


import java.util.List;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.Category;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface CategoryMapper extends GenericMapper<Category, Long> {
    
    List<Category> pageEffectiveList(QueryParams query);
    
}

