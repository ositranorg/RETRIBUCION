package com.kemal.spring.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_REPRESENTANTE")
public class Representante {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceREPRESENTANTE")
	@SequenceGenerator(name = "id_SequenceREPRESENTANTE", sequenceName = "SQ_RET_REPRESENTANTE", allocationSize= 1)
	private Integer id;
	private String sNumero;
	private String sNombres;
	private String sApePaterno;
	private String sApeMaterno;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dFechaInicio;
	private String sCargo;
	@Column(nullable = true)
	private int nEstado=1;
	/*
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)	
	@JoinColumn(unique = true,name = "tipodocumento_id", insertable=false, updatable=false)
	*/
	//@OneToOne(fetch = FetchType.LAZY)
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipodocumento_id")
	//@JsonBackReference
	private TipoDocumento tipoDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY,
			optional = false)
	@JoinColumn(name = "contribuyente_id", nullable = false)
	@JsonIgnore
	private Contribuyente contribuyente;
}
