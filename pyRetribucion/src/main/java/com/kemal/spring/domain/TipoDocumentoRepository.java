package com.kemal.spring.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer>{
	 @Query(value = "SELECT sq_ret_tipodocumento.nextval FROM dual", nativeQuery = 
 		        true)
	 Long getNextSeriesId();
	 @Query(value = "SELECT t FROM TipoDocumento t",
	            nativeQuery = false)
	 List<TipoDocumento> findAllTipoDocumento();
}
