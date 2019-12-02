package com.kemal.spring.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

	 @Query(value = "SELECT sq_ret_liquidacion.nextval FROM dual", nativeQuery = 
		        true)
		 Long getNextSeriesId();
	
	
}
