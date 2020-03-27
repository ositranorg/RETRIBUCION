package com.kemal.spring.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kemal.spring.filter.Interceptor;

@Configuration
@ComponentScan(basePackages = "com.kemal.*")
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Interceptor()).addPathPatterns("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**", "/img/**", "/css/**", "/js/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/", "classpath:/static/img/",
						"classpath:/static/css/", "classpath:/static/js/")
				.addResourceLocations("/resources/static/**");
	}

	 @Bean
	public Jackson2ObjectMapperBuilderCustomizer customJson()
	{
	        return builder -> {
	 
	            // human readable
	            builder.indentOutput(true);
	 
	            // exclude null values
	            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
	 
	            // all lowercase with under score between words
	            builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	        };
	 }
}
