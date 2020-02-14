package com.kemal.spring.web.controllers.restApiControllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContribuyenteDto {
	private Integer id;
	private int pagina;
	private String sTelefono;
	private String sCorreo;
	private String sruc;
	private RepresentanteDto representante;
}
