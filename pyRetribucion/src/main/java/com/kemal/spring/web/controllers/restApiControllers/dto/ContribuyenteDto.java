package com.kemal.spring.web.controllers.restApiControllers.dto;

import lombok.Data;

@Data
public class ContribuyenteDto {
	private Integer id;
	private int pagina;
	private String sTelefono;
	private String sCorreo;
	private String sruc;
	private RepresentanteDto representante;
}
