package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kemal.spring.domain.AportePorcentaje;
import com.kemal.spring.domain.CondicionBC;
import com.kemal.spring.domain.Moneda;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MostrarDJDto {
	 @JsonProperty("lstTipoPeriodicidad")
	private List<TipoPeriodicidad> lstTipoPeriodicidad;
	 @JsonProperty("lstAportePorcentaje")
	private List<AportePorcentaje> lstAportePorcentaje ;
	 @JsonProperty("lstCal")
	private List<TipoPeriodicidad> lstCal ;
	 @JsonProperty("lstAnios")
	private List<String> lstAnios;
	 @JsonProperty("lstTipoRetribucion")
	private List<TipoRetribucion> lstTipoRetribucion;
	 @JsonProperty("lstMonedaRetribucion")
	private List<Moneda> lstMonedaRetribucion;
	 @JsonProperty("condicionBC")
	private CondicionBC condicionBC;
	
}
