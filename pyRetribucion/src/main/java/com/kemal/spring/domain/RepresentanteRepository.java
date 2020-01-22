package com.kemal.spring.domain;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepresentanteRepository extends JpaRepository<Representante, Integer> {
	 @Query(value = "SELECT sq_ret_representante.nextval FROM dual", nativeQuery = 
		        true)
	 Integer getNextSeriesId();
	 @Query(value = "SELECT r FROM Representante r join r.contribuyente c join r.tipoDocumento t where c.id = :idContribuyente", nativeQuery = 
		        false)
	 List<Representante> findAllByIdContribuyente(@Param("idContribuyente") Integer idContribuyente);
	 /*@Query(value = "SELECT r FROM Representante r join r.contribuyente c join r.tipoDocumento t where c.id = :idContribuyente and r.nEstado=1",
		    countQuery = "SELECT count(r) FROM Representante r join r.contribuyente c join r.tipoDocumento t where c.id = :idContribuyente and r.nEstado=1", nativeQuery = 
	        false)*/
	 @Query(value = "SELECT r FROM Representante r join r.contribuyente c join r.tipoDocumento t where c.id = :idContribuyente and r.nEstado=1",
			    nativeQuery = false)
	 Page<Representante> findAllByIdContribuyentePaginacion(@Param("idContribuyente") Integer idContribuyente,Pageable pageable);
	 
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Representante c SET c.sNumero = :numeroDoc, c.sNombres=:nombres, c.sApePaterno=:apePaterno, c.sApeMaterno=:apeMaterno,"
    		+ "c.dFechaInicio=:fechaInicio WHERE c.id = :idReponsable")
    int updateAddress(@Param("numeroDoc") String numeroDoc, 
    		@Param("nombres") String nombres,@Param("apePaterno")String apePaterno,@Param("apeMaterno")String apeMaterno,
    		@Param("fechaInicio")Date fechaInicio);
}
