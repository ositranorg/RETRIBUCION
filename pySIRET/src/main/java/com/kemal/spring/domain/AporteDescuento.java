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
@Table(name = "SRET_APORTEDESCUENTO")
public class AporteDescuento {
	@Id
	@Column(name = "NCODIGO", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceAPORTEDESCUENTO")
	@SequenceGenerator(name = "id_SequenceAPORTEDESCUENTO", sequenceName = "SQ_RET_APORTEDESCUENTO", allocationSize= 1)
	private Integer id;
	
	
	@Column(name = "DFECHAREGISTRO")
    private Date dfecRegistro;
	@Column(name = "SUSUREGISTRA")
    private String sUsuRegistra;
	
	
	@Column(name = "DFECHAMODIFICA")
    private Date dfecModifica;
	@Column(name = "SUSUMODIFICA")
    private String sUsuModifica;
	@Column(name = "SESTADO")
	private String sEstado="1";
  
	@Column(name = "SDESCRIPCION_DES", length = 4000)
	private String sDescripcionDes;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Descuento.class)
	@JoinColumn(name = "ID_DESCUENTO")
	private Descuento descuento;
	@Column(name = "NIMPORTE_DES")
	private BigDecimal nImporteDes;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Aporte.class)
	@JoinColumn(name = "NCODIGO_APORTE")
	private Aporte aporte;




}