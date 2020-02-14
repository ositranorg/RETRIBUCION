package com.kemal.spring.web.form;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuscarRetribucionForm {
	private String tipoPeriodicidad;
	private String tipoRetribucion;
	private String mesRetribucion;
	private String anioRetribucion;
	private Integer monedaRetribucion;
	private String buenContribuyente;
	private String fdesde;
	private String fhasta;
	private String mes;
	private int codaportebihdd;
	private String errorMessage;
	private BigDecimal porcentaje;
	private String flagtermino;
	
	private int lstAportePorcentajesize;
}
