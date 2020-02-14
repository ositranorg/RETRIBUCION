package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AporteDescuentoRepository extends JpaRepository<AporteDescuento, Integer>{
	@Query("SELECT "
			+ " u"
			+ " FROM AporteDescuento u inner join u.aporte m "
			+ " where m.id=:idAporte"		
			+ " and u.sEstado = :sEstado "
			+ " order By u.id desc")
	public List<AporteDescuento> getListaSaldo(		
			@Param("idAporte") Integer idAporte,
			@Param("sEstado") String sEstado
			);	
	public List<AporteDescuento> findByAporteAndSEstado(Aporte aporte,String estado);	
}
