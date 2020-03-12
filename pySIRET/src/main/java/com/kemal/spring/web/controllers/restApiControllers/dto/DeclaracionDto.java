package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.math.BigDecimal;
import java.util.List;

import com.kemal.spring.domain.BaseCalculo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeclaracionDto {
	private Integer idConcesionario;
	
	private String cbocodTipoDeclaracion;
	private String tipoPeriodicidad;
	private String tipoRetribucion;
	private String tipoPeriodicidadDes;
	private String tipoRetribucionDes;
	private String mesRetribucion;
	private String anioRetribucion;
	private String fdesde;
	private String fhasta;
	private BigDecimal porcentaje;
	private String monedaRetribucion;
	private String monedaSimbolo;
	private String monedaRetribucionDes;
	private String buenContribuyente;	
	
	private String cboDetalleBC;
	private String biconcepto;
	private BigDecimal biimporte;
	private List<BaseCalculo> lstBaseCalculo;
	
	private BigDecimal aportePorcentaje; 	
	private BigDecimal baseCalculo;
	private BigDecimal deduccion;
	private BigDecimal pagosprevios;
	private BigDecimal creditos;
	private BigDecimal liberacionpago;
	private BigDecimal otrosDescuentos;
	private BigDecimal intereses;
	
	private BigDecimal retribucionpendienteapagar;
	private BigDecimal retribucionapagar;
	private BigDecimal retribucionresultante;
	
	
}
