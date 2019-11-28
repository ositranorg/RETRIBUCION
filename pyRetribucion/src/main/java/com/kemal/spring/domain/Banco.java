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
@Table(name = "SRET_BANCO")
public class Banco {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceBANCO")
	@SequenceGenerator(name = "id_SequenceBANCO", sequenceName = "SQ_RET_BANCO", allocationSize= 1)
	private Integer id;
	@Column(length = 250)
	private String sDescripcion;
	
	
	
	private String sEstado="1";
  
	
	

}
