package com.kemal.spring.bd.view;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "VW_CREDITOREGISTRADO")
public class VWCreditoRegistrado {
	@Id
	@Column(name = "NCODIGO")
	private Integer ncodigo;
	@Column(name = "SPERIODICIDAD_ORIGEN")
	private String periodicidadOrigen;
	@Column(name = "SRETRIBUCION_ORIGEN")
	private String retribucionOrigen;
	@Column(name = "SANIOPERIODO_ORIGEN")
	private String sAnioPeriodoiOrigen;
	@Column(name = "SMESPERIODO_ORIGEN")
	private String sMesPeriodoOrigen;
	@Column(name = "SPERIODICIDAD_DESTINO")
	private String periodicidadDestino;
	@Column(name = "SRETRIBUCION_DESTINO")
	private String retribucionDestino;
	@Column(name = "SANIOPERIODO_DESTINO")
	private String sAnioPeriodoDestino;
	@Column(name = "SMESPERIODO_DESTINO")
	private String sMesPeriodoDestino;
	@Column(name = "NIMPORTE")
	private BigDecimal nimporte;
	@Column(name = "NCODIGOCN")
	private Integer nCodigocn;
	@Column(name = "NCODIGO_APORTE")
	private Integer nCodigoAporte;
	
	
	@Column(name = "ESTADO")
	private String estado;
	
}
