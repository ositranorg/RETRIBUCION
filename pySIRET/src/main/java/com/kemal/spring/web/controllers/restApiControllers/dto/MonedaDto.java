package com.kemal.spring.web.controllers.restApiControllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@CustomJsonRootName(plural = "lstMoneda", singular = "moneda")
public class MonedaDto {
	private Integer id;
	private String sDescripcion;
}
