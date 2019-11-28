package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConceptoRepository   extends JpaRepository<Concepto, Integer> {
	@Query("select c from Concepto c where c.modulo.ncodigo=3 and c.sEstado='1' ")
	public List<Concepto>  findConceptosBaseImponible();
}
