package com.kemal.spring.domain.nonentity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;

//@Repository
@Mapper
public interface CambiarClaveMapper {
	@Select(value = "{ CALL PK_USER.PRC_CAMBIARCLAVE"
			+ "("
			+ "#{filtro.idUsuario,mode=IN,jdbcType=NUMERIC,javaType=int},"
			+ "#{filtro.claveAnterior,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.nuevaClave,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.confirmarClave,mode=IN,jdbcType=VARCHAR,javaType=String},"
			+ "#{filtro.respuesta,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=CambiarClaveMapping}"
			+ ")}")	
	@ResultType(Resultado.class)
	@Options(statementType = StatementType.CALLABLE)	
	@Results(id = "CambiarClaveMapping", 
	value = { 
			@Result(property = "mensaje", column = "MENSAJE",jdbcType=JdbcType.VARCHAR,javaType = String.class),
			@Result(property = "resultado", column = "RESULTADO",jdbcType=JdbcType.NUMERIC,javaType = int.class),			
			})
	public void cambiarClave(@Param("filtro") CambiarClaveFiltro filtro);
}
