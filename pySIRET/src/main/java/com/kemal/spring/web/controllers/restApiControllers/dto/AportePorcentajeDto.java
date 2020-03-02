package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("aportePorcentajeDto")
public class AportePorcentajeDto {
	
private Integer id;
	
private BigDecimal porcentaje;


}
