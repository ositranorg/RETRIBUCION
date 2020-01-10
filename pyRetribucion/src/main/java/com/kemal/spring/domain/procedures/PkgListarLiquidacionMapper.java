package com.kemal.spring.domain.procedures;

import java.util.HashMap;
import java.util.List;

import javax.validation.valueextraction.Unwrapping.Unwrap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Repository;

import com.kemal.spring.domain.nonentity.ListarLiquidacion;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
//@Repository
@Mapper
public interface PkgListarLiquidacionMapper {
	@Select(value = "{ CALL PK_RET_LIQUIDACION.PRC_LISTAR_LIQUIDACION"
			+ "(#{lista,mode=OUT,jdbcType=CURSOR,javaType=java.sql.ResultSet,resultMap=ListarLiquidacion2Mapping})}")
	
	@ResultType(ListarLiquidacion.class)
	@Options(statementType = StatementType.CALLABLE)	
	//@ResultMap("ListarLiquidacionMapping")	
	@Results(id = "ListarLiquidacion2Mapping", 
	value = { 
			@Result(property = "nOrden", column = "NORDEN",jdbcType=JdbcType.NUMERIC,javaType = java.math.BigDecimal.class),
			@Result(property = "nCodigo", column = "ncodigo",jdbcType=JdbcType.NUMERIC,javaType = java.math.BigDecimal.class),
			@Result(property = "dFechaRegistro", column = "dfecharegistro",jdbcType=JdbcType.VARCHAR),
			@Result(property = "dFechaModifica", column = "dfechamodifica",jdbcType=JdbcType.VARCHAR),
			@Result(property = "dHoraRegistro", column = "dhoraregistro",jdbcType=JdbcType.VARCHAR),
			@Result(property = "dHoraModifica", column = "dhoramodifica",jdbcType=JdbcType.VARCHAR)
			/*,
			@Result(property = "nAnio", column = "nanio",jdbcType=JdbcType.NUMERIC,javaType = int.class),
			@Result(property = "nIdTipoDocumento", column = "nidtipodocumento",jdbcType=JdbcType.NUMERIC,javaType = int.class),
			@Result(property = "nPeriodo", column = "nperiodo",jdbcType=JdbcType.NUMERIC,javaType = int.class),
			@Result(property = "sDocumento", column = "sdocumento",jdbcType=JdbcType.VARCHAR),
			@Result(property = "sEstado", column = "sestado",jdbcType=JdbcType.VARCHAR)*/
			})
	//public List<ListarLiquidacion> listarLiquidacion();
	public void listarLiquidacion(@Param("lista") List<ListarLiquidacion> lista) throws Exception;
	//public void listarLiquidacion(@Param("lista") HashMap<String, Object> lista) throws Exception;
}
