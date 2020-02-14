package com.kemal.spring.domain.nonentity;

import java.math.BigDecimal;

import com.kemal.spring.domain.procedures.PRC_LISTAR_LIQUIDACION;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListarLiquidacion {
	private BigDecimal nOrden;
	private BigDecimal nCodigo;
	private String dFechaRegistro;
	private String dFechaModifica;
	private String dHoraRegistro;
	private String dHoraModifica;
	private BigDecimal nAnio;
	private BigDecimal nIdTipoDocumento;
	private BigDecimal nPeriodo;
	private String sDocumento;
	private String sEstado;
	private String susumodifica;
	private String susuregistra;
	
}
