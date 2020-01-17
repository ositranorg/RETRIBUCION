package com.kemal.spring.bd.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "VW_NUEVOCREDITO")
public class VWNuevoCredito {
	@Id
	@Column(name="NCODIGO")
	private Long nCodigo;
	
	@Column(name="NCODIGOCN")
	private Integer nCodigocn;
	@Column(name = "S_TIPO_PERIODICIDAD")
	private String sTipoPeriodicidad;
	@Column(name = "S_TIPO_RETRIBUCION")
	private String sTipoRetribucion;
	@Column(name = "S_ANIO_PERIODO")
	private String sAnio;
	@Column(name = "S_MES_PERIODO")
	private String sMes;
	@Column(name = "NRET_RESULTANTE")
	private BigDecimal nretResultante;
	
	
	
}
