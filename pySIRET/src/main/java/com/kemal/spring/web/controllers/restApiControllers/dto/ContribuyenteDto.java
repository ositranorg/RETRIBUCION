package com.kemal.spring.web.controllers.restApiControllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContribuyenteDto {
	private Integer id;
	private String sDescripcion;
	private int pagina;
	private String sTelefono;
	private String sCorreo;
	private String sruc;
	private RepresentanteDto representante;
	
	public  ContribuyenteDto(Integer id,String sDescripcion) {
		this.id=id;
		this.sDescripcion=sDescripcion;
	}
}
