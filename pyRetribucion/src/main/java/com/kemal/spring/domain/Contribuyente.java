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
@Table(name = "SRET_CONTRIBUYENTE")
public class Contribuyente {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceCONTRIBUYENTE")
	@SequenceGenerator(name = "id_SequenceCONTRIBUYENTE", sequenceName = "SQ_RET_CONTRIBUYENTE", allocationSize= 1)
	private Integer id;
	@Column(length = 11)
	private String sruc;
	private String snombre;

	private String sestado = "1";

	

}
