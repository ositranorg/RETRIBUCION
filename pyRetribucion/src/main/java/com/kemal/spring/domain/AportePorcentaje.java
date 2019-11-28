package com.kemal.spring.domain;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "SRET_APORTEPORCENTAJE")
	
public class AportePorcentaje {
	
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SequenceAPORTEPORCENTAJE")
	@SequenceGenerator(name = "id_SequenceAPORTEPORCENTAJE", sequenceName = "SQ_RET_APORTEPORCENTAJE", allocationSize= 1)
	private Integer id;
	
	private BigDecimal porcentaje;
	
	@ManyToOne
	private Contribuyente contribuyente;
	
	@ManyToOne
	private TipoRetribucion tipoRetribucion;
	private String sEstado="1";
	

}
