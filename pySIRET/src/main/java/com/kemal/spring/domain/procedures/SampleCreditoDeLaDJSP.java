package com.kemal.spring.domain.procedures;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component
public class SampleCreditoDeLaDJSP {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	public void listar(int concesionario,int page) {
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SqlParameterSource parameters=new MapSqlParameterSource()
				.addValue("PCONCESIONARIO", concesionario)
				.addValue("PPAGE", page);
		simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate).withCatalogName("PK_RET_SALDOS").withProcedureName("PRC_DETSALDOACT")
				//.returningResultSet("CPAGOS",BeanPropertyRowMapper.newInstance(CreditoDeLaDJ.class))
				;
		Map<String, Object> map=simpleJdbcCallRefCursor.execute(parameters);
		List d=(List)map.get("CPAGOS");
		
//		List<CreditoDeLaDJ> x=new ArrayList<CreditoDeLaDJ>();
//		for (Object object : d) {
//			CreditoDeLaDJ credito=(CreditoDeLaDJ)object;
//			x.add(credito);
//		}
//		return x;
	}		
	
	
	
}
