package com.kemal.spring.domain;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

	 @Query(value = "SELECT sq_ret_liquidacion.nextval FROM dual", nativeQuery = 
		        true)
	 Long getNextSeriesId();
	
	 @Query(value="select l from Liquidacion l where l.nAnio=?1 and l.sEstado=?2")
	 Collection<Liquidacion> findByEstadoAndAnio( int anio,String estado);
}


