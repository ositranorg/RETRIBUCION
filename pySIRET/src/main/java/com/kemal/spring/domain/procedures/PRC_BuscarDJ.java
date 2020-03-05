package com.kemal.spring.domain.procedures;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component
public class PRC_BuscarDJ {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	public Map<String, Object>  getTipoDeclaracion(int concesionario,String mes,String anio) {
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
			SqlParameterSource parameters=new MapSqlParameterSource()
					.addValue("CODIGO_CONSECION", concesionario)
					.addValue("MES_PERIODO", mes)
					.addValue("ANIO_PERIODO", anio);
			simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate)
					.withCatalogName("PK_SRET_APORTE")
					.withProcedureName("PRC_TIPODECLARACION");
			return	simpleJdbcCallRefCursor.execute(parameters);
		} catch (NumberFormatException e) {
			return new  HashMap<String, Object>();
		}
	}
}
