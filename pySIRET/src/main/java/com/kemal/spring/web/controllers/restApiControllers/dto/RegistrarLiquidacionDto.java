package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrarLiquidacionDto {
	private Integer estado = 1;
	private List<LiquidacionDto> liquidaciones;
}
