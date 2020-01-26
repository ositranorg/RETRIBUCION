package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

	 @Query(value = "SELECT sq_ret_liquidacion.nextval FROM dual", nativeQuery = 
		        true)
	 Long getNextSeriesId();
	
	 @Query(value="select l from Liquidacion l where l.nAnio=?1 and l.sEstado=?2")
	 Collection<Liquidacion> findByEstadoAndAnio( int anio,String estado);
	 
	 @Query(value="select max(l.nOrden) from Liquidacion l where l.sEstado='2'")
	 BigDecimal maxOrdenByEstadoDos();
	 
	 @Modifying(flushAutomatically = true)
	 @Query("update Liquidacion l set l.nOrden = :orden, l.sEstado='2', l.dfecModifica=:currentDate where l.sEstado='1'")
	 int actualizarOrden(@Param("orden")int orden, @Param("currentDate")Date currentDate);

	
}


