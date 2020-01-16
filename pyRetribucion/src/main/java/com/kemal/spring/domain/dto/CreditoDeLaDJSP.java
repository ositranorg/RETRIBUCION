package com.kemal.spring.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
@Component
public class CreditoDeLaDJSP implements CreditoDeLaDJRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallRefCursor;
	@SuppressWarnings({ "rawtypes", "unlikely-arg-type", "unchecked" })
	@Override
	public List<CreditoDeLaDJ> listar(int concesionario,int ini,int max) {
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SqlParameterSource parameters=new MapSqlParameterSource()
				.addValue("PCONCESIONARIO", concesionario)
				.addValue("IDXINI", ini)
				.addValue("IDXFIN", max);
		simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate).withCatalogName("PK_RET_SALDOS").withProcedureName("PRC_DETSALDOACT")
				.returningResultSet("CPAGOS",BeanPropertyRowMapper.newInstance(CreditoDeLaDJ.class));
		Map<String, Object> map=simpleJdbcCallRefCursor.execute(parameters);
		List d=(List)map.get("CPAGOS");
		
		List<CreditoDeLaDJ> x=new ArrayList<CreditoDeLaDJ>();
		for (Object object : d) {
			CreditoDeLaDJ credito=(CreditoDeLaDJ)object;
			x.add(credito);
			
			
		}
		
		
		
		return x;
	}		
	public long countListar(int concesionario) {
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		SqlParameterSource parameters=new MapSqlParameterSource()
				.addValue("PCONCESIONARIO", concesionario);
				
		simpleJdbcCallRefCursor=new SimpleJdbcCall(jdbcTemplate).withCatalogName("PK_RET_SALDOS").withProcedureName("PRC_DETSALDOACTCOUNT");
		Map<String, Object> map=simpleJdbcCallRefCursor.execute(parameters);
		
		long total=((BigDecimal)map.get("TOTAL")).longValue();
	
		
		
		return total;
	}
	
	
}
