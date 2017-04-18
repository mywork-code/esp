package com.apass.gfb.framework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * 
 * <pre>
 * 
 * &#64;description Config Server Start Application
 * 
 * 
 * 
 * 注意：
 * 
 *   spring boot 不可添加@EnableWebMvc其实官方解释没有提及一点，就是不能使用@EnableWebMvc，
 * 
 *   当然如果Spring Boot在classpath里看到有 spring webmvc 也会自动添加@EnableWebMvc 
 * 
 *   (http://spring.io/guides/gs/rest-service/) 如果@EnableWebMvc了，那么就会
 * 
 *   自动覆盖了官方给出的/static, /public, META-INF/resources, /resources等存放静态
 * 
 *   资源的目录。而将静态资源定位于src/main/webapp。当需要重新定义好资源所在目录时，则需要主动添加上述的那
 * 
 *   个配置类，来Override addResourceHandlers方法。
 * 
 * </pre>
 * 
 * 
 * 
 * @author listening
 * 
 * @version $Id: BootApplication.java, v 0.1 2015年5月4日 下午3:59:32 listening Exp $
 */
@SpringBootApplication(scanBasePackages = { BootApplication.SCANN_PACKAGE })
public class BootApplication extends SpringBootServletInitializer {
	public static final String SCANN_PACKAGE = "com.apass";

	/**
	 * 
	 * 
	 * 
	 * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(BootApplication.class);
	}

}