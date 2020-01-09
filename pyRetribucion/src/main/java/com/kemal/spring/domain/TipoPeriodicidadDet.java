/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kemal.spring.domain;

/**
 *
 * @author consultor_jti07
 */
import java.io.Serializable;
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

/**
 *
 * @author consultor_jti07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_TIPOPERIODICIDADDET")
public class TipoPeriodicidadDet implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    @Column(name = "NCODIGO",unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceTIPOPERIODICIDADDET")
    @SequenceGenerator(name = "id_SequenceTIPOPERIODICIDADDET", sequenceName = "SQ_RET_TIPOPERIODICIDADDET", allocationSize= 1)
    private Integer id;
   
    
    @Column(name = "DFECHAVENC", nullable = false)
    private Date dFechaVenc;
    
    @ManyToOne
	@JoinColumn(name = "ncodigotpvenc")
	private TipoVencimiento tipoVencimiento;
    
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoRetribucion.class)
 	@JoinColumn(name="nTipoRetribucion")
	private TipoRetribucion tipoRetribucion; 
 	
 	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoPeriodicidad.class)
 	@JoinColumn(name="nTipoPeriodicidad")
	private TipoPeriodicidad tipoPeriodicidad;
 	
 	
    @Column(name = "SMES_PERIODO", nullable = false,length = 2)
    private String sMesPeriodo;
    
    @Column(name = "SANIO_PERIODO", nullable = false,length = 4)
    private String sAnioPeriodo;
  
    
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Concepto.class)
    @JoinColumn(name = "ncodigoConcepto")
    private Concepto concepto ;   
    
	
    
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = Contrato.class)
    @JoinColumn(name = "ncodigocontrato")
    private Contrato contrato;
	
	@Column(name = "SESPECIAL")
	private String sEspecial="0";
	@Column(name = "SDIGITO_RUC",length = 1)
	private String sDigitoRuc;
	@Column(name = "SESTADO")
	private String sEstado="1";
    
}
