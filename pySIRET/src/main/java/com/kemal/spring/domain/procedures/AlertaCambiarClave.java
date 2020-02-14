package com.kemal.spring.domain.procedures;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component
public class AlertaCambiarClave {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	
	public void enviarclave(String  concesionario,String clave,String correo) {
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SqlParameterSource parameters=new MapSqlParameterSource()
				.addValue("IDENTIDAD_PRESTADORA", concesionario)
				.addValue("CORREO", clave)
				.addValue("CLAVE_DECODE",correo);
		simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate).withCatalogName("PK_USER").withProcedureName("PRC_ENVIARCLAVEPORCORREO");
		Map<String, Object> map=simpleJdbcCallRefCursor.execute(parameters);
		String salida=map.get("SALIDA").toString();
		System.out.println(salida);
	}		
}
