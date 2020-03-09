package com.kemal.spring.web.controllers.restApiControllers.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonRootName("buscarDJDto")
public class BuscarDJDto {
	private Integer tipoperiodicidad;
	private Integer tiporetribucion;
	private String smesperiodo;
	private String sanioperiodo;
	private Integer moneda;
	private BigDecimal porcentaje;
	private String mensaje;
	private Integer codigo;
}
