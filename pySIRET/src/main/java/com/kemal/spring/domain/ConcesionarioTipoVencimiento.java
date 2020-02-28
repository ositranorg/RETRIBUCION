package com.kemal.spring.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name = "SRET_CSNRIOTIPOVEN")
@IdClass(ConcesionarioTipoVencimientoId.class)
public class ConcesionarioTipoVencimiento {
	@Id
	private Integer idConcesionario;
	@Id
	private Integer idTipoVencimiento;

	private String sAnioPeriodo;

	@ManyToOne
	@JoinColumn(name = "idConcesionario", referencedColumnName = "CNC_ID", insertable = false, updatable = false)
	private Concesionario concesionario;

	@ManyToOne
	@JoinColumn(name = "idTipoVencimiento", referencedColumnName = "NCODIGO", insertable = false, updatable = false)
	private TipoVencimiento tipoVencimiento;
	
	public ConcesionarioTipoVencimiento(Integer idConcesionario,Integer idTipoVencimiento,String sAnioPeriodo) {
		
		this.idConcesionario=idConcesionario;
		this.idTipoVencimiento=idTipoVencimiento;
		this.sAnioPeriodo=sAnioPeriodo;
	}
}
