package com.kemal.spring.web.controllers.restApiControllers.dto;

import lombok.Data;

@Data
public class UsuarioDto {
	private String claveAnterior;
	private String nuevaClave;
	private String confirmarClave;
	private int idUsuario;
	private int idEntidadPrestadora;
	private String correo;
}
