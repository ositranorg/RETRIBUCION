package com.kemal.spring.web.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
@PropertySource("classpath:config.properties")
public class Config {
	@Value("${buencontribuyente}")
	private String buencontribuyente;
	@Value("${liberacion}")
	private String liberacion;
	@Value("${otrosDescuentos}")
	private String otrosDescuentos;
	@Value("${deducciones}")
	private String deducciones;
	@Value("${pago}")
	private String pago;
}
