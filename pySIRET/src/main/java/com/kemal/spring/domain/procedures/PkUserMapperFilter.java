package com.kemal.spring.domain.procedures;

import java.util.List;

import lombok.Data;
@Data
public class PkUserMapperFilter {
	private int identidadPrestadora;
	private String correo;
	private String claveDecode;
	private String claveEncode;
	private List<PkUserMapperResultado> respuesta;
}
