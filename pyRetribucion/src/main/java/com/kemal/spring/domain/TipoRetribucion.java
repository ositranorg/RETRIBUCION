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
@Table(name = "SRET_TIPORETRIBUCION")
public class TipoRetribucion {
	@Id
	@Column(name="NCODIGO")
	private Integer id;
	@Column(name = "SDESCRIPCION")
	private String sDescripcion;
	@Column(name = "SESTADO")
	private String sEstado="1";
	

	
}
