package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "T_FERIADO",schema ="SCINVGSF" )
public class Feriado {
	@Id
	@Column(name = "FER_ID", unique = true, nullable = false,insertable = false,updatable = false)
	private Integer ferId;
	
	@Column(name = "FER_ANYO",insertable = false,updatable = false)
	private Integer ferAnyo;	
	
	@Column(name = "FER_MES",insertable = false,updatable = false)
	private Integer ferMes;
	
	
	@Column(name = "FER_DIA",insertable = false,updatable = false)
	private Integer ferDia;
}
