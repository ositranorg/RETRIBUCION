package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("mostrarDto")
public class MostrarDJDto {
	private List<TipoPeriodicidadDto> lstTipoPeriodicidad;
	private List<AportePorcentajeDto> lstAportePorcentaje ;
	private List<AnioDto> lstAnios;
	private List<TipoRetribucionDto> lstTipoRetribucion;
	private List<MonedaDto> lstMonedaRetribucion;
	private CondicionBCDTO condicionBC;
	private int anioActual;
	
}
