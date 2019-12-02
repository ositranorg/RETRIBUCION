package com.kemal.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"com.kemal.*"}) 
@ComponentScan(basePackages = { "com.kemal","com.kemal.spring.web.controllers.restApiControllers"} )
public class SpringThymeleafJpaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringThymeleafJpaApplication.class, args);
	}


}
