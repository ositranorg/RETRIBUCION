package com.kemal.spring.domain.nonentity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resultado {
	private String mensaje;
	private int resultado;
}
