package com.kemal.spring.bd.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VW_REPRESENTANTE",schema = "SAPREGSF")
public class VWRepresentante {
	@Id
	@Column(name = "NCODIGO")
	private Integer id;
	@Column(name = "SNOMBRE")
	private String sNombres;
	@Column(name = "SAPEPAT")
	private String sApePaterno;
	@Column(name = "SAPEMAT")
	private String sApeMaterno;
	@Column(name = "STIPODOC")
	private String tipoDocumento;
	@Column(name = "SNUMDOC")
	private String sNumero;
	@Column(name = "SCARGO")
	private String sCargo;
	@Column(name = "DFECINIPODER")	
	private Date dFechaInicio;
	@Column(name = "CNC_ID")	
	private Integer idConcesionario;
}
