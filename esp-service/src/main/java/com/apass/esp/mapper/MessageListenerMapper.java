package com.apass.esp.mapper;

import java.util.List;

import com.apass.esp.domain.entity.MessageListener;
import com.apass.gfb.framework.mybatis.GenericMapper;

public interface MessageListenerMapper extends GenericMapper<MessageListener, Long>{
	
    List<MessageListener> getMessageListenerListPage(MessageListener messageListener);
	
	Integer getMessageListenerListPageCount(MessageListener messageListener);
}
