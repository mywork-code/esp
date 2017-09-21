package com.apass.esp.web.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apass.esp.domain.entity.MessageListener;
import com.apass.esp.service.message.MessageListenerService;
import com.apass.esp.utils.ResponsePageBody;

/**
 *京东推送消息监听后台显示
 */
@Controller
@RequestMapping("/application/message/listener")
public class MessageListenerController {
	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MessageListenerController.class);
	@Autowired
	private MessageListenerService messageListenerService;
	  /**
	    * 京东推送消息监听配置页
	    * @return
	    */
		@RequestMapping(value = "/index", method = RequestMethod.GET)
	    public String introduceConfig() {
	      return "message/messageListener";
	    }
	/**
	 * 分页查询
	 */
	@ResponseBody
	@RequestMapping("/pagelist")
	public ResponsePageBody<MessageListener> messageListenerPageList(MessageListener messageListener) {
		ResponsePageBody<MessageListener> respBody = new ResponsePageBody<MessageListener>();
		try {
			// 获取分页结果返回给页面
			respBody=messageListenerService.getmessageListenerPageList(messageListener);
		} catch (Exception e) {
			LOG.error("京东推送消息监听查询失败", e);
			respBody.setMsg("京东推送消息监听查询失败");
		}
		return respBody;
	}
}
