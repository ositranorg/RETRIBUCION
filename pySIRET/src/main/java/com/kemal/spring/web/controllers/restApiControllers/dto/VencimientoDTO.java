package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kemal.spring.domain.Concesionario;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor

public class VencimientoDTO {

@JsonProperty("sMesPeriodo")
private String sMesPeriodo;
@JsonProperty("sAnioPeriodo")
private String sAnioPeriodo;
@JsonProperty("dFechaVenc")
private String dFechaVenc;
@JsonProperty("concesionario")
private Integer concesionario;
public VencimientoDTO(String sMesPeriodo,String sAnioPeriodo,String dFechaVenc) 
{
	this.sMesPeriodo=sMesPeriodo;
	this.sAnioPeriodo=sAnioPeriodo;
	this.dFechaVenc=dFechaVenc;
}
}
