package com.apass.esp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.apass.gfb.framework.BootApplication;

@Configuration
@ImportResource(locations = {"classpath*:spring/data-source.xml"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
