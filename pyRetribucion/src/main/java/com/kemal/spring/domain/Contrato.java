package com.kemal.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "SRET_CONTRATO")
public class Contrato implements Serializable {
	@Id
	@Column(name = "NCODIGO",unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceContrato")
	@SequenceGenerator(name = "id_SequenceContrato", sequenceName = "SQ_RET_CONTRATO", allocationSize= 1)
	private Integer id;
	private String sDescripcion;		
	private String sEstado="1";
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoPeriodicidad.class)
    @JoinColumn(name = "ncodigocalendario")
    private TipoPeriodicidad calendario ;//mensual trimestral
	
	@ManyToOne
	@JoinColumn(name = "ncodigocnt")
	private Contribuyente contribuyente;

	@ManyToOne(fetch = FetchType.EAGER,targetEntity = TipoVencimiento.class)
	@JoinColumn(name = "ncodigotpvenc")
	private TipoVencimiento tipoVencimiento;
}
