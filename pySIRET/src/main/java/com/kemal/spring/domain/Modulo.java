package com.kemal.spring.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "SRET_MODULO")
public class Modulo {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceModulo")
	@SequenceGenerator(name = "id_SequenceModulo", sequenceName = "SQ_RET_MODULO", allocationSize= 1)
	private Integer ncodigo;
	
	@Column(name = "SDESCRIPCION")
	private String sDescripcion;
	
	@CreationTimestamp
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DFECHAREGISTRO")
	private Date dFechaRegistro;
	
	
	@Column(name = "SESTADO")
	private String sEstado="1";
}
