package com.apass.esp.web.commons;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apass.esp.service.fileview.FileViewService;

/**
 * 图片查看工具
 * 
 * @author tanww
 *
 */
@Controller
@RequestMapping("/fileView")
public class FileViewController {

    private static final Logger logger = LoggerFactory.getLogger(FileViewController.class);

    @Autowired
    private FileViewService     fileViewService;

    /**
     * 查看图片
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void random(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream output = null;
        try {
            String picUrl = request.getParameter("picUrl"); //图片url
            if (StringUtils.isBlank(picUrl)) {
                logger.error("查看图片参数有误.picUrl[{}]", picUrl);
                return;
            }

            logger.info("查看图片参数.picUrl[{}]", picUrl);

            byte[] image = fileViewService.readFileToByteArray(picUrl);

            output = response.getOutputStream();
            output.write(image);
        } catch (Exception e) {
            logger.error("查看图片异常.", e);
        } finally {
            IOUtils.closeQuietly(output);
        }
        return;
    }

}
