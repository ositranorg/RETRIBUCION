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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_APORTEDEDUCCION")
public class AporteDeduccion {
	@Id
	@Column(name = "NCODIGO", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceAPORTEDEDUCCION")
	@SequenceGenerator(name = "id_SequenceAPORTEDEDUCCION", sequenceName = "SQ_RET_APORTEDEDUCCION", allocationSize= 1)
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
  
	
	@Column(name = "SDESCRIPCION_DED", length = 4000)
	private String sDescripcionDed;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Deduccion.class)
	@JoinColumn(name = "ID_DEDUCCION")
	private Deduccion deduccion;
	
	@Column(name = "NIMPORTE_DED")
	private BigDecimal nImporteDed;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Aporte.class)
	@JoinColumn(name = "NCODIGO_APORTE")
	private Aporte aporte;




	

}

