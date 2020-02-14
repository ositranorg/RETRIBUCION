package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_APORTELIBERACION")
public class AporteLiberacion {
	@Id
	@Column(name = "NCODIGO", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceAPORTELIBERACION")
	@SequenceGenerator(name = "id_SequenceAPORTELIBERACION", sequenceName = "SQ_RET_APORTELIBERACION", allocationSize= 1)
	private Integer id;
	
	
	@Column(name = "DFECHAREGISTRO")
    private Date dfecRegistro;
	@Column(name = "SUSUREGISTRA")
    private String sUsuRegistra;
	
	
	@Column(name = "DFECHAMODIFICA")
    private Date dfecModifica;
	@Column(name = "SUSUMODIFICA")
    private String sUsuModifica;
	private String sEstado="1";
  
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DFECRECONOCIMIENTO_LIB")
	private Date dfecReconocimientoLib;
	@Column(name = "SDESCRIPCION_LIB", length = 4000)
	private String sDescripcionLib;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = LiberacionPago.class)
	@JoinColumn(name = "ID_LIBERACION")
	private LiberacionPago liberacion;
	@Column(name = "NIMPORTE_LIB")
	private BigDecimal nImporteLib;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Aporte.class)
	@JoinColumn(name = "NCODIGO_APORTE")
	private Aporte aporte;




}
