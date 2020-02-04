package com.kemal.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.kemal.*"}) 
public class SpringThymeleafJpaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringThymeleafJpaApplication.class, args);
	}


}
