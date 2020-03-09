package com.kemal.spring.domain.procedures;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component
public class PRC_TipoDeclaracion {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	public Integer getTipoDeclaracion(int concesionario,String mes,String anio) {
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
			SqlParameterSource parameters=new MapSqlParameterSource()
					.addValue("CODIGO_CONSECION", concesionario)
					.addValue("MES_PERIODO", mes)
					.addValue("ANIO_PERIODO", anio);
			simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate).withCatalogName("PK_RET_APORTE").withProcedureName("PRC_TIPODECLARACION");
			Map<String, Object> map=simpleJdbcCallRefCursor.execute(parameters);
			return Integer.parseInt(map.get("TIPODECLARACION").toString());
		} catch (NumberFormatException e) {
			return new Integer("-1");
		}
	};
}
