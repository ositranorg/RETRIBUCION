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
public class AporteLiberacionModalDTO {
	private Integer id;
	private String dfecReconocimiento;
	private String sDescripcion;
	private BigDecimal nImporte;
	private BigDecimal nSaldo;
	private String moneda ;
}
