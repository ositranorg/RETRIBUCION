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
	@Column(name = "PERIODICIDADORIGEN")
	private Integer periodicidadOrigen;
	@Column(name = "RETRIBUCIONORIGEN")
	private Integer retribucionOrigen;
	@Column(name = "SANIOPERIODO_ORIGEN")
	private Integer sAnioPeriodoiOrigen;
	@Column(name = "SMESPERIODO_ORIGEN")
	private Integer sMesPeriodoOrigen;
	@Column(name = "PERIODICIDADDESTINO")
	private Integer periodicidadDestino;
	@Column(name = "RETRIBUCIONDESTINO")
	private Integer retribucionDestino;
	@Column(name = "SANIOPERIODO_DESTINO")
	private String sAnioPeriodoDestino;
	@Column(name = "SMESPERIODO_DESTINO")
	private String sMesPeriodoDestino;
	@Temporal(TemporalType.DATE)
	@Column(name = "DFECHAAPLICADO")
	private Date dFechaAplicado;
	@Temporal(TemporalType.DATE)
	@Column(name = "DFECHAMODIFICA")
	private Date dFechaModifica;
	@Temporal(TemporalType.DATE)
	@Column(name = "DFECHAREGISTRO")
	private Date dFechaRegistro;
	@Column(name = "NIMPORTE")
	private BigDecimal nImporte;
	@Column(name = "NCODIGOCN")
	private Integer nCodigocn;
	@Column(name = "NCODIGO_APORTE")
	private Integer nCodigoAporte;
	@Column(name = "SESTADO")
	private String sEstado;
	@Column(name = "SUSUMODIFICA")
	private String sUsuModifica;
	@Column(name = "SUSUREGISTRA")
	private String sUsuRegistra;
}
