package com.apass.gfb.framework.exception;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @description 定义错误页面
 * @author lixining
 * @version $Id: ListeningErrorPageInitializer.java, v 0.1 2015年4月8日 下午4:32:41 lixining Exp $
 */
@Configuration
public class ListeningExceptionMapping {

    /**
     * specific error pages
     * 
     * @return CustomExceptionContainer
     */
    @Bean
    public CustomExceptionContainer customExceptionContainer() {
        return new CustomExceptionContainer();
    }

    /**
     * @description 自定义多个错误页面
     * @author lixining
     * @version $Id: ListeningErrorPageInitializer.java, v 0.1 2015年4月9日 上午11:21:09 lixining Exp $
     */
    private static class CustomExceptionContainer implements EmbeddedServletContainerCustomizer {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            // 403 page
            ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN, AbstractException.PAGE_403);
            container.addErrorPages(error403);

            // 404 page
            ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, AbstractException.PAGE_404);
            container.addErrorPages(error404);

            // 500 page
            ErrorPage error500 = new ErrorPage(Throwable.class, AbstractException.PAGE_500);
            container.addErrorPages(error500);
        }
    }
}
