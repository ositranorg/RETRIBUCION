package com.kemal.spring.domain;

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
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_CONCEPTO")
public class Concepto {
	/*
	 * 
	 * NCODIGO_MODULO NUMBER Yes 1 
	 * NCODIGO NUMBER No 2 
	 * SDESCRIPCION VARCHAR2(50 BYTE) Yes 3 
	 * DFECHAREGISTRO DATE Yes SYSDATE 4
	 * 
	 */
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceCONCEPTO")
	@SequenceGenerator(name = "id_SequenceCONCEPTO", sequenceName = "SQ_RET_CONCEPTO", allocationSize= 1)
	private Integer id;
	
	@Column(name = "SDESCRIPCION")
	private String sDescripcion;
	@CreationTimestamp
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DFECHAREGISTRO")
	private Date dFechaRegistro;
	
	@Column(name = "SESTADO")
	private String sEstado="1";
	
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Modulo.class)
    @JoinColumn(name = "ncodigoModulo")
    private Modulo modulo;
    
    public Concepto (Integer id){
    	this.id=id;
    }
    
}
