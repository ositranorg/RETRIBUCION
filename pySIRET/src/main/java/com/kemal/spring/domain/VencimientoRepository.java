package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VencimientoRepository extends JpaRepository<Vencimiento, Integer> {


	public List<Vencimiento> findBysEstadoOrderById(String Estado);
	@Query("select i from Vencimiento i "
			+ " left join i.concesionario s "
			+ " left join i.tipoPeriodicidad p"
			+ " left join i.tipoRetribucion r "
			+ " left join i.concepto c"
			+ " where (s.id is null or s.id=:idconcesionario)"
			+ " and (p.id is null or p.id=:idtipoPeriodicidad)"
			+ " and (r.id is null or r.id=:idtipoRetribucion) "
			+ " and (c.id is null or c.id=:idconcepto)"
			+ " and i.sAnioPeriodo=:sAnioPeriodo"
			+ " and i.sEstado=:sEstado"
			+ " Order By i.sMesPeriodo Asc")
	public List<Vencimiento> getVencimiento(Integer idconcesionario,
										    Integer idtipoPeriodicidad,
										    Integer idtipoRetribucion,
										    Integer idconcepto,
										    String sAnioPeriodo,
										    String sEstado);
}
