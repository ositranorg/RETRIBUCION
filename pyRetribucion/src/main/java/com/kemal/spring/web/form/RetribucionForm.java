/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.web.form;

import java.math.BigDecimal;
import java.util.List;

import com.kemal.spring.domain.BaseCalculo;
import com.kemal.spring.domain.Credito;
import com.kemal.spring.domain.LiberacionPago;
import com.kemal.spring.domain.Descuento;
import com.kemal.spring.domain.TipoPeriodicidad;
import com.kemal.spring.domain.TipoRetribucion;

/**
 *
 * @author consultor_jti07
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RetribucionForm {
	private String tipoPeriodicidad;
	private String tipoRetribucion;
	
	private String tipoPeriodicidadDes;
	private String tipoRetribucionDes;
	
	
	private String mesRetribucion;
	private String anioRetribucion;
	private Integer codaportebihdd;
	private Integer codaportehdd;
	private Integer codigobihdd;
	private Integer tipoconceptobihdd;
	private String cboDetalleBC;
	private String biconcepto;
	private BigDecimal biimporte;
	private List<BaseCalculo> lstBaseCalculo;
	private String monedaRetribucion;
	private String monedaRetribucionDes;
	private String monedaSimbolo;
	private String buenContribuyente;
	private BigDecimal aportePorcentaje; 
	private String accion;	


	private String fdesde;
	private String fhasta;
	private BigDecimal porcentaje;
	
	
	
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
	private String cbocodTipoDeclaracion;
	
	private Integer iddeduccionhdd;
	private Integer idliberacionhdd;
	private Integer iddescuentohdd;
	private BigDecimal porcentajehdd;
}
