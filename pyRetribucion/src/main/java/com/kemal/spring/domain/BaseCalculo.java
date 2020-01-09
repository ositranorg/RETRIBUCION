package com.kemal.spring.domain;

import java.math.BigDecimal;

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

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SRET_BASECALCULO")
public class BaseCalculo {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceBASECALCULO")
	@SequenceGenerator(name = "id_SequenceBASECALCULO", sequenceName = "SQ_RET_BASECALCULO", allocationSize= 1)
	private Integer id;
	@Column(length = 250)
	private String sDescripcion;
	
	private BigDecimal nimporte;
	
	private String sEstado="1";
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Concepto.class)
    @JoinColumn(name = "ncodigoConcepto")
    private Concepto concepto ;  
	
	
	private Integer ncodigoAp;

}
