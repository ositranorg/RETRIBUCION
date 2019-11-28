package com.kemal.spring.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AporteDeduccionModalDTO {
	private Integer id;
	private String dfecRegistro;
	private String sDescripcion;
	private BigDecimal nImporte;
	private BigDecimal nSaldo;
	private String moneda ;
}
