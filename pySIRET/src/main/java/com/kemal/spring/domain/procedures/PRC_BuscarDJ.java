package com.kemal.spring.domain.procedures;

import java.util.HashMap;

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
	public HashMap<String,Object>  buscarDJ(int concesionario,String mes,String anio,String usuario) {
		HashMap<String,Object> m=new HashMap<String,Object>();
		try {
			jdbcTemplate.setResultsMapCaseInsensitive(true);
			SqlParameterSource parameters=new MapSqlParameterSource()
					.addValue("PMES", mes)
					.addValue("PANIO", anio)
					.addValue("PCONCESIONARIO", concesionario)					
					.addValue("CODUSUARIO", usuario);
			simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate)
					.withCatalogName("PK_RET_APORTE")
					.withFunctionName("PRC_GETDJ");
			String resultado=simpleJdbcCallRefCursor.executeFunction(String.class,parameters);
			
			Integer n=null;
			try {
				n=new Integer(resultado);
			}catch(NumberFormatException e) {
				n=new Integer("-1");
				e.printStackTrace();
				System.out.println(resultado);
			}
			m.put("codigo", n);
			m.put("mensaje", resultado);
			return m;
		} catch (NumberFormatException e) {
			return m;
		}
	}
}
