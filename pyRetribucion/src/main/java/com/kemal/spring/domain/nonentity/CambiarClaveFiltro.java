package com.kemal.spring.domain.nonentity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
public class CambiarClaveFiltro {
	private String claveAnterior;
	private String nuevaClave;
	private String confirmarClave;	
	private List<Resultado> respuesta;
}
