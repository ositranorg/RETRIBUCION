package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class RepresentanteDto {
	private int pagina;
	private Integer id;
	private String sNumero;
	private String sNombres;
	private String sApePaterno;
	private String sApeMaterno;
	private Date dFechaInicio;
	private String sCargo;
	private TipoDocumentoDto tipoDocumento;
	private ContribuyenteDto contribuyente;
}
