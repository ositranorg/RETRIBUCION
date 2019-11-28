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
public class BaseCalculoForm {
	private Integer codaportebihdd;
	private Integer codigobihdd;
	private String biconcepto;
	private BigDecimal biimporte;
	private Integer cboDetalleBC;
}
