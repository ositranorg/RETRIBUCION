package com.kemal.spring.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreditosForm {
	private String ftipoPeriodicidad;
	private String ftipoRetribucion;
	private String fmesRetribucion;
	private String fanioRetribucion;
	
	private String ftipoPeriodicidadDestino;
	private String ftipoRetribucionDestino;
	private String fmesRetribucionDestino;
	private String fanioRetribucionDestino;
	

	
	private String id;
	private String accion;	
	private String ids;
}
