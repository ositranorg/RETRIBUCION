package com.kemal.spring.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreditoDeLaDJ {
private String periodo;
private String tiporetribucion;
private String mes;
private String anio;
private BigDecimal nmonto;
}
