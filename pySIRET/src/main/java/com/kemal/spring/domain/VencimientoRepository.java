package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VencimientoRepository extends JpaRepository<Vencimiento, Integer> {
	public List<Vencimiento> findBySAnioPeriodoAndSEstado(String sAnioPeriodo,String sEstado);
	
	public List<Vencimiento> findBysEstadoOrderById(String Estado);
	@Query("select i from Vencimiento i "
			+ " left join i.concesionario s "
			+ " left join i.tipoPeriodicidad p"
			+ " left join i.tipoRetribucion r "
			+ " where (:idconcesionario is null or s.id=:idconcesionario)"
			+ " and (:idtipoPeriodicidad is null or p.id=:idtipoPeriodicidad)"
			+ " and (:idtipoRetribucion is null or r.id=:idtipoRetribucion) "
			+ " and i.sAnioPeriodo=:sAnioPeriodo"
			+ " and i.sEstado=:sEstado"
			+ " Order By i.sMesPeriodo Asc")
	public List<Vencimiento> getVencimiento(Integer idconcesionario,
										    Integer idtipoPeriodicidad,
										    Integer idtipoRetribucion,
										    String sAnioPeriodo,
										    String sEstado);
}
