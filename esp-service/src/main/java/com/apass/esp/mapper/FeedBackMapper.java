package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.common.model.QueryParams;
import com.apass.esp.domain.entity.FeedBack;
import com.apass.esp.domain.query.FeedBackQuery;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface FeedBackMapper extends GenericMapper<FeedBack,Long>{
	
	List<FeedBack> pageEffectiveList(QueryParams query);
	
	Integer count();
	
    List<FeedBack> getFeedBackListPage(FeedBackQuery feedBack);
	
	Integer getFeedBackListPageCount(FeedBackQuery feedBack);
}
