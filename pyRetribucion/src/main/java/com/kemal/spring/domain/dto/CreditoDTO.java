package com.kemal.spring.domain.dto;

import java.math.BigDecimal;

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

	@JsonProperty("NCODIGO")
	private Long nCodigo;
	
	@JsonProperty("NCODIGOCN")
	private Integer nCodigocn;
	
	
	@JsonProperty("S_TIPO_PERIODICIDAD")
	private Integer sTipoPeriodicidad;
	
	@JsonProperty("S_TIPO_RETRIBUCION")
	private Integer sTipoRetribucion;
	
	
	
	@JsonProperty("S_ANIO_PERIODO")
	private String sAnio;
	@JsonProperty("S_MES_PERIODO")
	private String sMes;
	@JsonProperty("NRET_RESULTANTE")
	private BigDecimal nretResultante;
	
}
