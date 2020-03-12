package com.kemal.spring.domain;

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
@Table(name = "SRET_APORTETIPOPRESNT")
public class AporteTipoPresentacion {
	@Id
	@Column(name = "NCODIGO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence_APORTETIPOPRESNT")
	@SequenceGenerator(name = "id_Sequence_APORTETIPOPRESNT", sequenceName = "SQ_RET_APORTETIPOPRESNT", allocationSize= 1)
	private Integer id;
	
	@Column(name = "SDESCRIPCION")
	private String sDescripcion;
	@Column(name = "SESTADO")
	private String sEstado="1";
	
	
	
	
}
