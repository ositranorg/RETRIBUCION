package com.kemal.spring.domain;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConcesionarioTipoVencimientoId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer idConcesionario;
	@Id
	private Integer idTipoVencimiento;
	
}
