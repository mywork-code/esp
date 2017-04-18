package com.apass.esp.web.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.utils.FileUtilsCommons;
import com.apass.gfb.framework.utils.HttpWebUtils;
import com.baidu.ueditor.ActionEnter;

@Controller
@RequestMapping("/webapp/ueditor")
class UEController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UEController.class);
	@Value("${nfs.rootPath}")
	private String rootPath;
	@Value("${server.ip}")
	private  String serverIp;

	@RequestMapping("/init")
	public ModelAndView upeditor(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html");
			String rootPath2 = UEController.class.getResource("/baidu").getPath();
			response.getWriter().write(new ActionEnter(request, rootPath2).exec(serverIp));
		} catch (Exception e) {
			LOGGER.error("初始化失败。。", e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/loadPoto")
	public String loadPic(HttpServletRequest request, HttpServletResponse response) throws Exception {

		byte[] xlsbyte = FileUtilsCommons.loadPicByte(rootPath, HttpWebUtils.getValue(request, "picUrl"));

		response.getOutputStream().write(xlsbyte);

		return null;
	}

}
