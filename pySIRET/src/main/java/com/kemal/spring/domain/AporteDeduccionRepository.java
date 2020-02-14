package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AporteDeduccionRepository extends JpaRepository<AporteDeduccion, Integer>{
	@Query("SELECT "
			+ " u"
			+ " FROM AporteDeduccion u inner join u.aporte m "
			+ " where m.id=:idAporte"		
			+ " and u.sEstado = :sEstado "
			+ " order By u.id desc")
	public List<AporteDeduccion> getListaSaldo(		
			@Param("idAporte") Integer idAporte,
			@Param("sEstado") String sEstado
			);	
	public List<AporteDeduccion> findByAporteAndSEstado(Aporte aporte,String estado);		
}
