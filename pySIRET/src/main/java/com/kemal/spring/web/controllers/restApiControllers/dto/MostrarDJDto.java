package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MostrarDJDto {
	@JsonProperty("codigoTipoRetribucionDTO")
	private String codigoTipoRetribucionDTO;
	@JsonProperty("sDescripcion")
	private String sDescripcion;
}
