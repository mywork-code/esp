package com.apass.esp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

import com.apass.gfb.framework.BootApplication;

@Configuration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
