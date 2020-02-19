package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VencimientoRepository extends JpaRepository<Vencimiento, Integer> {


	public List<Vencimiento> findBysEstadoOrderById(String Estado);
	public List<Vencimiento> findByConcesionarioAndSAnioPeriodoAndSEstado(Concesionario concesionario,String anio,String Estado);
}
