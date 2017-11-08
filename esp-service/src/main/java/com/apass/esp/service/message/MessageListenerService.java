package com.apass.esp.service.message;

import com.apass.esp.domain.entity.MessageListener;
import com.apass.esp.mapper.MessageListenerMapper;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageListenerService {
	@Autowired
	private MessageListenerMapper messageListenerMapper;
	/**
	 * 获取反馈信息列表
	 */
	public ResponsePageBody<MessageListener> getmessageListenerPageList(MessageListener query) throws BusinessException{
		ResponsePageBody<MessageListener> pageBody = new ResponsePageBody<MessageListener>();
		List<MessageListener> backList = messageListenerMapper.getMessageListenerListPage(query);
		if(CollectionUtils.isEmpty(backList)){
			pageBody.setTotal(0);
		}else{
			Integer count =messageListenerMapper.getMessageListenerListPageCount(query);
			pageBody.setTotal(count);
		}
		for(MessageListener m:backList){
			m.setCreateDateString(DateFormatUtil.datetime2String(m.getCreatedTime()));
		}
		pageBody.setRows(backList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}
}
