package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoPeriodicidadDetRepository extends JpaRepository<TipoPeriodicidadDet, Integer> {


	public List<TipoPeriodicidadDet> findBysEstadoOrderById(String Estado);
	
	public List<TipoPeriodicidadDet> findByContratoAndConceptoOrderById(Contrato contrato,Concepto concepto);
}
