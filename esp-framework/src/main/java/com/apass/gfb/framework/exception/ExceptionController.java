package com.apass.gfb.framework.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 全局错误页面
 * @author lixining
 * @version $Id: ListeningErrorController.java, v 0.1 2015年4月9日 上午9:49:34 lixining Exp $
 */
@Controller
public class ExceptionController extends AbstractException {

    @RequestMapping(PAGE_403)
    public String mapping403() {
        return getMapping(PAGE_403);
    }

    @RequestMapping(PAGE_404)
    public String mapping404() {
        return getMapping(PAGE_404);
    }

    @RequestMapping(PAGE_500)
    public String mapping500() {
        return getMapping(PAGE_500);
    }
}
