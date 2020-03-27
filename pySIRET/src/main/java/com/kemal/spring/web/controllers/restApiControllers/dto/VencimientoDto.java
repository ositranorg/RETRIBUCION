package com.kemal.spring.web.controllers.restApiControllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@CustomJsonRootName(plural = "lstVencimiento", singular = "vencimiento")
public class VencimientoDto {

@JsonProperty("sMesPeriodo")
private String sMesPeriodo;
@JsonProperty("sAnioPeriodo")
private String sAnioPeriodo;
@JsonProperty("dFechaVencPago")
private String dFechaVencPago;
@JsonProperty("dFechaVencPres")
private String dFechaVencPres;

@JsonProperty("concesionario")
private Integer concesionario;

@JsonProperty("itipoRetribuciondto")
private Integer itipoRetribuciondto;
@JsonProperty("itipoPeriodicidaddto")
private Integer itipoPeriodicidaddto;


@JsonProperty("tipoRetribuciondto")
private String tipoRetribuciondto;
@JsonProperty("tipoPeriodicidaddto")
private String tipoPeriodicidaddto;

@JsonProperty("oderbytipoPeriodicidaddto")
private Integer oderbytipoPeriodicidaddto;

public VencimientoDto(Integer oderbytipoPeriodicidaddto,String tipoPeriodicidaddto,String tipoRetribuciondto,String sMesPeriodo,String sAnioPeriodo,String dFechaVencPago,String dFechaVencPres) 
{	
	this.oderbytipoPeriodicidaddto=oderbytipoPeriodicidaddto;
	this.tipoPeriodicidaddto=tipoPeriodicidaddto;
	this.tipoRetribuciondto=tipoRetribuciondto;
	this.sMesPeriodo=sMesPeriodo;
	this.sAnioPeriodo=sAnioPeriodo;
	this.dFechaVencPago=dFechaVencPago;
	this.dFechaVencPres=dFechaVencPres;
}
}
