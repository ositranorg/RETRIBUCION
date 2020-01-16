package com.kemal.spring.domain.nonentity;

import java.util.List;

import lombok.Data;

@Data
public class CambiarClaveFiltro {
	private String claveAnterior;
	private String nuevaClave;
	private String confirmarClave;	
	private int idUsuario;
	private List<Resultado> respuesta;
}
