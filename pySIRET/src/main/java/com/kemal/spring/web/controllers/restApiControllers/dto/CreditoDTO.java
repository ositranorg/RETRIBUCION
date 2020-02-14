package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditoDTO {
	@JsonProperty("idCredito")
	private Integer idCredito;
	@JsonProperty("ncodigoAporte")
	private Integer ncodigoAporte;
	
	@JsonProperty("nCodigoCn")
	private Integer nCodigoCn;
	
	
	@JsonProperty("tipoPeriodicidadOrigen")
	private Integer tipoPeriodicidadOrigen;
	
	@JsonProperty("tipoRetribucionOrigen")
	private Integer tipoRetribucionOrigen;
	
	
	
	@JsonProperty("anioRetribucionOrigen")
	private String anioRetribucionOrigen;
	@JsonProperty("mesRetribucionOrigen")
	private String mesRetribucionOrigen;
	
	
	@JsonProperty("tipoPeriodicidadDestino")
	private Integer tipoPeriodicidadDestino;
	@JsonProperty("tipoRetribucionDestino")
	private Integer tipoRetribucionDestino;
	@JsonProperty("mesRetribucionDestino")
	private String mesRetribucionDestino;
	@JsonProperty("anioRetribucionDestino")
	private String anioRetribucionDestino;		
	
	
	@JsonProperty("tipoRegistroCredito")
	private Integer tipoRegistroCredito;
	
	@JsonProperty("nimporte")
	private BigDecimal nimporte;
	
	
	
	
	
	
	
}
