package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoDeclaracionDto {
	@JsonProperty("idConcesionario")
	private Integer idConcesionario;
	@JsonProperty("mes")
	private String mes;
	@JsonProperty("anio")
	private String anio;
	
	
}
