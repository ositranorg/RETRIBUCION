package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoRetribucionDto {
	@JsonProperty("codigoTipoRetribucionDTO")
	private String codigoTipoRetribucionDTO;
	@JsonProperty("sDescripcion")
	private String sDescripcion;
}
