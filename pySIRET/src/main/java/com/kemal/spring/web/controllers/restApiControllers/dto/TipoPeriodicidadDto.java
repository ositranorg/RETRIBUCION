package com.kemal.spring.web.controllers.restApiControllers.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@CustomJsonRootName(plural = "lsttipoPeriodicidad", singular = "tipoPeriodicidad")
public class TipoPeriodicidadDto {
	private Integer id;
	private String sDescripcion;
}
