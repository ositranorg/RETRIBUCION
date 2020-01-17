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
@Table(name = "SRET_APORTE")
public class Aporte {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceAPORTE")
	@SequenceGenerator(name = "id_SequenceAPORTE", sequenceName = "SQ_RET_APORTE", allocationSize= 1)
	private Integer id;
	

 	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoRetribucion.class)
 	@JoinColumn(name="sTipoRetribucion")
	private TipoRetribucion tipoRetribucion; 
 	
 	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoPeriodicidad.class)
 	@JoinColumn(name="sTipoPeriodicidad")
	private TipoPeriodicidad tipoPeriodicidad;
 	
 	
	private String sMesPeriodo;
	private String sAnioPeriodo;
	

	private String sEstadoPres="I";
	private String sEstado="1";
	@Temporal(TemporalType.DATE)
	private Date fechaVenPago;
	@Temporal(TemporalType.DATE)
	private Date fechaVenPres;
	
	@ManyToOne
	@JoinColumn(name="ncodigoCN")
	private Contribuyente contribuyente;
	
	@ManyToOne
	@JoinColumn(name="ncodigoAT")
	private AporteTipo aporteTipo;
	
    @Column(name = "NINTERES")
	private BigDecimal nInteres;
    @Column(name = "NRET_RESULTANTE")
	private BigDecimal nRetribucionResultante;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DFECHAREGISTRO")
    private Date dfecRegistro;
	@Column(name = "SUSUREGISTRA")
    private String sUsuRegistra;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DFECHAMODIFICA")
    private Date dfecModifica;
	@Column(name = "SUSUMODIFICA")
    private String sUsuModifica;
	
}
