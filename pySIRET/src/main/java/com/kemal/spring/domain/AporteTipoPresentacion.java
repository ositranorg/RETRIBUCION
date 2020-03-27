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
@Table(name = "SAPR_TIPODECLARACION",schema ="SAPREGSF")
public class AporteTipoPresentacion {
	@Id
	@Column(name = "NCODIGO_TIPODECLARACION")
	private Integer id;
	
	@Column(name = "SNOMBRE_TIPODECLARACION")
	private String sDescripcion;
	@Column(name = "SESTADO")
	private String sEstado="1";
	
	
	
	
}
