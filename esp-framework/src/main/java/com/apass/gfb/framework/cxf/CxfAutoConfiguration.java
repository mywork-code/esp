package com.apass.gfb.framework.cxf;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath*:META-INF/cxf/cxf.xml",
		"classpath*:META-INF/cxf/cxf-servlet.xml",
		"classpath*:META-INF/cxf/cxf-extension-*.xml",
		"classpath*:spring/webservice/webservice-*.xml" })
public class CxfAutoConfiguration {

	@Bean
	public ServletRegistrationBean getCxfServletRegistrationBean() {
		ServletRegistrationBean cxfServletRegistrationBean = new ServletRegistrationBean(
				new CXFServlet(), "/data/ws/*");
		return cxfServletRegistrationBean;
	}

}
