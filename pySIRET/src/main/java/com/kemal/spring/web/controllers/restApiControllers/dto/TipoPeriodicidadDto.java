package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoPeriodicidadDto {
	@JsonProperty("codigoTipoPeridicidadDTO")
	private String codigoTipoPeridicidadDTO;
	@JsonProperty("sDescripcion")
	private String sDescripcion;
}
