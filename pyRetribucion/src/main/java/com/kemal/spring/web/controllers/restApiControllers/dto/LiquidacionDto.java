package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquidacionDto {
	@JsonProperty("idPeriodo")
	private Integer nPeriodo;
	@JsonProperty("idTipoDocumento")
	private Integer nIdTipoDocumento;
	@JsonProperty("anio")
	private Integer nAnio;
	@JsonProperty("documento")
	private String sDocumento;
	@JsonProperty("estado")
	private String sEstado;
	
	@JsonProperty("fechaRegistro")
	private Date dfecRegistro;
	
	@JsonProperty("fechaModificacion")
	private Date dfecModifica;
	
	public String getsDocumento() {
		return sDocumento;
	}
	public void setsDocumento(String sDocumento) {
		this.sDocumento = sDocumento;
	}
	public String getsEstado() {
		return sEstado;
	}
	public void setsEstado(String sEstado) {
		this.sEstado = sEstado;
	}
	public Integer getnPeriodo() {
		return nPeriodo;
	}
	public void setnPeriodo(Integer nPeriodo) {
		this.nPeriodo = nPeriodo;
	}
	public Integer getnIdTipoDocumento() {
		return nIdTipoDocumento;
	}
	public void setnIdTipoDocumento(Integer nIdTipoDocumento) {
		this.nIdTipoDocumento = nIdTipoDocumento;
	}
	public Integer getnAnio() {
		return nAnio;
	}
	public void setnAnio(Integer nAnio) {
		this.nAnio = nAnio;
	}
	public Date getDfecRegistro() {
		return dfecRegistro;
	}
	public void setDfecRegistro(Date dfecRegistro) {
		this.dfecRegistro = dfecRegistro;
	}
	public Date getDfecModifica() {
		return dfecModifica;
	}
	public void setDfecModifica(Date dfecModifica) {
		this.dfecModifica = dfecModifica;
	}
}
