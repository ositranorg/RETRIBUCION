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
	@ManyToOne
	@JoinColumn(name="ID_CONCESIONARIO")
	private Concesionario concesionario;
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoRetribucion.class)
 	@JoinColumn(name="ID_TIPORETRIBUCION")
	private TipoRetribucion tipoRetribucion; 
 	
 	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoPeriodicidad.class)
 	@JoinColumn(name="ID_TIPOPERIODICIDAD")
	private TipoPeriodicidad tipoPeriodicidad;
 	@Column(name = "SMES_PERIODO")
	private String sMesPeriodo;
 	@Column(name = "SANIO_PERIODO")
	private String sAnioPeriodo;
 	
	@Temporal(TemporalType.DATE)
	private Date fechaVenPago;
	@Temporal(TemporalType.DATE)
	private Date fechaVenPres;
	
	@ManyToOne
	@JoinColumn(name="ID_APORTETIPOESTDJ")
	private AporteEstadoDJ aporteTipoEstadoDJ;
	
	@ManyToOne
	@JoinColumn(name="ID_APORTETIPOPRESNT")
	private AporteTipoPresentacion aporteTipoPresentacion;
	
	@Column(name = "SESTADO")
	private String sEstado="1";
	
    @Column(name = "NINTERES")
	private BigDecimal nInteres;
    @Column(name = "NRET_RESULTANTE")
	private BigDecimal nRetribucionResultante;
	
	@ManyToOne
	@JoinColumn(name="ID_MONEDA")
	private Moneda moneda;
	
	@ManyToOne
	@JoinColumn(name="ID_APORTEPORCTJE")
	private AportePorcentaje aportePorcentaje;
	
	
	
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
