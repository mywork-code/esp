package com.apass.esp.jdserve;

import com.apass.gfb.framework.BootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
