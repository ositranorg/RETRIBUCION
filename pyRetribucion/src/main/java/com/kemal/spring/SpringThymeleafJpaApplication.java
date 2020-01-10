package com.kemal.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@SpringBootApplication
@SpringBootApplication(scanBasePackages = {"com.kemal.*"}) 

//@ComponentScan(basePackages = { "com.kemal","com.kemal.spring.web.controllers.restApiControllers"} )
public class SpringThymeleafJpaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringThymeleafJpaApplication.class, args);
	}


}
