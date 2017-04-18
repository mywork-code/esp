package com.apass.gfb.framework.security.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.gfb.framework.utils.HttpWebUtils;
import com.apass.gfb.framework.utils.ImageUtils;
import com.apass.gfb.framework.utils.RandomUtils;


/**
 * 
 * @description 验证码程序
 * 
 * @author Listening
 * @version $Id: RandomController.java, v 0.1 2014年11月4日 下午9:14:51 Listening Exp $
 */
@Controller
@RequestMapping("/listeningboot")
public class RandomController {
    private static final Logger logger = LoggerFactory.getLogger(RandomController.class);

    /**
     * 验证验证码
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/random/validate", method = RequestMethod.GET)
    public Map<String, String> validate(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("valid", "false");
        Object sessionObj = HttpWebUtils.getSession(request).getAttribute("random");
        String requestCode = request.getParameter("random");
        requestCode = StringUtils.isNoneBlank(requestCode) ? requestCode.trim() : "";
        String sessionCode = sessionObj != null ? sessionObj.toString() : null;
        if (StringUtils.isBlank(requestCode) || StringUtils.isBlank(sessionCode)) {
            return result;
        }
        result.put("valid", String.valueOf(sessionCode.equalsIgnoreCase(requestCode)));
        return result;
    }

    /**
     * 生成验证码
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ModelAndView random(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream output = null;
        try {
            HttpWebUtils.setViewHeader(response, MediaType.IMAGE_JPEG_VALUE);
            String random = RandomUtils.getRandom(4);
            HttpWebUtils.getSession(request).setAttribute("random", random);
            byte[] image = ImageUtils.getRandomImgage(random);
            output = response.getOutputStream();
            output.write(image);
        } catch (IOException e) {
            logger.error("write random image to response fail", e);
        } finally {
            IOUtils.closeQuietly(output);
        }
        return null;
    }

}
