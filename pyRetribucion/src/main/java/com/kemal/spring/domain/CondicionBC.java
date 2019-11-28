package com.kemal.spring.domain;

import java.util.Date;

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
@Table(name = "SRET_BUENCONTRIBUYENTE")
public class CondicionBC {

	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceBuenContribuyente")
	@SequenceGenerator(name = "id_SequenceBuenContribuyente", sequenceName = "SQ_RET_BUENCONTRIBUYENTE", allocationSize= 1)
	private Integer id;
	
	@Column(name = "DFECINICIO")
	private Date dfecInicio;

	@Column(name = "DFECEXCLUSION")
	private Date dfecExclusion;

	@Column(name = "SBUENCONTRIBUYENTE", length = 2)
	private String sBuenContribuyente;

	@Column(name = "STATUS")
	private String status = "VIGENTE";

	@Column(name = "SESTADO", length = 1)
	private String sEstado = "1";

	@Column(name = "DFECHAREGISTRO")
	private Date dfecRegistro;

	@Column(name = "NCODIGOCNS")
	private Integer nCodigoCns;
}
