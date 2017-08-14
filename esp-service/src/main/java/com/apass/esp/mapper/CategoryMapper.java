package com.apass.esp.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.Category;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface CategoryMapper extends GenericMapper<Category, Long> {
    
    List<Category> pageEffectiveList(QueryParams query);
    
    List<Category> selectByParentKey(@Param("parentId") Long parentId);
    
    List<Category> selectByCategoryName(@Param("categoryName") String categoryName,@Param("level") long level);
    //查询客户端首页的前3个类目信息
    List<Category> selectCategoryList(Long levelId);
    //查询客户端首页的1级类目信息
    List<Category> selectCategoryListJd(Long levelId);
     
    Integer getMaxSortOrder(@Param("level")Long level);
    
    void updateStatus1To0();
    
    List<Category> goodsCategoryList(Long levelId);
    List<Category> goodsCategoryListById(Long parentId);

    List<Category> selectByParentId(Long parentId);
}

