package com.apass.esp.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.Category;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface CategoryMapper extends GenericMapper<Category, Long> {
    
    List<Category> pageEffectiveList(QueryParams query);
    
    List<Category> selectByParentKey(@Param("parentId") Long parentId);
    
    List<Category> selectByCategoryName(@Param("categoryName") String categoryName);
    
    List<Category> selectCategoryList(Long levelId);
    
    Integer getMaxSortOrder(@Param("level")Long level);
    
    void updateStatus1To0();
    
}

