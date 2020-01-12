package com.kemal.spring.domain.procedures;

import java.sql.ResultSet;
import java.util.List;

import com.kemal.spring.domain.nonentity.ListarLiquidacion;

import lombok.Data;
@Data
public class PkgListarLiquidacionMapperFilter {
	private int pagina;
	private int totalRegistroPagina;
	private int totalRegistros;
	private List<ListarLiquidacion> lista;
	
	
	public List<ListarLiquidacion> getLista() {
		return lista;
	}

	public void setLista(List<ListarLiquidacion> lista) {
		this.lista = lista;
	}

	private ResultSet P_CURSOR;

	public ResultSet getP_CURSOR() {
		System.out.println("INGRESE 1");
		return P_CURSOR;
	}

	public void setP_CURSOR(ResultSet p_CURSOR) {
		System.out.println("INGRESE 2");
		P_CURSOR = p_CURSOR;
	}
}
