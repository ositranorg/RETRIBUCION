package com.kemal.spring.bd.view;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NuevoCreditoDTO {
	
	private Long codigo;	
	
	private Long nCodigo;

	private Integer nCodigocn;
	
	private String sTipoPeriodicidad;

	private String sTipoRetribucion;
	
	private String sTipoPeriodicidadNombre;

	private String sTipoRetribucionNombre;

	private String sAnio;

	private String sMes;

	private BigDecimal nretResultante;
	private int pagina;
}
