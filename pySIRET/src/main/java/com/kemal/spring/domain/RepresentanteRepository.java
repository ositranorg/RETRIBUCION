package com.kemal.spring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kemal.spring.bd.view.VWRepresentante;

public interface RepresentanteRepository extends JpaRepository<VWRepresentante, Integer> {
	public List<VWRepresentante> findByIdConcesionario(Integer concesionario);
	
}
