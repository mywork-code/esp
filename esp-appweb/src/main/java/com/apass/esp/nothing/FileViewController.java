package com.apass.esp.nothing;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.enums.LogStashKey;
import com.apass.esp.service.fileview.FileViewService;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.EncodeUtils;


/**
 * 图片查看工具
 * 
 * @author tanww
 *
 */
@Controller
@RequestMapping("/fileView")
public class FileViewController {

	@Autowired
	private FileViewService fileViewService;

	/**
	 * 查看图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView random(HttpServletRequest request, HttpServletResponse response) {
	    
	    String logStashSign = LogStashKey.FILEVIEW.getValue();
        String methodDesc = LogStashKey.FILEVIEW.getName();
        
        String picUrl = request.getParameter("picUrl"); // 客户号
        if (StringUtils.isBlank(picUrl)) {
            LOG.info("查看图片参数有误");
            return null;
        }
        
        String picUrlDecode = EncodeUtils.base64Decode(picUrl);

        String requestId = logStashSign;
        LOG.info(requestId, methodDesc, picUrlDecode);
        
		ServletOutputStream output = null;
		try {
			
			byte[] image = fileViewService.readFileToByteArray(picUrlDecode);
			
			output = response.getOutputStream();
			output.write(image);
		} catch (Exception e) {
		    LOG.logstashException(requestId, methodDesc, e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(output);
		}
		return null;
	}

}
