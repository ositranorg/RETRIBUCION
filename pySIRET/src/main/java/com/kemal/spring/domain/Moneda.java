package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "SRET_MONEDA")
public class Moneda {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceMONEDA")
	@SequenceGenerator(name = "id_SequenceMONEDA", sequenceName = "SQ_RET_MONEDA", allocationSize= 1)
	private Integer id;
	@Column(length = 20)
	private String sDescripcion;
	
	
	@Column(length = 5)
	private String sSimbolo;
	
	
	@Column(length = 1)
	private String sEstado="1";
	
	
}
