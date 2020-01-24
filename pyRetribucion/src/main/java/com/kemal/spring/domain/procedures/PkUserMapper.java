package com.kemal.spring.domain.procedures;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface PkUserMapper {
	@Select(value = "{ CALL PK_USER.PRC_ENVIAR_CLAVE"
			+ "("
			+ "#{filtro.identidadPrestadora,mode=IN,jdbcType=NUMERIC,javaType=int},"
			+ "#{filtro.correo,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.claveDecode,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.claveEncode,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.respuesta,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=recuperarClaveMapper}"
			+ ")}")	
	@ResultType(PkUserMapperResultado.class)
	@Options(statementType = StatementType.CALLABLE)	
	@Results(id = "recuperarClaveMapper", 
	value = { 
			@Result(property = "estadoClave", column = "estadoClave",jdbcType=JdbcType.NUMERIC,javaType = java.math.BigDecimal.class),
			@Result(property = "resultado", column = "resultado",jdbcType=JdbcType.NUMERIC,javaType = int.class),
			@Result(property = "mensaje", column = "mensaje",jdbcType=JdbcType.VARCHAR,javaType = String.class)
			})
	//public List<ListarLiquidacion> listarLiquidacion();
	public void recuperarClave(@Param("filtro") PkUserMapperFilter filtro) throws Exception;
	
}



